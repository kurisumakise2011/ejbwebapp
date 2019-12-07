package edu.web.app.entity;

import edu.web.app.dto.ClientDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "T_USER_IDENTITY")
public class ClientIdentityEntity implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
   private Long id;
   @Column(name = "name")
   private String name;
   @Column(name = "description")
   private String description;
   @CreationTimestamp
   @Column(name = "created_at")
   private Timestamp createdAt;
   @Column(name = "banned")
   private Boolean banned;
   @Column(name = "serial", unique = true, updatable = false, nullable = false)
   private String serial;
   @Column(name = "code", unique = true, updatable = false, nullable = false)
   private String code;
   @Column(name = "address")
   private String address;

   @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private List<CardEntity> cards;

   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "privacy_id", referencedColumnName = "id")
   private UserPrivacyEntity privacy;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Timestamp getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Timestamp createdAt) {
      this.createdAt = createdAt;
   }

   public Boolean getBanned() {
      return banned;
   }

   public void setBanned(Boolean banned) {
      this.banned = banned;
   }

   public String getSerial() {
      return serial;
   }

   public void setSerial(String serial) {
      this.serial = serial;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public List<CardEntity> getCards() {
      return cards;
   }

   public void setCards(List<CardEntity> cards) {
      this.cards = cards;
   }

   public UserPrivacyEntity getPrivacy() {
      return privacy;
   }

   public void setPrivacy(UserPrivacyEntity privacy) {
      this.privacy = privacy;
   }

   public ClientDto clientDto() {
      ClientDto clientDto = new ClientDto();

      clientDto.setId(id);
      clientDto.setAddress(address);
      clientDto.setBanned(banned);
      clientDto.setName(name);
      clientDto.setCode(code);
      clientDto.setCreatedAt(createdAt);
      clientDto.setDescription(description);
      clientDto.setEmail(privacy.getEmail());
      clientDto.setPhone(privacy.getPhone());
      clientDto.setSerial(serial);

      return clientDto;
   }
}
