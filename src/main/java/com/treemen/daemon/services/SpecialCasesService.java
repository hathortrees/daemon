package com.treemen.daemon.services;

import com.treemen.daemon.data.entities.SmallTree;
import com.treemen.daemon.data.repositories.SmallTreeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialCasesService {

   private final WalletService walletService;
   private final SmallTreeRepository smallTreeRepository;


   public SpecialCasesService(WalletService walletService, SmallTreeRepository smallTreeRepository) {
      this.walletService = walletService;
      this.smallTreeRepository = smallTreeRepository;
   }

   public void createSpecialNft() {
      String hash = walletService.createNft("Special Hathor Tree", "HTS1", "https://ipfs.io/ipfs/QmbRTsuXcd2hMbPigXkcfn3T7eGP9WjbhbeRC9Mb5NK8YZ");

      SmallTree s = smallTreeRepository.findById(12001).get();

      s.setToken(hash);
      s.setIpfs("https://ipfs.io/ipfs/QmZYexZmezMoKfMxQ96b4wANXN8CbcpCDyPC8ekpBps1xx");
      smallTreeRepository.save(s);
   }

   public void sendMultipleNfts(String address, List<Integer> numbers, boolean checkTaken) {
      Iterable<SmallTree> trees = smallTreeRepository.findAllById(numbers);
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
         smallTreeRepository.saveAll(treeList);
      }
   }
}
