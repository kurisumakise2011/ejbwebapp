package edu.web.app.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {
   private static final Logger LOGGER = LogManager.getLogger(CorsFilter.class);

   /**
    * Default constructor.
    */
   public CorsFilter() {
   }

   /**
    * @see Filter#destroy()
    */
   public void destroy() {
   }

   /**
    * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
    */
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
         throws IOException, ServletException {

      HttpServletRequest request = (HttpServletRequest) servletRequest;
      LOGGER.info("CORSFilter HTTP Request: " + request.getMethod());

      // Authorize (allow) all domains to consume the content
      ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
      ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST, DELETE");

      HttpServletResponse resp = (HttpServletResponse) servletResponse;

      // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
      if (request.getMethod().equals("OPTIONS")) {
         resp.setStatus(HttpServletResponse.SC_ACCEPTED);
         return;
      }

      // pass the request along the filter chain
      chain.doFilter(request, servletResponse);
   }

   /**
    * @see Filter#init(FilterConfig)
    */
   public void init(FilterConfig fConfig) throws ServletException {
   }
}
