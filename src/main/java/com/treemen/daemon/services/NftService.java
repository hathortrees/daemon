package com.treemen.daemon.services;

import com.google.gson.Gson;
import com.treemen.daemon.data.entities.SmallTree;
import com.treemen.daemon.data.entities.TreeAttributes;
import com.treemen.daemon.data.repositories.TreeAttributesRepository;
import com.treemen.daemon.data.repositories.SmallTreeRepository;
import com.treemen.daemon.services.nft.AttributeType;
import com.treemen.daemon.services.nft.Nft;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class NftService {

   private static final String JSON_IPFS = "QmYBBimXBZgjkTrBANpbgRqDuMuqNhqqp3x7J5Dxq4GqWK";
   private static final String JSON_IPFS_FIX = "QmaSXU5WaycWb4XzfpoEcFtXd6UnJyez8CDqJZVujZKeGo";
   private static final String IMAGE_IPFS = "QmZKK4cMdEvaZjrPU35rbMFCdFKg5tojBzqCDN7TzT9gre";
   private static final int NFT_COUNT = 6371;

   private final WalletService walletService;
   private final SmallTreeRepository smallTreeRepository;
   private final TreeAttributesRepository treeAttributesRepository;
   private final Gson gson;

   Logger logger = LoggerFactory.getLogger(NftService.class);

   public NftService(WalletService walletService, SmallTreeRepository smallTreeRepository,
                     TreeAttributesRepository treeAttributesRepository) {
      this.walletService = walletService;
      this.smallTreeRepository = smallTreeRepository;
      this.treeAttributesRepository = treeAttributesRepository;

      this.gson = new Gson();
   }

   //@PostConstruct
   public void init() {
      //generateNfts();
   }

   private String createNft(int i, SmallTree s) throws Exception {
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generator/metadata/" + i + ".json");
      String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
      Nft nft = gson.fromJson(text, Nft.class);

      String ipfsJsonUrl = "ipfs://ipfs/" + JSON_IPFS + "/" + i + ".json";
      if(i == 445 || i == 1316 || i == 4900) {
         ipfsJsonUrl = "ipfs://ipfs/" + JSON_IPFS_FIX + "/" + i + ".json";
      }

      String hash = null;
      String tokenSymbol = "T" + i;

      while(hash == null) {
         hash = walletService.createNft("TreeMen " + i, tokenSymbol, ipfsJsonUrl);
         if(hash == null) {
            try {
               walletService.checkWallets();
               Thread.sleep(10000);
            } catch (Exception ignored) {

            }
         }
      }
      return hash;
   }

   public void generateNfts() {
      List<Integer> failed = new ArrayList<>();
      for (int i = 1; i <= NFT_COUNT; i++) {
         try {
            logger.info("Creating NFT " + i);
            try {
               walletService.checkWallets();
            } catch (Exception ex) {
               logger.error("Unable to check wallets, exiting!", ex);
               break;
            }

            Integer htrBalance = walletService.checkHtrBalance(true);
            if (htrBalance != null) {
               logger.info("HTR Balance is " + htrBalance);
               if(htrBalance == 0) {
                  logger.info("Balance is 0, exiting");
                  break;
               }
            }

            boolean processed = false;
            Optional<SmallTree> t = smallTreeRepository.findById(i);
            if (t.isPresent()) {
               SmallTree tree = t.get();
               if(tree.getToken().contains("hash")) {
                  String hash = createNft(i, tree);
                  tree.setToken(hash);
                  smallTreeRepository.save(tree);
                  processed = true;
                  logger.info("Successfully created NFT " + i);
               } else {
                  logger.info("Skipping " + i + " already has token " + tree.getToken());
               }
            }

            if(processed) {
               Thread.sleep(3000);
            }
         } catch (Exception ex) {
            logger.error("Could not create NFT " + i, ex);
            failed.add(i);
         }
      }

      if(failed.size() > 0) {
         logger.error("FAILED TREES");
         for(Integer i : failed) {
            logger.info(i.toString());
         }
      }
   }

   public void generateDatabaseNfts() {
      for(int i = 1; i <= NFT_COUNT; i++) {
         try {
            Optional<SmallTree> t = smallTreeRepository.findById(i);
            if(t.isPresent()) {
               logger.info("NFT " + i + " already exists");
               continue;
            }

            logger.info("Creating NFT " + i);

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generator/metadata/" + i + ".json");
            String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
            Nft nft = gson.fromJson(text, Nft.class);

            String ipfsJsonUrl = "ipfs://ipfs/" + JSON_IPFS + "/" + i + ".json";
            String ipfsPublicUrl = "https://ipfs.io/ipfs/" + IMAGE_IPFS + "/" + i + ".png";

            String hash = null;
            String tokenSymbol = "T" + i;

            hash = "hash" + i;

            SmallTree tree = new SmallTree();
            tree.setId(i);
            tree.setToken(hash);
            tree.setTaken(false);
            tree.setIpfs(ipfsPublicUrl);

            TreeAttributes attributes = new TreeAttributes();
            attributes.setTree(nft.getAttributeValue(AttributeType.TREE));
            attributes.setBackground(nft.getAttributeValue(AttributeType.BACKGROUND));
            attributes.setEyes(nft.getAttributeValue(AttributeType.EYES));
            attributes.setBody(nft.getAttributeValue(AttributeType.BODY));
            attributes.setMouth(nft.getAttributeValue(AttributeType.MOUTH));
            attributes.setClothes(nft.getAttributeValue(AttributeType.CLOTHES));
            attributes.setAccessory(nft.getAttributeValue(AttributeType.ACCESSORY));
            attributes.setTreesPlanted(nft.getOverall_rarity());

            treeAttributesRepository.save(attributes);

            tree.setTreeAttributes(attributes);
            smallTreeRepository.save(tree);

            logger.info("Successfully created NFT " + i);

         } catch (Exception ex) {
            logger.error("Could not create NFT " + i, ex);
         }
      }
   }
}
