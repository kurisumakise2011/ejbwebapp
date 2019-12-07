package edu.web.app.exception;

public class UserBannedException extends RuntimeException {
   public UserBannedException() {
      super();
   }

   public UserBannedException(String message) {
      super(message);
   }

   public UserBannedException(String message, Throwable cause) {
      super(message, cause);
   }

   public UserBannedException(Throwable cause) {
      super(cause);
   }
}
