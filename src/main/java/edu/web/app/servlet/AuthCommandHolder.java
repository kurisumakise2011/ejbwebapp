package edu.web.app.servlet;

import javax.ejb.Local;

@Local
public interface AuthCommandHolder {

   AuthCommandHolderImpl.SignIn signIn();

   AuthCommandHolderImpl.SignUp signUp();

}
