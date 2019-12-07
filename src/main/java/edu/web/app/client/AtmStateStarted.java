package edu.web.app.client;

import edu.web.app.entity.CardEntity;
import edu.web.app.entity.CardType;
import edu.web.app.exception.AtmProcessException;

import java.sql.Timestamp;
import java.time.Instant;

public class AtmStateStarted implements AtmState {
   @Override
   public void next(Atm atm) {
      atm.setState(new AtmPincodeState());
   }

   @Override
   public void prev(Atm atm) {
      atm.setState(new AtmPincodeState());
   }

   @Override
   public void exec(Atm atm) {
      CardEntity card = atm.getCard();
      if (!card.getActive()) {
         throw new AtmProcessException("Could not proceed operation your card not active");
      }

      if (card.getExpireDate().before(Timestamp.from(Instant.now()))) {
         throw new AtmProcessException("Your card has been expired. You cannot do operation with it");
      }

      if (card.getType() == CardType.CREDIT) {
         throw new AtmProcessException("The atm doesn't server credit card types, please try another one");
      }

      atm.setMessage("Hello if you are agree with bank policy please " +
            "enter pincode to your card to proceed operation");
   }
}
