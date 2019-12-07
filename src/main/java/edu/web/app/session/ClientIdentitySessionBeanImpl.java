package edu.web.app.session;

import edu.web.app.entity.ClientIdentityEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClientIdentitySessionBeanImpl implements ClientIdentitySessionBean {

   @PersistenceContext(unitName = "persistence")
   private EntityManager entityManager;

   @Override
   public ClientIdentityEntity findClient(Long id) {
      return entityManager.find(ClientIdentityEntity.class, id);
   }
}
