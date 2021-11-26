package com.hathor.daemon.data.entities;

import javax.persistence.*;

@Entity
public class TreeAttributes {

   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   private Integer id;

   private String treeType;

   private String background;

   private String eyes;

   private String nose;

   private String mouth;

   private String hair;

   private String accessory;

   @OneToOne(mappedBy = "treeAttributes")
   private Tree tree;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getTreeType() {
      return treeType;
   }

   public void setTreeType(String treeType) {
      this.treeType = treeType;
   }

   public String getBackground() {
      return background;
   }

   public void setBackground(String background) {
      this.background = background;
   }

   public String getEyes() {
      return eyes;
   }

   public void setEyes(String eyes) {
      this.eyes = eyes;
   }

   public String getNose() {
      return nose;
   }

   public void setNose(String nose) {
      this.nose = nose;
   }

   public String getMouth() {
      return mouth;
   }

   public void setMouth(String mouth) {
      this.mouth = mouth;
   }

   public String getHair() {
      return hair;
   }

   public void setHair(String hair) {
      this.hair = hair;
   }

   public String getAccessory() {
      return accessory;
   }

   public void setAccessory(String accessory) {
      this.accessory = accessory;
   }

   public Tree getTree() {
      return tree;
   }

   public void setTree(Tree tree) {
      this.tree = tree;
   }
}
