package edu.web.app.dto;

import edu.web.app.entity.CardType;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CardDto {
   private Long id;
   private Integer cvv;
   private String cardNumber;
   private BigDecimal currentAmount;
   private String currency;
   private CardType type;
   private Timestamp createdAt;
   private Timestamp expireDate;
   private Boolean active;
   private String pinCode;
   private String meta;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Integer getCvv() {
      return cvv;
   }

   public void setCvv(Integer cvv) {
      this.cvv = cvv;
   }

   public String getCardNumber() {
      return cardNumber;
   }

   public void setCardNumber(String cardNumber) {
      this.cardNumber = cardNumber;
   }

   public BigDecimal getCurrentAmount() {
      return currentAmount;
   }

   public void setCurrentAmount(BigDecimal currentAmount) {
      this.currentAmount = currentAmount;
   }

   public String getCurrency() {
      return currency;
   }

   public void setCurrency(String currency) {
      this.currency = currency;
   }

   public CardType getType() {
      return type;
   }

   public void setType(CardType type) {
      this.type = type;
   }

   public Timestamp getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Timestamp createdAt) {
      this.createdAt = createdAt;
   }

   public Timestamp getExpireDate() {
      return expireDate;
   }

   public void setExpireDate(Timestamp expireDate) {
      this.expireDate = expireDate;
   }

   public Boolean getActive() {
      return active;
   }

   public void setActive(Boolean active) {
      this.active = active;
   }

   public String getPinCode() {
      return pinCode;
   }

   public void setPinCode(String pinCode) {
      this.pinCode = pinCode;
   }

   public String getMeta() {
      return meta;
   }

   public void setMeta(String meta) {
      this.meta = meta;
   }
}
