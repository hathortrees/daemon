package com.hathor.daemon.data.entities;

import javax.persistence.*;

@Entity
public class BigTree {

   @Id
   private Integer id;

   @OneToOne
   private SmallTree smallTree;

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

   public SmallTree getSmallTree() {
      return smallTree;
   }

   public void setSmallTree(SmallTree smallTree) {
      this.smallTree = smallTree;
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
