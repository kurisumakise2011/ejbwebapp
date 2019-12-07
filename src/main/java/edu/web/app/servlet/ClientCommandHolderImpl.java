package edu.web.app.servlet;

import edu.web.app.entity.ClientIdentityEntity;
import edu.web.app.session.ClientIdentitySessionBean;
import edu.web.app.util.JsonUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Stateless
public class ClientCommandHolderImpl implements ClientCommandHolder {

   @Inject
   public ClientIdentitySessionBean clientIdentitySessionBean;

   @Override
   public GetClientByIdentifier getClientByIdentifier() {
      return new GetClientByIdentifier();
   }

   public class GetClientByIdentifier implements Command {

      @Override
      public ResponseStrategy performRequest(HttpServletRequest request, HttpServletResponse response) {
         Long id = RequestContext.get();
         ClientIdentityEntity client = clientIdentitySessionBean.findClient(id);
         return (resp, req) -> JsonUtils.toJson(response, client.clientDto());
      }
   }

}
