package com.treemen.daemon.data.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mai_tree_mints")
public class Mint {
   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
   private String id;

   private Integer balance;

   private int count = 1;

   private boolean dead;

   private String sendBackTransaction;

   private int state;

   private String transaction;

   private Date transactionDate;

   private String userAddress;

   private String userTransaction;

   @ManyToOne
   @JoinColumn(name = "deposit_address_id", referencedColumnName = "id")
   private Address depositAddress;

   @ManyToOne
   @JoinColumn(name = "team_id", referencedColumnName = "id")
   private Team team;

   private String email;

   private String name;

   private String giftEmail;

   private String giftName;

   private boolean gift;

   private Date createdAt;

   private Date updatedAt;

   private String message;

   private Integer treesCount;

   @OneToMany(mappedBy="mint", fetch = FetchType.EAGER)
   private Set<SmallTree> trees;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public int getState() {
      return state;
   }

   public void setState(int state) {
      this.state = state;
   }

   public Address getDepositAddress() {
      return depositAddress;
   }

   public void setDepositAddress(Address depositAddress) {
      this.depositAddress = depositAddress;
   }

   public String getUserAddress() {
      return userAddress;
   }

   public void setUserAddress(String userAddress) {
      this.userAddress = userAddress;
   }

   public Set<SmallTree> getTrees() {
      return trees;
   }

   public void setTrees(Set<SmallTree> trees) {
      this.trees = trees;
   }

   public boolean isDead() {
      return dead;
   }

   public void setDead(boolean dead) {
      this.dead = dead;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getGiftEmail() {
      return giftEmail;
   }

   public void setGiftEmail(String giftEmail) {
      this.giftEmail = giftEmail;
   }

   public String getGiftName() {
      return giftName;
   }

   public void setGiftName(String giftName) {
      this.giftName = giftName;
   }

   public boolean isGift() {
      return gift;
   }

   public void setGift(boolean gift) {
      this.gift = gift;
   }

   public Date getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
   }

   public Date getUpdatedAt() {
      return updatedAt;
   }

   public void setUpdatedAt(Date updatedAt) {
      this.updatedAt = updatedAt;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getTransaction() {
      return transaction;
   }

   public void setTransaction(String transaction) {
      this.transaction = transaction;
   }

   public String getSendBackTransaction() {
      return sendBackTransaction;
   }

   public void setSendBackTransaction(String sendBackTransaction) {
      this.sendBackTransaction = sendBackTransaction;
   }

   public Date getTransactionDate() {
      return transactionDate;
   }

   public void setTransactionDate(Date transactionDate) {
      this.transactionDate = transactionDate;
   }

   public Integer getBalance() {
      return balance;
   }

   public void setBalance(Integer balance) {
      this.balance = balance;
   }

   public int getCount() {
      return count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public Team getTeam() {
      return team;
   }

   public void setTeam(Team team) {
      this.team = team;
   }

   public String getUserTransaction() {
      return userTransaction;
   }

   public void setUserTransaction(String userTransaction) {
      this.userTransaction = userTransaction;
   }

   public Integer getTreesCount() {
      return treesCount;
   }

   public void setTreesCount(Integer treesCount) {
      this.treesCount = treesCount;
   }
}
