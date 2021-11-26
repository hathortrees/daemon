package com.hathor.daemon.data.entities;

import javax.persistence.*;

@Entity
public class Tree {

   @Id
   private Integer id;

   @Column(unique = true)
   private String picture;

   private String ipfs;

   @Column(unique = true)
   private String token;

   private boolean taken;

   @ManyToOne
   private Mint mint;

   @OneToOne
   @JoinColumn(name = "tree_attributes_id", referencedColumnName = "id")
   private TreeAttributes treeAttributes;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getPicture() {
      return picture;
   }

   public void setPicture(String picture) {
      this.picture = picture;
   }

   public boolean isTaken() {
      return taken;
   }

   public void setTaken(boolean taken) {
      this.taken = taken;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public Mint getMint() {
      return mint;
   }

   public void setMint(Mint mint) {
      this.mint = mint;
   }

   public String getIpfs() {
      return ipfs;
   }

   public void setIpfs(String ipfs) {
      this.ipfs = ipfs;
   }

   public TreeAttributes getTreeAttributes() {
      return treeAttributes;
   }

   public void setTreeAttributes(TreeAttributes treeAttributes) {
      this.treeAttributes = treeAttributes;
   }
}
