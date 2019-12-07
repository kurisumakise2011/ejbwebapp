package edu.web.app.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface ResponseStrategy {

   void doResponse(HttpServletResponse response, HttpServletRequest request);

}
