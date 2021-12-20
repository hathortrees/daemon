package com.treemen.daemon.services.nft;

public enum AttributeType {

   TREE("Tree"),
   BACKGROUND("Background"),
   EYES("Eyes"),
   SKIN("Skin"),
   MOUTH("Mouth"),
   CLOTHES("Clothes"),
   ACCESSORY("Accessory");

   AttributeType(String name){
      this.name = name;
   }

   private String name;

   public String getName() {
      return name;
   }
}
