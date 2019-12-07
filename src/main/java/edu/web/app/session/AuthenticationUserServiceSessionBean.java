package edu.web.app.session;

import edu.web.app.dto.Credentials;
import edu.web.app.dto.SignupRequest;
import edu.web.app.entity.ClientIdentityEntity;

import javax.ejb.Local;

@Local
public interface AuthenticationUserServiceSessionBean {

   ClientIdentityEntity login(Credentials credentials);

   void signup(SignupRequest signupRequest);

}
