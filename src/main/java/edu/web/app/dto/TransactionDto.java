package edu.web.app.dto;

import edu.web.app.entity.TransactionStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionDto {
   private Long id;
   private Timestamp createdAt;
   private Timestamp completeAt;
   private TransactionStatus status;
   private String location;
   private String meta;
   private String currency;
   private BigDecimal amount;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

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

   public TransactionStatus getStatus() {
      return status;
   }

   public void setStatus(TransactionStatus status) {
      this.status = status;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public String getMeta() {
      return meta;
   }

   public void setMeta(String meta) {
      this.meta = meta;
   }

   public String getCurrency() {
      return currency;
   }

   public void setCurrency(String currency) {
      this.currency = currency;
   }

   public BigDecimal getAmount() {
      return amount;
   }

   public void setAmount(BigDecimal amount) {
      this.amount = amount;
   }
}
