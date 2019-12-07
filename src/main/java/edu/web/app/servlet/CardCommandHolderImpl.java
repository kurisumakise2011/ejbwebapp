package edu.web.app.servlet;

import edu.web.app.client.AtmClientService;
import edu.web.app.dto.AtmReq;
import edu.web.app.dto.AtmResp;
import edu.web.app.dto.CardDto;
import edu.web.app.entity.CardEntity;
import edu.web.app.entity.CardType;
import edu.web.app.session.CardSessionBean;
import edu.web.app.util.JsonUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class CardCommandHolderImpl implements CardCommandHolder {
   @Inject
   public AtmClientService atmClientService;

   @Inject
   public CardSessionBean cardSessionBean;

   @Override
   public AtmOperation atmOperation() {
      return new AtmOperation();
   }

   @Override
   public GetCards getCards() {
      return new GetCards();
   }

   @Override
   public FetchCardByNumber fetchCardByNumber() {
      return new FetchCardByNumber();
   }

   @Override
   public CreateCard createCard() {
      return new CreateCard();
   }

   @Override
   public RemoveCard removeCard() {
      return new RemoveCard();
   }

   public class GetCards extends JsonCommand<Void, List<CardDto>> {

      @Override
      public List<CardDto> exec(Void aVoid) {
         Long id = RequestContext.get();
         List<CardEntity> cardEntities = cardSessionBean.clientCards(id);
         return cardEntities
               .stream()
               .map(CardEntity::cardDto)
               .collect(Collectors.toList());
      }

      @Override
      public Class<Void> type() {
         return Void.class;
      }

      @Override
      protected ResponseStrategy doResponse(List<CardDto> returnedValue) {
         return (response, request) -> {};
      }
   }

   public class CreateCard extends ParameterCommand<CardDto> {

      @Override
      protected CardDto exec(Map<String, String[]> parameters) {
         String currency = parameters.get("currency")[0];
         CardType type = CardType.valueOf(parameters.get("type")[0]);
         Long id = RequestContext.get();
         CardEntity card = cardSessionBean.createCard(id, currency, type);
         return card.cardDto();
      }

      @Override
      protected ResponseStrategy doResponse(CardDto returnedValue) {
         return (response, request) -> {

         };
      }
   }

   public class RemoveCard extends ParameterCommand<Void> {

      @Override
      protected Void exec(Map<String, String[]> parameters) {
         Long cardId = Long.valueOf(parameters.get("cardId")[0]);
         Long id = RequestContext.get();
         cardSessionBean.deleteCreditCard(id, cardId);
         return null;
      }

      @Override
      protected ResponseStrategy doResponse(Void returnedValue) {
         return (response, request) -> {};
      }
   }

   public class FetchCardByNumber extends ParameterCommand<CardDto> {

      @Override
      protected CardDto exec(Map<String, String[]> parameters) {
         String cardNumber = parameters.get("cardNumber")[0];
         Long id = RequestContext.get();
         CardEntity cardEntity = cardSessionBean.clientCard(id, cardNumber);
         return cardEntity.cardDto();
      }

      @Override
      protected ResponseStrategy doResponse(CardDto returnedValue) {
         return (response, request) -> JsonUtils.toJson(response, returnedValue);
      }
   }

   public class AtmOperation extends JsonCommand<AtmReq, AtmResp> {

      @Override
      public AtmResp exec(AtmReq atmReq) {
         return atmClientService.performAction(atmReq);
      }

      @Override
      public Class<AtmReq> type() {
         return AtmReq.class;
      }

      @Override
      protected ResponseStrategy doResponse(AtmResp returnedValue) {
         return (response, request) -> {
            HttpSession session = request.getSession();
            session.setAttribute("atm", RequestContext.getAtmKey());
         };
      }
   }
}
