package com.hathor.daemon.data.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Mint {
   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
   private String id;

   private int state;

   @ManyToOne
   private Address depositAddress;

   private String userAddress;

   @OneToMany(mappedBy="mint", fetch = FetchType.EAGER)
   private Set<SmallTree> trees;

   private boolean dead;

   private Date created;

   private String transaction;

   private String sendBackTransaction;

   private Date transactionDate;

   private Integer balance;

   private String mail;

   @ManyToOne
   private Team team;

   private int count = 1;

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

   public Date getCreated() {
      return created;
   }

   public void setCreated(Date created) {
      this.created = created;
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

   public String getMail() {
      return mail;
   }

   public void setMail(String mail) {
      this.mail = mail;
   }
}
