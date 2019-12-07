package edu.web.app.servlet;

import edu.web.app.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class JsonCommand<Request, Response> implements Command {
   public abstract Response exec(Request request);

   public ResponseStrategy performRequest(HttpServletRequest request, HttpServletResponse response) {
      Request dto = getJson(request, type());
      Response resp = exec(dto);
      toJson(response, resp);
      return doResponse(resp);
   }

   public abstract Class<Request> type();

   protected abstract ResponseStrategy doResponse(Response returnedValue);

   protected Request getJson(HttpServletRequest request, Class<Request> clazz) {
      return JsonUtils.getJson(request, clazz);
   }

   protected void toJson(HttpServletResponse response, Response data) {
      JsonUtils.toJson(response, data);
   }
}
