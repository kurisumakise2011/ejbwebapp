package edu.web.app.servlet;

import javax.ejb.Local;

@Local
public interface ClientCommandHolder {

   ClientCommandHolderImpl.GetClientByIdentifier getClientByIdentifier();

}
