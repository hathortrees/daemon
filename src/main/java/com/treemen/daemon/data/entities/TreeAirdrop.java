package com.treemen.daemon.data.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mai_tree_tree_airdrops")
public class TreeAirdrop {

   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @ManyToOne
   @JoinColumn(name = "airdrop_id", referencedColumnName = "id")
   private Airdrop airdrop;

   @ManyToOne
   @JoinColumn(name = "small_tree_id", referencedColumnName = "id")
   private SmallTree smallTree;

   private String address;

   private Long htrAmount;

   private String transaction;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Airdrop getAirdrop() {
      return airdrop;
   }

   public void setAirdrop(Airdrop airdrop) {
      this.airdrop = airdrop;
   }

   public SmallTree getSmallTree() {
      return smallTree;
   }

   public void setSmallTree(SmallTree smallTree) {
      this.smallTree = smallTree;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public Long getHtrAmount() {
      return htrAmount;
   }

   public void setHtrAmount(Long htrAmount) {
      this.htrAmount = htrAmount;
   }

   public String getTransaction() {
      return transaction;
   }

   public void setTransaction(String transaction) {
      this.transaction = transaction;
   }
}
