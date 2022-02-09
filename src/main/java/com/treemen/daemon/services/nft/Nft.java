package com.treemen.daemon.services.nft;

import java.util.List;

public class Nft {

   private String name;
   private String description;
   private String file;
   private Integer overall_rarity;
   private List<Attribute> attributes;

   public String getAttributeValue(AttributeType type) {
      for (Attribute attribute : attributes) {
         if(attribute.getType().equals(type.getName())) {
            return attribute.getValue();
         }
      }
      return null;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getFile() {
      return file;
   }

   public void setFile(String file) {
      this.file = file;
   }

   public List<Attribute> getAttributes() {
      return attributes;
   }

   public void setAttributes(List<Attribute> attributes) {
      this.attributes = attributes;
   }

   public Integer getOverall_rarity() {
      return overall_rarity;
   }

   public void setOverall_rarity(Integer overall_rarity) {
      this.overall_rarity = overall_rarity;
   }
}
