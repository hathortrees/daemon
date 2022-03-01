package com.treemen.daemon.services.dto;

import java.util.List;

public class TokenHistory {

   private boolean success;
   private List<TokenTransaction> transactions;

   public boolean isSuccess() {
      return success;
   }

   public void setSuccess(boolean success) {
      this.success = success;
   }

   public List<TokenTransaction> getTransactions() {
      return transactions;
   }

   public void setTransactions(List<TokenTransaction> transactions) {
      this.transactions = transactions;
   }
}
