package com.treemen.daemon.services.nft;

public class Attribute {

   private String type;
   private String value;
   private int rarity;

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public int getRarity() {
      return rarity;
   }

   public void setRarity(int rarity) {
      this.rarity = rarity;
   }
}
