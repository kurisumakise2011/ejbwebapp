package edu.web.app.servlet;

import edu.web.app.dto.TransactionDto;
import edu.web.app.dto.TransactionQuery;
import edu.web.app.entity.PaymentTransactionEntity;
import edu.web.app.session.PaymentTransactionSessionBean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@LocalBean
@Stateless
public class TransactionCommandHolderImpl implements TransactionCommandHolder {

   @Inject
   public PaymentTransactionSessionBean paymentTransactionSessionBean;

   @Override
   public GetTransactionsByQuery getTransactionsByQuery() {
      return new GetTransactionsByQuery();
   }

   public class GetTransactionsByQuery extends JsonCommand<TransactionQuery, List<TransactionDto>> {

      @Override
      public List<TransactionDto> exec(TransactionQuery query) {
         List<PaymentTransactionEntity> transactions = paymentTransactionSessionBean
               .fetchAllTransactionsByQuery(RequestContext.get(), query);

         return transactions.stream()
               .map(PaymentTransactionEntity::transactionDto)
               .collect(Collectors.toList());
      }

      @Override
      protected ResponseStrategy doResponse(List<TransactionDto> returnedValue) {
         return (response, request) -> {};
      }

      @Override
      public Class<TransactionQuery> type() {
         return TransactionQuery.class;
      }
   }
}
