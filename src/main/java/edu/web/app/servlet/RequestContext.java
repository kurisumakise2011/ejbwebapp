package edu.web.app.servlet;

import edu.web.app.exception.AuthenticationException;

public class RequestContext {
   private static ThreadLocal<Long> key = new ThreadLocal<>();
   private static ThreadLocal<String> atmKey = new ThreadLocal<>();

   public static Long get() {
      Long id = key.get();
      if (id == null) {
         throw new AuthenticationException("Not authorized");
      }
      return id;
   }

   public static void set(Long value) {
      key.set(value);
   }

   public static void remove() {
      key.remove();
   }

   public static String getAtmKey() {
      return atmKey.get();
   }

   public static void set(String atmId) {
      atmKey.set(atmId);
   }

   public static void removeAtm() {
      atmKey.remove();
   }
}
