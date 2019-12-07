package edu.web.app.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public abstract class ParameterCommand<T> implements Command {
   @Override
   public ResponseStrategy performRequest(HttpServletRequest request, HttpServletResponse response) {
      return null;
   }

   protected abstract T exec(Map<String, String[]> parameters);

   protected abstract ResponseStrategy doResponse(T returnedValue);

}
