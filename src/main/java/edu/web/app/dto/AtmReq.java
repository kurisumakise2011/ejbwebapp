package edu.web.app.dto;

import edu.web.app.client.Atm;

import java.math.BigDecimal;

public class AtmReq {
   private Atm.AtmOption option;
   private BigDecimal amount;
   private String pincode;
   private String card;
   private String toCard;
   private Boolean prev;

   public Atm.AtmOption getOption() {
      return option;
   }

   public void setOption(Atm.AtmOption option) {
      this.option = option;
   }

   public String getCard() {
      return card;
   }

   public void setCard(String card) {
      this.card = card;
   }

   public String getToCard() {
      return toCard;
   }

   public void setToCard(String toCard) {
      this.toCard = toCard;
   }

   public BigDecimal getAmount() {
      return amount;
   }

   public void setAmount(BigDecimal amount) {
      this.amount = amount;
   }

   public String getPincode() {
      return pincode;
   }

   public void setPincode(String pincode) {
      this.pincode = pincode;
   }

   public Boolean getPrev() {
      return prev;
   }

   public void setPrev(Boolean prev) {
      this.prev = prev;
   }
}
