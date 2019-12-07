package edu.web.app.session;

import edu.web.app.entity.ClientIdentityEntity;

import javax.ejb.Local;

@Local
public interface ClientIdentitySessionBean {

   ClientIdentityEntity findClient(Long id);

}
