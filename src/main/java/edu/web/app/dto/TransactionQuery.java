package edu.web.app.dto;

import edu.web.app.entity.TransactionStatus;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TransactionQuery {
   private Timestamp createdAt = Timestamp.from(Instant.now().minus(2, ChronoUnit.DAYS));
   private Timestamp completeAt = Timestamp.from(Instant.now());
   private TransactionStatus transactionStatus;
   private String cardFrom;
   private String cardTo;
   private int limit = 10;

   public Timestamp getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Timestamp createdAt) {
      this.createdAt = createdAt;
   }

   public Timestamp getCompleteAt() {
      return completeAt;
   }

   public void setCompleteAt(Timestamp completeAt) {
      this.completeAt = completeAt;
   }

   public TransactionStatus getTransactionStatus() {
      return transactionStatus;
   }

   public void setTransactionStatus(TransactionStatus transactionStatus) {
      this.transactionStatus = transactionStatus;
   }

   public String getCardFrom() {
      return cardFrom;
   }

   public void setCardFrom(String cardFrom) {
      this.cardFrom = cardFrom;
   }

   public String getCardTo() {
      return cardTo;
   }

   public void setCardTo(String cardTo) {
      this.cardTo = cardTo;
   }

   public int getLimit() {
      return limit;
   }

   public void setLimit(int limit) {
      this.limit = limit;
   }
}
