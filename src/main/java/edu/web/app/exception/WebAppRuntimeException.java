package edu.web.app.exception;

public class WebAppRuntimeException extends RuntimeException {
   public WebAppRuntimeException() {
      super();
   }

   public WebAppRuntimeException(String message) {
      super(message);
   }

   public WebAppRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }

   public WebAppRuntimeException(Throwable cause) {
      super(cause);
   }
}
