package com.treemen.daemon.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "mai_tree_small_trees")
public class SmallTree {

   @Id
   private Integer id;

   private String ipfs;

   private boolean taken;

   @Column(unique = true)
   private String token;

   @ManyToOne
   @JoinColumn(name = "mint_id", referencedColumnName = "id")
   private Mint mint;

   @OneToOne
   @JoinColumn(name = "big_tree_id", referencedColumnName = "id")
   private BigTree bigTree;

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
