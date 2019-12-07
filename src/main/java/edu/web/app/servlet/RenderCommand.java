package edu.web.app.servlet;

import edu.web.app.exception.WebAppRuntimeException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RenderCommand implements Command {
   private String jspPath;

   public RenderCommand(String jspPath) {
      this.jspPath = jspPath;
   }

   @Override
   public ResponseStrategy performRequest(HttpServletRequest request, HttpServletResponse response) {
      return new ResponseStrategy() {
         @Override
         public void doResponse(HttpServletResponse response, HttpServletRequest request) {
            try {
               request.getRequestDispatcher(jspPath).forward(request, response);
            } catch (ServletException | IOException e) {
               throw new WebAppRuntimeException(e);
            }
         }
      };
   }
}
