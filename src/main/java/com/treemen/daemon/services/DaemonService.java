package com.treemen.daemon.services;

import com.treemen.daemon.data.entities.Mint;
import com.treemen.daemon.data.entities.SmallTree;
import com.treemen.daemon.data.entities.Team;
import com.treemen.daemon.data.entities.enums.MintState;
import com.treemen.daemon.data.repositories.MintRepository;
import com.treemen.daemon.data.repositories.SmallTreeRepository;
import com.treemen.daemon.data.repositories.TeamRepository;
import com.treemen.daemon.services.dto.Balance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DaemonService {

   Logger logger = LoggerFactory.getLogger(DaemonService.class);

   private final static int PRICE = 50; // 50HTR
   private final static int PRICE_HTR = PRICE * 100;
   private final static int NO_TEAM_ID = 5;

   private final MintRepository mintRepository;
   private final WalletService walletService;
   private final SmallTreeRepository smallTreeRepository;
   private final RetryTemplate retryTemplate;
   private final TeamRepository teamRepository;
   private final MailService mailService;

   public DaemonService(MintRepository mintRepository,
                        WalletService walletService,
                        SmallTreeRepository smallTreeRepository,
                        RetryTemplate retryTemplate, TeamRepository teamRepository, MailService mailService){
      this.mintRepository = mintRepository;
      this.walletService = walletService;
      this.smallTreeRepository = smallTreeRepository;
      this.retryTemplate = retryTemplate;
      this.teamRepository = teamRepository;
      this.mailService = mailService;
   }

   @Scheduled(fixedDelay = 10000)
   public void checkAddresses() {
      logger.info("Loop started");

      //LOADING MINTS TO PROCESS
      final List<Mint> mints = new ArrayList<>();
      try {
         retryTemplate.execute(context -> {
            mints.addAll(this.mintRepository.getAllByStateAndDead(MintState.WAITING_FOR_DEPOSIT.ordinal(), false));
            return null;
         });
         retryTemplate.execute(context -> {
            mints.addAll(this.mintRepository.getAllByStateAndDead(MintState.SENDING_NFT.ordinal(), false));
            return null;
         });
      } catch (Exception ex) {
         logger.error("Failed to get mints from database", ex);
      }
      //END OF LOADING MINTS TO PROCESS

      //LOADING MINTS TO SEND DEPOSIT BACK
      final List<Mint> returnDepositMints = new ArrayList<>();
      try {
         retryTemplate.execute(context -> {
            returnDepositMints.addAll(this.mintRepository.getAllByState(MintState.OUT_OF_NFTS.ordinal()));
            return null;
         });
      } catch(Exception ex) {
         logger.error("Failed to get mints for OUT OF TREES from database", ex);
      }
      for(Mint mint : returnDepositMints) {
         sendDepositBack(mint);
      }
      //END OF LOADING MINTS TO SEND DEPOSIT BACK

      List<Mint> notDeadMints = mints.stream().filter(mint -> !mint.isDead()).collect(Collectors.toList());
      logger.info("Processing mints " + notDeadMints.size());

      Integer receiveBalance = walletService.checkHtrBalance(false);
      if(receiveBalance != null) {
         logger.info("Receive balance is " + (receiveBalance / 100) + " HTR");
         if(receiveBalance / 100 > 500000) {
            logger.warn("WE SHOULD STOP SELLING!");
         }
      }

      for (Mint mint : notDeadMints) {
         walletService.checkWallets();

         receiveBalance = walletService.checkHtrBalance(false);
         if(receiveBalance != null) {
            logger.info("Receive balance is " + (receiveBalance / 100) + " HTR");
            if(receiveBalance / 100 > 500000) {
               logger.warn("WE SHOULD STOP SELLING!");
            }
         }

         try {
            logger.info("========================================================================");
            logger.info("Processing mint " + mint.getId());
            if(mint.getState() == MintState.WAITING_FOR_DEPOSIT.ordinal()) {
               logger.info("Mint is in WAITING_FOR_DEPOSIT state");
               Balance balance = walletService.checkBalance(mint.getDepositAddress().getAddress());
               int totalBalance = balance == null ? 0 : balance.getTotal_amount_available();
               logger.info("Balance is " + totalBalance + " and it should be " + (PRICE_HTR * mint.getCount()));
               if(totalBalance > 0) {
                  mint.setBalance(totalBalance);
                  if(balance.getUtxos() != null && balance.getUtxos().size() > 0){
                     String tx = balance.getUtxos().get(0).getTx_id();
                     logger.info("Setting user transaction " + tx + " to mint " + mint.getId());
                     mint.setUserTransaction(balance.getUtxos().get(0).getTx_id());
                  } else {
                     logger.warn("Unable to set user transaction for mint " + mint.getId());
                  }
                  retryTemplate.execute(context -> {
                     mintRepository.save(mint);
                     return null;
                  });
               }
               if (totalBalance >= PRICE_HTR * mint.getCount()) {
                  logger.info("Balance is good, initializing trees");
                  if(initTrees(mint)) {
                     logger.info("Setting mint " + mint.getId() + " state to SENDING_NFT");
                     mint.setState(MintState.SENDING_NFT.ordinal());
                     retryTemplate.execute(context -> {
                        mintRepository.save(mint);
                        return null;
                     });
                     send(mint);
                  }
               }
               if (totalBalance == 0) {
                  Date created = mint.getCreatedAt();
                  Date now = new Date();

                  long diff = now.getTime() - created.getTime();
                  long hours = TimeUnit.MILLISECONDS.toHours(diff);
                  if(hours >= 2) {
                     logger.info("Setting mint " + mint.getId() + " as dead");
                     mint.setDead(true);
                     mintRepository.save(mint);
                  }
               }
            }
            else {
               send(mint);
            }
            Thread.sleep(3000);
         } catch (Exception ex) {
            logger.error("Mint failed " + mint.getId(), ex);
         }
         logger.info("-----------------------------------------------------------------------------");
      }
      logger.info("Loop ended");
   }

   private boolean initTrees(Mint mint) {
      try {
         if (mint.getTrees() == null || mint.getTrees().isEmpty()) {
            List<SmallTree> trees = new ArrayList<>();
            logger.info("Finding trees");
            try {
               retryTemplate.execute(context -> {
                  trees.addAll(smallTreeRepository.findNotTaken(mint.getCount()));
                  return null;
               });
            } catch (Exception ex) {
               logger.error("Failed to initialize tree for mint " + mint.getId());
               return false;
            }

            if(trees == null || trees.size() < mint.getCount()) {
               logger.warn("We are out of trees " + mint.getId());
               mint.setState(MintState.OUT_OF_NFTS.ordinal());
               retryTemplate.execute(context -> {
                  mintRepository.save(mint);
                  return null;
               });
               return false;
            }
            int treesCount = 0;
            for (SmallTree s : trees) {
               logger.info("Setting tree " + s.getId() + " as taken!");
               s.setTaken(true);
               s.setMint(mint);
               treesCount += s.getTreeAttributes().getTreesPlanted();
            }

            logger.info("Saving trees");
            retryTemplate.execute(context -> {
               smallTreeRepository.saveAll(trees);
               return null;
            });

            mint.setTrees(new HashSet<>(trees));
            mint.setTreesCount(treesCount);
            logger.info("Saving mint " + mint.getId());
            retryTemplate.execute(context -> {
               mintRepository.save(mint);
               return null;
            });
            Team team = mint.getTeam();
            if(team == null) {
               team = teamRepository.findById(NO_TEAM_ID).get();
            }
            final Team fTeam = team;
            logger.info("Setting trees count to team " + fTeam.getName() + " to " + (fTeam.getTreesCount() + treesCount));
            fTeam.setTreesCount(fTeam.getTreesCount() + treesCount);
            retryTemplate.execute(context -> {
               teamRepository.save(fTeam);
               return null;
            });
         }
      } catch (Exception ex) {
         logger.error("Failed to init trees for mint " + mint.getId(), ex);
         return false;
      }
      return true;
   }

   private void send(Mint mint) throws Exception {
      logger.info("Sending NFT for mint " + mint.getId());
      List<String> tokens = new ArrayList<>();
      for(SmallTree tree : mint.getTrees()) {
         tokens.add(tree.getToken());
      }
      String transactionHash = walletService.sendTokens(mint.getUserAddress().trim(), tokens);
      if(transactionHash != null) {
         logger.info("Transaction hash " + transactionHash);
         mint.setTransaction(transactionHash);
         mint.setTransactionDate(new Date());
         mint.setState(MintState.NFT_SENT.ordinal());
         logger.info("Setting mint " + mint.getId() + " state to NFT_SENT");
         try {
            retryTemplate.execute(context -> {
               mintRepository.save(mint);
               return null;
            });
         } catch (Exception ex) {
            logger.error("FATAL! Could not save mint " + mint.getId() + " to state NFT_SENT when TREES were sent!", ex);
         }

         if (mint.getEmail() != null) {
            mailService.sendMail(mint);
         }
      }
   }

   private void sendDepositBack(Mint mint) {
      if(mint.getBalance() != null && mint.getBalance() > 0) {
         logger.info("Sending deposit back for mint " + mint.getId());
         String transactionHash = walletService.sendHtrFromReceive(mint.getUserAddress(), mint.getBalance());
         if (transactionHash != null) {
            logger.info("Transaction hash " + transactionHash);
            mint.setSendBackTransaction(transactionHash);
            mint.setState(MintState.HTR_SENT_BACK.ordinal());
            logger.info("Setting mint " + mint.getId() + " state to HTR_SENT_BACK");
            try {
               retryTemplate.execute(context -> {
                  mintRepository.save(mint);
                  return null;
               });
            } catch (Exception ex) {
               logger.error("FATAL! Could not save mint " + mint.getId() + " to state HTR_SENT_BACK when HTR was sent back!", ex);
            }
         }
      }
   }


}
