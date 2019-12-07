package edu.web.app.entity;

import edu.web.app.dto.CardDto;
import org.hibernate.annotations.CreationTimestamp;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "T_BANK_CARD")
public class CardEntity implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
   private Long id;
   @Column(name = "cvv")
   private Integer cvv;
   @Column(name = "card_number")
   private String cardNumber;
   @Column(name = "current_amount")
   private BigDecimal currentAmount;
   @Column(name = "currency")
   private String currency;
   @Column(name = "type")
   @Enumerated(value = EnumType.STRING)
   private CardType type;
   @CreationTimestamp
   @Column(name = "created_at")
   private Timestamp createdAt;
   @Column(name = "expire_date")
   private Timestamp expireDate;
   @Column(name = "active")
   private Boolean active;
   @Column(name = "pin_code")
   private String pinCode;
   @Column(name = "meta")
   private String meta;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "client_id")
   private ClientIdentityEntity owner;

   @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
   private List<PaymentTransactionEntity> outcomes;

   @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
   private List<PaymentTransactionEntity> incomes;

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

   public ClientIdentityEntity getOwner() {
      return owner;
   }

   public void setOwner(ClientIdentityEntity owner) {
      this.owner = owner;
   }

   public List<PaymentTransactionEntity> getOutcomes() {
      return outcomes;
   }

   public void setOutcomes(List<PaymentTransactionEntity> outcomes) {
      this.outcomes = outcomes;
   }

   public List<PaymentTransactionEntity> getIncomes() {
      return incomes;
   }

   public void setIncomes(List<PaymentTransactionEntity> incomes) {
      this.incomes = incomes;
   }

   public CardDto cardDto() {
      CardDto dto = new CardDto();
      dto.setActive(active);
      dto.setCardNumber(cardNumber);
      dto.setCreatedAt(createdAt);
      dto.setCurrency(currency);
      dto.setCurrentAmount(currentAmount);
      dto.setCvv(cvv);
      dto.setExpireDate(expireDate);
      dto.setId(id);
      dto.setMeta(meta);
      dto.setPinCode(pinCode);
      dto.setType(type);

      return dto;
   }
}
