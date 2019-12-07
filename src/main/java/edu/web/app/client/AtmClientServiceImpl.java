package edu.web.app.client;

import edu.web.app.dto.AtmReq;
import edu.web.app.dto.AtmResp;
import edu.web.app.entity.CardEntity;
import edu.web.app.exception.AtmProcessException;
import edu.web.app.servlet.RequestContext;
import edu.web.app.session.CardSessionBean;
import edu.web.app.session.ClientIdentitySessionBean;
import edu.web.app.session.PaymentTransactionSessionBean;
import org.apache.commons.lang3.BooleanUtils;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Stateful
public class AtmClientServiceImpl implements AtmClientService {
   @Inject
   public CardSessionBean cardSessionBean;

   @Inject
   public ClientIdentitySessionBean clientIdentitySessionBean;

   @Inject
   public PaymentTransactionSessionBean paymentTransactionSessionBean;

   private Map<String, Atm> atms = new ConcurrentHashMap<>();


   @Override
   public AtmResp performAction(AtmReq req) {
      if (RequestContext.getAtmKey() == null) {
         CardEntity card = cardSessionBean.clientCard(RequestContext.get(), req.getCard());
         String uuid = insertCard(card);
         RequestContext.set(uuid);
      }
      Atm atm = enhanceAtm(req);
      AtmResp response;
      try {
         atm.exec();
         response = toAtmResp(atm);
         if (BooleanUtils.isTrue(req.getPrev())) {
            atm.previousState();
         } else {
            atm.nextState();
         }

      } catch (AtmProcessException e) {
         response = toAtmRespException(e);
         atm.clear();
      }
      persistCard(atm);

      return response;
   }

   private Atm enhanceAtm(AtmReq req) {
      Atm atm = atms.get(RequestContext.getAtmKey());

      atm.setWithdrawal(req.getAmount());
      atm.setPincode(req.getPincode());
      atm.setOption(req.getOption());

      return atm;
   }

   private String insertCard(CardEntity card) {
      String uuid = UUID.randomUUID().toString();
      if (atms.put(uuid, new Atm(card, cardSessionBean)) != null) {
         throw new AtmProcessException("Critical error");
      }
      return uuid;
   }

   private AtmResp toAtmResp(Atm atm) {
      AtmResp response = new AtmResp();

      response.setCode(atm.getState().getClass().getSimpleName());
      response.setMessage(atm.getMessage());
      response.setLinks(atm.getLinks());

      return response;
   }

   private AtmResp toAtmRespException(AtmProcessException e) {
      AtmResp response = new AtmResp();

      response.setCode(AtmStateStarted.class.getSimpleName());
      response.setMessage(e.getMessage());

      return response;
   }

   private void persistCard(Atm atm) {
      CardEntity card = atm.getCard();
      cardSessionBean.updateCard(card);
   }
}
