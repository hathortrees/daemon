package com.hathor.daemon.services;

import com.hathor.daemon.data.entities.SmallTree;
import com.hathor.daemon.data.repositories.TreeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialCasesService {

   private final WalletService walletService;
   private final TreeRepository treeRepository;


   public SpecialCasesService(WalletService walletService, TreeRepository treeRepository) {
      this.walletService = walletService;
      this.treeRepository = treeRepository;
   }

   public void createSpecialNft() {
      String hash = walletService.createNft("Special Hathor Tree", "HTS1", "https://ipfs.io/ipfs/QmbRTsuXcd2hMbPigXkcfn3T7eGP9WjbhbeRC9Mb5NK8YZ");

      SmallTree s = treeRepository.findById(12001).get();

      s.setToken(hash);
      s.setIpfs("https://ipfs.io/ipfs/QmZYexZmezMoKfMxQ96b4wANXN8CbcpCDyPC8ekpBps1xx");
      treeRepository.save(s);
   }

   public void sendMultipleNfts(String address, List<Integer> numbers, boolean checkTaken) {
      Iterable<SmallTree> trees = treeRepository.findAllById(numbers);
      List<SmallTree> treeList = new ArrayList<>();
      for(SmallTree s : trees) {
         if(checkTaken) {
            if (!s.isTaken()) {
               treeList.add(s);
            }
         } else {
            treeList.add(s);
         }
      }

      List<String> tokens = treeList.stream().map(tree -> tree.getToken()).collect(Collectors.toList());
      String hash = walletService.sendTokens(address, tokens);
      if(hash != null) {
         System.out.println(hash);
         for(SmallTree tree : treeList) {
            System.out.println("https://www.hathortrees.com/explorer.html?id=" + tree.getId());
            tree.setTaken(true);
         }
         treeRepository.saveAll(treeList);
      }
   }
}
