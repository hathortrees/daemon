package com.treemen.daemon.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "mai_tree_tree_attributes")
public class TreeAttributes {

   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   private Integer id;

   private String accessory;

   private String background;

   private String eyes;

   private String mouth;

   private String tree;

   private String body;

   private String clothes;

   private int treesPlanted;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getAccessory() {
      return accessory;
   }

   public void setAccessory(String accessory) {
      this.accessory = accessory;
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

   public String getMouth() {
      return mouth;
   }

   public void setMouth(String mouth) {
      this.mouth = mouth;
   }

   public String getTree() {
      return tree;
   }

   public void setTree(String tree) {
      this.tree = tree;
   }

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }

   public String getClothes() {
      return clothes;
   }

   public void setClothes(String clothes) {
      this.clothes = clothes;
   }

   public int getTreesPlanted() {
      return treesPlanted;
   }

   public void setTreesPlanted(int treesPlanted) {
      this.treesPlanted = treesPlanted;
   }
}
