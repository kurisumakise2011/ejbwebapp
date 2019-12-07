package edu.web.app.servlet;

import javax.ejb.Local;

@Local
public interface TransactionCommandHolder {

   TransactionCommandHolderImpl.GetTransactionsByQuery getTransactionsByQuery();

}
