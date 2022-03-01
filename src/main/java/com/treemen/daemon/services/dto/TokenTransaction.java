package com.treemen.daemon.services.dto;

import java.util.List;

public class TokenTransaction {

   private String txId;
   private List<TransactionData> inputs;
   private List<TransactionData> outputs;

   public String getTxId() {
      return txId;
   }

   public void setTxId(String txId) {
      this.txId = txId;
   }

   public List<TransactionData> getInputs() {
      return inputs;
   }

   public void setInputs(List<TransactionData> inputs) {
      this.inputs = inputs;
   }

   public List<TransactionData> getOutputs() {
      return outputs;
   }

   public void setOutputs(List<TransactionData> outputs) {
      this.outputs = outputs;
   }
}
