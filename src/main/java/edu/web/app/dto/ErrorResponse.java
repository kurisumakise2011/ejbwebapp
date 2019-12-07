package edu.web.app.dto;

public class ErrorResponse {
   private Integer status;
   private String message;
   private String code;

   public ErrorResponse() {
   }

   public ErrorResponse(Integer status, String message, String code) {
      this.status = status;
      this.message = message;
      this.code = code;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }
}
