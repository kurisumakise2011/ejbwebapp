package edu.web.app.client;

import edu.web.app.entity.CardEntity;
import edu.web.app.exception.AtmProcessException;

import java.math.BigDecimal;

public class AtmReplenishState implements AtmState {
   private static final BigDecimal LIMIT = BigDecimal.valueOf(10_000);

   @Override
   public void next(Atm atm) {
      atm.setState(new AtmStateStarted());
   }

   @Override
   public void prev(Atm atm) {
      atm.setState(new AtmStateStarted());
   }

   @Override
   public void exec(Atm atm) {
      if (atm.getWithdrawal() == null || atm.getWithdrawal().compareTo(LIMIT) > 0) {
         throw new AtmProcessException("Cannot replenish to such sum limit is " + LIMIT);
      }
      CardEntity card = atm.getCard();
      BigDecimal currentAmount = card.getCurrentAmount();
      card.setCurrentAmount(currentAmount.add(atm.getWithdrawal().plus()));
      atm.setMessage("You replenished your account number");
   }
}
