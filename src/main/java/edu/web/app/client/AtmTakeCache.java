package edu.web.app.client;

import edu.web.app.entity.CardEntity;
import edu.web.app.exception.AtmProcessException;

import java.math.BigDecimal;

public class AtmTakeCache implements AtmState {
   private static final BigDecimal HUNDRED = new BigDecimal(100);

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
      BigDecimal withdrawal = atm.getWithdrawal();
      if (withdrawal != null && withdrawal.remainder(HUNDRED).compareTo(BigDecimal.ZERO) == 0) {
         CardEntity card = atm.getCard();
         BigDecimal amountOnCard = card.getCurrentAmount();
         if (amountOnCard.compareTo(withdrawal) < 0) {
            throw new AtmProcessException("You don't have enough many to withdrawal");
         } else {
            BigDecimal current = atm.getCurrent();
            atm.setCurrent(current.add(withdrawal.negate()));
            card.setCurrentAmount(current.add(withdrawal.negate()));
            atm.setMessage("Please take your money in amount " + withdrawal);
         }
      } else {
         throw new AtmProcessException("Incorrect amount");
      }
   }
}
