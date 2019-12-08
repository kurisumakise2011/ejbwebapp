package edu.web.app.session;

import edu.web.app.entity.CardEntity;
import edu.web.app.entity.CardType;
import edu.web.app.entity.ClientIdentityEntity;
import edu.web.app.entity.PaymentTransactionEntity;
import edu.web.app.entity.TransactionStatus;
import edu.web.app.exception.UserBannedException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Stateless
public class CardSessionBeanImpl implements CardSessionBean {

   @PersistenceContext(unitName = "persistence")
   private EntityManager entityManager;

   @Override
   public CardEntity createCard(Long id, String currency, CardType type) {
      ClientIdentityEntity client = entityManager.find(ClientIdentityEntity.class, id);

      if (BooleanUtils.isFalse(client.getBanned())) {
         CardEntity entity = new CardEntity();
         entity.setActive(true);
         entity.setExpireDate(Timestamp.from(Instant.now().plus(2, ChronoUnit.YEARS)));
         entity.setCurrency(currency);
         entity.setCvv(ThreadLocalRandom.current().nextInt(100, 1000));
         entity.setCurrentAmount(BigDecimal.ZERO);
         entity.setPinCode("0000");
         entity.setMeta("Credit card for user with id " + id);
         entity.setType(type);
         entity.setCardNumber(RandomStringUtils.randomNumeric(16));

         CollectionUtils.emptyIfNull(client.getCards()).add(entity);

         entityManager.persist(client);

         return entity;
      }
      throw new UserBannedException("Cannot create card for user. User is banned");
   }

   @Override
   public List<CardEntity> clientCards(Long id) {
      TypedQuery<CardEntity> query = entityManager.createQuery(
            "select cards from CardEntity cards where cards.owner.id = ?1 and cards.active = true ",
            CardEntity.class);

      return new ArrayList<>(query
            .setParameter(1, id)
            .getResultList());
   }

   @Override
   public void deleteCreditCard(Long id, Long cardId) {
      Query query = entityManager.createQuery(
            "delete from CardEntity card where card.id = ?1 and card.owner.id = ?2");
      query.setParameter(1, cardId);
      query.setParameter(2, id);
      query.executeUpdate();
   }

   @Override
   public CardEntity clientCard(Long id, String cardName) {
      TypedQuery<CardEntity> query = entityManager.createQuery(
            "select card from CardEntity card where card.cardNumber = ?1 and card.owner.id = ?2",
            CardEntity.class);

      query.setParameter(1, cardName);
      query.setParameter(2, id);

      List<CardEntity> resultList = query.getResultList();
      return CollectionUtils.isEmpty(resultList) ? null : resultList.iterator().next();

   }

   @Override
   public void updateCard(CardEntity entity) {
      entityManager.merge(entity);
   }

   @Override
   public void transferMoney(CardEntity from, CardEntity to, BigDecimal amount) {
      BigDecimal currentAmount = from.getCurrentAmount();
      BigDecimal toCurrentAmount = to.getCurrentAmount();

      from.setCurrentAmount(currentAmount.add(amount.negate()));
      to.setCurrentAmount(toCurrentAmount.add(amount.plus()));

      PaymentTransactionEntity tr = new PaymentTransactionEntity();
      tr.setAmount(amount);
      tr.setCompleteAt(Timestamp.from(Instant.now().plus(5, ChronoUnit.SECONDS)));
      tr.setCurrency(from.getCurrency());
      tr.setLocation(Locale.getDefault().getCountry());
      tr.setMeta("Transaction from " + from.getCardNumber() + " to " + to.getCardNumber() + " with amount " + amount);
      tr.setReceiver(to);
      tr.setSender(from);
      tr.setStatus(TransactionStatus.COMPLETE);

      entityManager.persist(tr);
   }
}
