package com.treemen.daemon.services;

import com.treemen.daemon.data.entities.SmallTree;
import com.treemen.daemon.data.repositories.SmallTreeRepository;
import com.treemen.daemon.services.dto.TokenHistory;
import com.treemen.daemon.services.dto.TokenTransaction;
import com.treemen.daemon.services.dto.TransactionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class DividendService {

   Logger logger = LoggerFactory.getLogger(DividendService.class);

   private final WalletService walletService;
   private final SmallTreeRepository smallTreeRepository;
   private final FullNodeService fullNodeService;

   public DividendService(WalletService walletService, SmallTreeRepository smallTreeRepository, FullNodeService fullNodeService) {
      this.walletService = walletService;
      this.smallTreeRepository = smallTreeRepository;
      this.fullNodeService = fullNodeService;
   }

   @PostConstruct
   public void run() {
      BigDecimal htrAmount = new BigDecimal("463");

      List<SmallTree> trees = smallTreeRepository.findByTakenIsTrue();
      Map<String, List<SmallTree>> treeMap = new HashMap<>();

      BigDecimal totalRarity = BigDecimal.ZERO;

      for(SmallTree tree : trees) {
         totalRarity = totalRarity.add(new BigDecimal(tree.getTreeAttributes().getTreesPlanted()));
         String address = null;
         while (address == null) {
            address = getAddressOfToken(tree);
         }
         if(treeMap.containsKey(address)) {
            treeMap.get(address).add(tree);
         }
         else{
            treeMap.put(address, new ArrayList<>());
            treeMap.get(address).add(tree);
         }
      }


      Map<String, BigDecimal> htrMap = new HashMap<>();
      for(String address : treeMap.keySet()) {
         List<SmallTree> treeList = treeMap.get(address);
         BigDecimal addressRarity = BigDecimal.ZERO;
         for(SmallTree tree : treeList) {
            addressRarity = addressRarity.add(new BigDecimal(tree.getTreeAttributes().getTreesPlanted()));
         }

         BigDecimal htrToSend = addressRarity.divide(totalRarity, 8, RoundingMode.HALF_UP).multiply(htrAmount);
         htrMap.put(address, htrToSend.setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(0));
      }

      logger.info(htrMap.toString());
   }

   private String getAddressOfToken(SmallTree tree) {
      TokenHistory tokenHistory = fullNodeService.getTokenHistory(tree.getToken());
      if(tokenHistory != null && tokenHistory.getTransactions() != null && tokenHistory.getTransactions().size() > 0) {
         TokenTransaction transaction = tokenHistory.getTransactions().get(0);
         if(transaction != null && transaction.getOutputs() != null && transaction.getOutputs().size() > 0) {
            Optional<TransactionData> data = transaction.getOutputs().stream().filter(it -> it.getToken().equals(tree.getToken())).findFirst();
            if(data.isPresent() && data.get().getDecoded() != null) {
               return data.get().getDecoded().getAddress();
            } else {
               logger.warn("Could not get address of tree " + tree.getId());
            }
         } else {
            logger.warn("Could not get address of tree " + tree.getId());
         }
      } else {
         logger.warn("Could not get address of tree " + tree.getId());
      }
      return null;
   }
}
