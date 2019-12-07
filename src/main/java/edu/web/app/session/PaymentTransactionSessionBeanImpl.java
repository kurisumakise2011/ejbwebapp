package edu.web.app.session;

import edu.web.app.dto.TransactionQuery;
import edu.web.app.entity.PaymentTransactionEntity;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PaymentTransactionSessionBeanImpl implements PaymentTransactionSessionBean {

   @PersistenceContext(unitName = "persistence")
   private EntityManager entityManager;

   @Override
   public List<PaymentTransactionEntity> fetchAllTransactionsByQuery(Long id, TransactionQuery transactionQuery) {
      String transactionStatus = transactionQuery.getTransactionStatus() == null ? "null" : "transaction.status";
      String cardTo = StringUtils.isBlank(transactionQuery.getCardTo()) ? "null" : "receiver.cardNumber";
      String cardFrom = StringUtils.isBlank(transactionQuery.getCardFrom()) ? "null" : "sender.cardNumber";

      String hql = "select transaction from PaymentTransactionEntity transaction " +
            "inner join transaction.receiver as receiver " +
            "inner join transaction.sender as sender " +
            "where card.owner.id = ?1 and transaction.createdAt >= ?2 and transaction.completeAt <= ?3 " +
            "and " + transactionStatus + " = ?4 " +
            "and " + cardTo + " = ?5 " +
            "and " + cardFrom + " = ?6 " +
            "limit ?7";

      TypedQuery<PaymentTransactionEntity> query = entityManager.createQuery(hql, PaymentTransactionEntity.class)
            .setParameter(1, id)
            .setParameter(2, transactionQuery.getCreatedAt())
            .setParameter(3, transactionQuery.getCompleteAt())
            .setParameter(4, transactionQuery.getTransactionStatus())
            .setParameter(5, transactionQuery.getCardTo())
            .setParameter(6, transactionQuery.getCardFrom())
            .setParameter(7, transactionQuery.getLimit());

      return query.getResultList();
   }
}
