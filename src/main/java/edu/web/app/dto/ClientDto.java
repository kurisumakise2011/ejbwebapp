package edu.web.app.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class ClientDto {
   private Long id;
   private String name;
   private String description;
   private Timestamp createdAt;
   private Boolean banned;
   private String serial;
   private String code;
   private String address;
   private String email;
   private String phone;

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

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ClientDto clientDto = (ClientDto) o;
      return Objects.equals(id, clientDto.id) &&
            Objects.equals(name, clientDto.name) &&
            Objects.equals(description, clientDto.description) &&
            Objects.equals(createdAt, clientDto.createdAt) &&
            Objects.equals(banned, clientDto.banned) &&
            Objects.equals(serial, clientDto.serial) &&
            Objects.equals(code, clientDto.code) &&
            Objects.equals(address, clientDto.address) &&
            Objects.equals(email, clientDto.email) &&
            Objects.equals(phone, clientDto.phone);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, name, description, createdAt, banned, serial, code, address, email, phone);
   }
}
