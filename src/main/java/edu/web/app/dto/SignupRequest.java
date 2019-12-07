package edu.web.app.dto;

import edu.web.app.entity.ClientIdentityEntity;
import edu.web.app.entity.UserPrivacyEntity;

import javax.validation.constraints.NotNull;

public class SignupRequest {
   @NotNull
   private String name;
   @NotNull
   private String description;
   @NotNull
   private String serial;
   @NotNull
   private String code;
   @NotNull
   private String address;
   @NotNull
   private String email;
   @NotNull
   private String password;
   @NotNull
   private String phone;

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

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public ClientIdentityEntity toIdentity() {
      ClientIdentityEntity clientIdentityEntity = new ClientIdentityEntity();

      clientIdentityEntity.setName(name);
      clientIdentityEntity.setAddress(address);
      clientIdentityEntity.setBanned(false);
      clientIdentityEntity.setSerial(serial);
      clientIdentityEntity.setCode(code);
      clientIdentityEntity.setDescription(description);

      UserPrivacyEntity entity = new UserPrivacyEntity();

      entity.setEmail(email);
      entity.setPhone(phone);
      entity.setPassword(password);

      clientIdentityEntity.setPrivacy(entity);

      return clientIdentityEntity;
   }

   @Override
   public String toString() {
      return "SignupRequest{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", serial='" + serial + '\'' +
            ", code='" + code + '\'' +
            ", address='" + address + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", phone='" + phone + '\'' +
            '}';
   }
}
