package edu.web.app.servlet;

import edu.web.app.exception.AuthenticationException;
import edu.web.app.exception.JsonParseException;
import edu.web.app.exception.RegistrationCollectedException;
import edu.web.app.exception.RegistrationException;
import edu.web.app.exception.UnknownCommandException;
import edu.web.app.exception.UserBannedException;
import edu.web.app.exception.WebAppRuntimeException;
import edu.web.app.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorHandler {
   private static final Logger LOGGER = LogManager.getLogger(FrontController.class);
   static final ErrorHandler INSTANCE = new ErrorHandler();

   private ErrorHandler() {

   }

   public void handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
      try {
         if (exception instanceof EJBException) {
            if (exception.getCause() == null) {
               LOGGER.error("IO exception occurred while handling error", exception);
               throw new WebAppRuntimeException(exception);
            }
            exception = (Exception) exception.getCause();
         }

         LOGGER.error("Handling exception with message " + exception.getMessage());
         if (exception instanceof JsonParseException) {
            proceedWithErrorJson(400, exception.getMessage(), response);
         } else if (exception instanceof AuthenticationException) {
            proceedWithErrorJson(401, exception.getMessage(), response);
         } else if (exception instanceof RegistrationException) {
            proceedWithErrorJson(422, exception.getMessage(), response);
         } else if (exception instanceof RegistrationCollectedException) {
            RegistrationCollectedException collectedException = (RegistrationCollectedException) exception;
            proceedWithErrorJson(422, collectedException.getMessage(),
                  collectedException.getViolations()
                        .stream()
                        .map(Exception::getMessage)
                        .collect(Collectors.toList()),
                  response);
         } else if (exception instanceof UserBannedException) {
            proceedWithErrorJson(403, exception.getMessage(), response);
         } else if (exception instanceof UnknownCommandException) {
            proceedWithErrorJson(404, exception.getMessage(), response);
         } else {
            LOGGER.error("Internal error occurred ", exception);
            proceedWithErrorJson(500, exception.getMessage(), response);
         }
      } catch (Exception e) {
         LOGGER.error("IO exception occurred while handling error", e);
         throw new WebAppRuntimeException(e);
      }
   }

   private void proceedWithErrorJson(int status, String message, HttpServletResponse response) {
      response.setStatus(status);
      JsonUtils.toJson(response, new ExceptionBody(message, status));
   }

   private void proceedWithErrorJson(int status, String message,
                                     List<String> messages, HttpServletResponse response) {
      response.setStatus(status);
      ExceptionBody exceptionBody = new ExceptionBody(message, status);
      exceptionBody.setMessages(messages);
      JsonUtils.toJson(response, exceptionBody);
   }

   private class ExceptionBody {
      private List<String> messages = new ArrayList<>();
      private String message;
      private Integer status;

      public ExceptionBody(String message, Integer status) {
         this.message = message;
         this.status = status;
      }

      public String getMessage() {
         return message;
      }

      public Integer getStatus() {
         return status;
      }

      public List<String> getMessages() {
         return messages;
      }

      public void setMessages(List<String> messages) {
         this.messages = messages;
      }
   }

}
