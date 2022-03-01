package com.treemen.daemon.services;

import com.treemen.daemon.data.entities.SmallTree;
import com.treemen.daemon.data.entities.Team;
import com.treemen.daemon.data.repositories.SmallTreeRepository;
import com.treemen.daemon.data.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialCasesService {

   private final WalletService walletService;
   private final SmallTreeRepository smallTreeRepository;
   private final TeamRepository teamRepository;
   private final static int NO_TEAM_ID = 5;

   public SpecialCasesService(WalletService walletService, SmallTreeRepository smallTreeRepository, TeamRepository teamRepository) {
      this.walletService = walletService;
      this.smallTreeRepository = smallTreeRepository;
      this.teamRepository = teamRepository;
   }

   //@PostConstruct
   public void init() {
      sendMultipleNfts("HG4eeCzuNmA5SZxCQWQWgjcTwFx1BJ6Fqi", Arrays.asList(1936), false);
      //sendRandomNft("HQHkkHT6dZLLWsxMThBc6GGaovzKxBs3C9", 2);
   }

   private void sendRandomNft(String address, int count) {
      List<SmallTree> trees = new ArrayList<>();
      for(int i = 0; i < count; i++) {
         int rarity = 50;
         SmallTree tree = null;
         while (rarity > 20) {
            tree = smallTreeRepository.findNotTaken(1).get(0);
            rarity = tree.getTreeAttributes().getTreesPlanted();
         }
         trees.add(tree);
      }
      if(trees.size() > 0) {
         List<String> tokens = trees.stream().map(nft -> nft.getToken()).collect(Collectors.toList());
         String hash = walletService.sendTokens(address, tokens);
         if(hash != null) {
            System.out.println("Transaction " + hash);
            for(SmallTree tree : trees) {
               System.out.println("https://tree-men.com/explorer/" + tree.getId());
               tree.setTaken(true);
            }
            smallTreeRepository.saveAll(trees);
         }
         int treesCount = 0;
         for(SmallTree tree : trees) {
            treesCount += tree.getTreeAttributes().getTreesPlanted();
         }
         Team noTeam = teamRepository.findById(NO_TEAM_ID).get();
         noTeam.setTreesCount(noTeam.getTreesCount() + treesCount);
         teamRepository.save(noTeam);
      }
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
