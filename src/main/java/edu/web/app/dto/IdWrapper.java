package edu.web.app.dto;

public class IdWrapper {
   private Object value;
   private Long id;

   public IdWrapper(Object value, Long status) {
      this.value = value;
      this.id = status;
   }

   public Object getValue() {
      return value;
   }

   public Long getId() {
      return id;
   }
}
