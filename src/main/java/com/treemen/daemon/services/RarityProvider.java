package com.treemen.daemon.services;

import com.google.gson.Gson;
import com.treemen.daemon.services.nft.AttributeType;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class RarityProvider {

   private final Gson gson;
   private Map<String, Map<String, Double>> rarityTable = null;

   public RarityProvider() {
      this.gson = new Gson();
      try {
         InputStream rarityIS = getClass().getClassLoader().getResourceAsStream("rarity/count.json");
         String rarityText = IOUtils.toString(rarityIS, StandardCharsets.UTF_8.name());
         this.rarityTable = gson.fromJson(rarityText, Map.class);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public Integer getRarity(AttributeType type, String asset) {
      Map<String, Double> rarityMap = rarityTable.get(type.getName());
      return rarityMap.get(asset).intValue();
   }
}
