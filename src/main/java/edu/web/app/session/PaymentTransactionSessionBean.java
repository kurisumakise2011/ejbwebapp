package edu.web.app.session;

import edu.web.app.dto.TransactionQuery;
import edu.web.app.entity.PaymentTransactionEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PaymentTransactionSessionBean {

   List<PaymentTransactionEntity> fetchAllTransactionsByQuery(Long id, TransactionQuery transactionQuery);

}
