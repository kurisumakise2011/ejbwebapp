package edu.web.app.servlet;

import edu.web.app.dto.ClientDto;
import edu.web.app.dto.Credentials;
import edu.web.app.dto.SignupRequest;
import edu.web.app.entity.ClientIdentityEntity;
import edu.web.app.session.AuthenticationUserServiceSessionBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@Stateless
public class AuthCommandHolderImpl implements AuthCommandHolder {

   @EJB
   public AuthenticationUserServiceSessionBean authService;

   @Override
   public SignIn signIn() {
      return new SignIn();
   }

   @Override
   public SignUp signUp() {
      return new SignUp();
   }

   public class SignIn extends JsonCommand<Credentials, ClientDto> {
      @Override
      public ClientDto exec(Credentials credentials) {
         ClientIdentityEntity client = authService.login(credentials);
         return client.clientDto();
      }

      @Override
      protected ResponseStrategy doResponse(ClientDto returnedValue) {
         return ((response, request) -> {
            HttpSession session = request.getSession();
            session.setAttribute("id", returnedValue.getId());
         });
      }

      @Override
      public Class<Credentials> type() {
         return Credentials.class;
      }
   }

   public class SignUp extends JsonCommand<SignupRequest, Void> {

      @Override
      public Void exec(SignupRequest signupRequest) {
         authService.signup(signupRequest);
         return null;
      }

      @Override
      protected ResponseStrategy doResponse(Void returnedValue) {
         return (response, request) -> {
            response.setStatus(201);
         };
      }

      @Override
      public Class<SignupRequest> type() {
         return SignupRequest.class;
      }
   }

}
