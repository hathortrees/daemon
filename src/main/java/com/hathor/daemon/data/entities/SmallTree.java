package com.hathor.daemon.data.entities;

import javax.persistence.*;

@Entity
public class SmallTree {

   @Id
   private Integer id;

   private boolean taken;

   @ManyToOne
   private Mint mint;

   @OneToOne
   private BigTree bigTree;

   private String ipfs;

   @Column(unique = true)
   private String token;

   @OneToOne
   @JoinColumn(name = "tree_attributes_id", referencedColumnName = "id")
   private TreeAttributes treeAttributes;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Mint getMint() {
      return mint;
   }

   public void setMint(Mint mint) {
      this.mint = mint;
   }

   public boolean isTaken() {
      return taken;
   }

   public void setTaken(boolean taken) {
      this.taken = taken;
   }

   public BigTree getBigTree() {
      return bigTree;
   }

   public void setBigTree(BigTree bigTree) {
      this.bigTree = bigTree;
   }

   public String getIpfs() {
      return ipfs;
   }

   public void setIpfs(String ipfs) {
      this.ipfs = ipfs;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public TreeAttributes getTreeAttributes() {
      return treeAttributes;
   }

   public void setTreeAttributes(TreeAttributes treeAttributes) {
      this.treeAttributes = treeAttributes;
   }
}
