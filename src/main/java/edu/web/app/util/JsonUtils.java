package edu.web.app.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public final class JsonUtils {
   private static Gson gson = new GsonBuilder()
         .serializeNulls()
         .setDateFormat("yyyy-MM-dd HH:mm:ss")
         .setPrettyPrinting()
         .create();

   private JsonUtils() {

   }

   public static void toJson(HttpServletResponse response, Object data) {
      try {
         if (data == null) {
            return;
         }
         PrintWriter out = response.getWriter();
         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");
         out.print(gson.toJson(data));
         out.flush();
      } catch (IOException e) {
         throw new JsonParseException("Could not write json to a response", e);
      }
   }

   public static <T> T getJson(HttpServletRequest request, Class<T> clazz) {
      try (BufferedReader reader = request.getReader()) {
         return gson.fromJson(reader, clazz);
      } catch (IOException e) {
         throw new JsonParseException("Could not parse json body from a request", e);
      }
   }
}
