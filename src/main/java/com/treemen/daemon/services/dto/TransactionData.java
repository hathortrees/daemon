package com.treemen.daemon.services.dto;

public class TransactionData {

   private String token;
   private Decoded decoded;

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public Decoded getDecoded() {
      return decoded;
   }

   public void setDecoded(Decoded decoded) {
      this.decoded = decoded;
   }
}
