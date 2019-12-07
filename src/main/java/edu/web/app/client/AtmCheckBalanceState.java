package edu.web.app.client;

import edu.web.app.entity.CardEntity;

public class AtmCheckBalanceState implements AtmState {
   @Override
   public void next(Atm atm) {
      atm.setState(new AtmChooseState());
   }

   @Override
   public void prev(Atm atm) {
      atm.setState(new AtmChooseState());
   }

   @Override
   public void exec(Atm atm) {
      CardEntity card = atm.getCard();
      atm.setMessage("Balance on card "
            + card.getCardNumber()
            + "/"
            + card.getCurrency()
            + " is "
            + card.getCurrentAmount());
   }
}
