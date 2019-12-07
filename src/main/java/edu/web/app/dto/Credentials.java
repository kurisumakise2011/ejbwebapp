package edu.web.app.dto;

import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

public class Credentials {
   private static final Pattern PHONE = Pattern.compile("^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$");

   @NotNull
   private String login;

   @NotNull
   private String password;

   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isEmail() {
      return login.contains("@");
   }

   public boolean isPhone() {
      return PHONE.matcher(login).matches();
   }
}
