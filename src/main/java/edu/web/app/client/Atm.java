package edu.web.app.client;

import edu.web.app.entity.CardEntity;
import edu.web.app.session.CardSessionBean;

import java.math.BigDecimal;
import java.util.List;

public class Atm {
   public enum AtmOption {
      WITHDRAWAL, TRANSFER, BALANCE, REPLENISH
   }

   private AtmState state = new AtmStateStarted();
   private BigDecimal current = BigDecimal.valueOf(100_000);
   private String message;
   private AtmOption option;
   private final CardEntity card;
   private String pincode;
   private BigDecimal withdrawal;
   private int attempts = 3;
   private List<String> links;
   private String toCard;
   private final CardSessionBean cardSessionBean;

   public Atm(CardEntity card, CardSessionBean cardSessionBean) {
      this.card = card;
      this.cardSessionBean = cardSessionBean;
   }

   public AtmState getState() {
      return state;
   }

   public void previousState() {
      state.prev(this);
   }

   public void nextState() {
      state.next(this);
   }

   public void exec() {
      state.exec(this);
   }

   public BigDecimal getCurrent() {
      return current;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public void setCurrent(BigDecimal current) {
      this.current = current;
   }

   public AtmOption getOption() {
      return option;
   }

   public void setOption(AtmOption option) {
      this.option = option;
   }

   public void setState(AtmState state) {
      this.state = state;
   }

   public CardEntity getCard() {
      return card;
   }

   public void setPincode(String pincode) {
      this.pincode = pincode;
   }

   public String getPincode() {
      return pincode;
   }

   public int getAttempts() {
      return attempts;
   }

   public void setAttempts(int attempts) {
      this.attempts = attempts;
   }

   public List<String> getLinks() {
      return links;
   }

   public void setLinks(List<String> links) {
      this.links = links;
   }

   public BigDecimal getWithdrawal() {
      return withdrawal;
   }

   public void setWithdrawal(BigDecimal withdrawal) {
      this.withdrawal = withdrawal;
   }

   public String getToCard() {
      return toCard;
   }

   public void setToCard(String toCard) {
      this.toCard = toCard;
   }

   public CardSessionBean getCardSessionBean() {
      return cardSessionBean;
   }

   public void clear() {
      this.setState(new AtmStateStarted());
      this.setMessage(null);
      this.setLinks(null);
      this.setOption(null);
      this.setAttempts(3);
      this.setPincode(null);
      this.setToCard(null);
      this.setWithdrawal(null);
   }
}
