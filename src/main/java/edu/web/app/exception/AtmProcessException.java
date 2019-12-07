package edu.web.app.exception;

public class AtmProcessException extends RuntimeException {
   public AtmProcessException() {
      super();
   }

   public AtmProcessException(String message) {
      super(message);
   }

   public AtmProcessException(String message, Throwable cause) {
      super(message, cause);
   }

   public AtmProcessException(Throwable cause) {
      super(cause);
   }
}
