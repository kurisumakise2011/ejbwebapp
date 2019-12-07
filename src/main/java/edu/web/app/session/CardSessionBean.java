package edu.web.app.session;

import edu.web.app.entity.CardEntity;
import edu.web.app.entity.CardType;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

@Local
public interface CardSessionBean {

   CardEntity createCard(Long id, String currency, CardType type);

   List<CardEntity> clientCards(Long id);

   void deleteCreditCard(Long id, Long cardId);

   CardEntity clientCard(Long id, String cardName);

   void updateCard(CardEntity entity);

   void transferMoney(CardEntity from, CardEntity to, BigDecimal amount);
}
