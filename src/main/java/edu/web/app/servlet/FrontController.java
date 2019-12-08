package edu.web.app.servlet;

import edu.web.app.exception.UnknownCommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FrontController extends HttpServlet {
   private static final Logger LOGGER = LogManager.getLogger(FrontController.class);


   private static class Key {
      String url;
      HttpMethod method;

      Key(String url, HttpMethod method) {
         this.url = url;
         this.method = method;
      }
   }

   private enum HttpMethod {
      POST, GET, DELETE, PUT
   }

   @Inject
   private CardCommandHolder cardCommandHolder;

   @Inject
   private AuthCommandHolder authCommandHolder;

   @Inject
   private TransactionCommandHolder transactionCommandHolder;
   
   @Inject
   private ClientCommandHolder clientCommandHolder;

   private final Map<Key, Command> commands = new HashMap<>();

   private Key lookup(String path, HttpMethod method) {
      Set<Key> keys = commands.keySet();
      for (Key key : keys) {
         if (key.method == method && (path.matches(key.url) || key.url.contains(path))) {
            return key;
         }
      }
      throw new UnknownCommandException(path);
   }

   private static final Command DEFAULT = new UnknownCommand();
   private static class UnknownCommand implements Command {

      @Override
      public ResponseStrategy performRequest(HttpServletRequest request, HttpServletResponse response) {
         throw new UnknownCommandException(request.getContextPath());
      }
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
      serviceRequest(req, resp);
   }

   @Override
   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      super.doHead(req, resp);
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
      serviceRequest(req, resp);
   }

   @Override
   protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
      serviceRequest(req, resp);
   }

   @Override
   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
      serviceRequest(req, resp);
   }

   @Override
   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      super.doOptions(req, resp);
   }

   @Override
   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      super.doTrace(req, resp);
   }

   private void serviceRequest(HttpServletRequest request, HttpServletResponse response) {
      applySession(request.getSession());
      try {
         commands.getOrDefault(lookup(request.getRequestURI(),
               HttpMethod.valueOf(request.getMethod())), DEFAULT)
               .performRequest(request, response)
               .doResponse(response, request);
      } catch (Exception e) {
         ErrorHandler.INSTANCE.handleException(request, response, e);
      }
   }

   private void applySession(HttpSession session) {
      if (session == null) {
         return;
      }
      Long id = (Long) session.getAttribute("id");
      RequestContext.set(id);

      String atm = (String) session.getAttribute("atm");
      RequestContext.set(atm);
   }

   @Override
   public void log(String msg) {
      LOGGER.info(msg);
   }

   @Override
   public void log(String message, Throwable t) {
      LOGGER.error(message, t);
   }

   @Override
   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      commands.put(new Key("/atm/withdrawal/.*", HttpMethod.POST), cardCommandHolder.atmOperation());
      commands.put(new Key("/atm/transfer/.*", HttpMethod.POST), cardCommandHolder.atmOperation());
      commands.put(new Key("/atm/balance", HttpMethod.POST), cardCommandHolder.atmOperation());
      commands.put(new Key("/atm", HttpMethod.POST), cardCommandHolder.atmOperation());
      commands.put(new Key("/atm/replenish/.*", HttpMethod.POST), cardCommandHolder.atmOperation());
      commands.put(new Key("/client", HttpMethod.GET), clientCommandHolder.getClientByIdentifier());
      commands.put(new Key("/cards", HttpMethod.GET), cardCommandHolder.getCards());
      commands.put(new Key("/card/.*", HttpMethod.GET), cardCommandHolder.fetchCardByNumber());
      commands.put(new Key("/card", HttpMethod.POST), cardCommandHolder.createCard());
      commands.put(new Key("/card/.*", HttpMethod.DELETE), cardCommandHolder.removeCard());
      commands.put(new Key("/transaction", HttpMethod.POST), transactionCommandHolder.getTransactionsByQuery());
      commands.put(new Key("/signin", HttpMethod.POST), authCommandHolder.signIn());
      commands.put(new Key("/signup", HttpMethod.POST), authCommandHolder.signUp());
      commands.put(new Key("/atm", HttpMethod.GET), new RenderCommand("atm.jsp"));
      commands.put(new Key("/signin", HttpMethod.GET), new RenderCommand("signin.jsp"));
      commands.put(new Key("/signup", HttpMethod.GET), new RenderCommand("signup.jsp"));
   }

   @Override
   public void init() throws ServletException {
      super.init();
   }

   @Override
   public String getServletName() {
      return FrontController.class.getSimpleName();
   }
}
