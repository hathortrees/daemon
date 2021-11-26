package com.hathor.daemon.services.nft;

public enum AttributeType {

   TREE_TYPE("TreeType"),
   BACKGROUND("Background"),
   EYES("Eyes"),
   NOSE("Nose"),
   MOUTH("Mouth"),
   HAIR("Hair"),
   ACCESSORY("Accessory");

   AttributeType(String name){
      this.name = name;
   }

   private String name;

   public String getName() {
      return name;
   }
}
