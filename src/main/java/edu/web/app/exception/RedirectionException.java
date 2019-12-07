package edu.web.app.exception;

public class RedirectionException extends RuntimeException {
   public RedirectionException() {
      super();
   }

   public RedirectionException(String message) {
      super(message);
   }

   public RedirectionException(String message, Throwable cause) {
      super(message, cause);
   }

   public RedirectionException(Throwable cause) {
      super(cause);
   }
}
