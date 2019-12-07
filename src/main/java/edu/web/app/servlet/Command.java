package edu.web.app.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
   ResponseStrategy performRequest(HttpServletRequest request, HttpServletResponse response);
}
