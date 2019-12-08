package edu.web.app.entity;

import edu.web.app.dto.TransactionDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "T_TRANSACTION_PAYMENT")
public class PaymentTransactionEntity implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
   private Long id;
   @CreationTimestamp
   @Column(name = "created_at")
   private Timestamp createdAt;
   @UpdateTimestamp
   @Column(name = "complete_at")
   private Timestamp completeAt;
   @Enumerated(value = EnumType.STRING)
   @Column(name = "status")
   private TransactionStatus status;
   @Column(name = "location")
   private String location;
   @Column(name = "meta")
   private String meta;
   @Column(name = "currency")
   private String currency;
   @Column(name = "amount")
   private BigDecimal amount;

   /**
    *
    */
   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "sender_id")
   private CardEntity sender;

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "receiver_id")
   private CardEntity receiver;

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

   public CardEntity getSender() {
      return sender;
   }

   public void setSender(CardEntity sender) {
      this.sender = sender;
   }

   public CardEntity getReceiver() {
      return receiver;
   }

   public void setReceiver(CardEntity receiver) {
      this.receiver = receiver;
   }

   public TransactionDto transactionDto() {
      TransactionDto transactionDto = new TransactionDto();
      transactionDto.setAmount(amount);
      transactionDto.setCompleteAt(completeAt);
      transactionDto.setCreatedAt(createdAt);
      transactionDto.setCurrency(currency);
      transactionDto.setId(id);
      transactionDto.setLocation(location);
      transactionDto.setMeta(meta);
      transactionDto.setStatus(status);

      return transactionDto;
   }
}
