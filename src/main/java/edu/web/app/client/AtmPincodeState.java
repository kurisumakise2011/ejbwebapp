package edu.web.app.client;

import edu.web.app.entity.CardEntity;

public class AtmPincodeState implements AtmState {
   @Override
   public void next(Atm atm) {
      atm.setState(new AtmChooseState());
   }

   @Override
   public void prev(Atm atm) {
      atm.setState(new AtmStateStarted());
   }

   @Override
   public void exec(Atm atm) {
      CardEntity card = atm.getCard();
      String enteredPincode = atm.getPincode();

      if (!card.getPinCode().equals(enteredPincode)) {
         int attempts = atm.getAttempts();
         if (attempts == 0) {
            card.setActive(false);
            atm.setMessage("Your card has been blocked, please contact a bank department to restore card");
         } else {
            atm.setMessage("Invalid pincode");
            atm.setAttempts(attempts - 1);
         }
      } else {
         atm.setMessage("Correct pincode.");
         atm.setAttempts(3);
      }
   }
}
