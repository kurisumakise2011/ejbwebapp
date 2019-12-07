package edu.web.app.dto;

import java.util.List;

public class AtmResp {
   private String message;
   private List<String> links;
   private String code;

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public List<String> getLinks() {
      return links;
   }

   public void setLinks(List<String> links) {
      this.links = links;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }
}
