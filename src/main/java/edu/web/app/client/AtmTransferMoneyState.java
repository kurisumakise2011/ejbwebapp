package edu.web.app.client;

import edu.web.app.entity.CardEntity;
import edu.web.app.exception.AtmProcessException;

import java.math.BigDecimal;
import java.util.List;

public class AtmTransferMoneyState implements AtmState {

   @Override
   public void next(Atm atm) {
      atm.setState(new AtmStateStarted());
   }

   @Override
   public void prev(Atm atm) {
      atm.setState(new AtmTransferState());
   }

   @Override
   public void exec(Atm atm) {
      BigDecimal withdrawal = atm.getWithdrawal();
      String toCard = atm.getToCard();
      if (withdrawal != null && toCard != null) {
         CardEntity card = atm.getCard();

         if (withdrawal.compareTo(card.getCurrentAmount()) > 0) {
            throw new AtmProcessException("You don't have enough money for transferring");
         }

         if (!card.getCardNumber().equals(toCard)) {
            List<CardEntity> cards = card.getOwner().getCards();
            CardEntity toCardEntity = cards.stream()
                  .filter(anotherCard -> anotherCard.getCardNumber().equals(toCard))
                  .findAny()
                  .orElseThrow(() -> new AtmProcessException("Invalid to card number, you don't posses it"));

            if (card.getCurrency().equals(toCardEntity.getCurrency())) {
               atm.getCardSessionBean().transferMoney(card, toCardEntity, withdrawal);
               atm.setMessage("Transaction completed");
            } else {
               throw new AtmProcessException("You cannot transfer money to card with different currency via ATM");
            }
         } else {
            throw new AtmProcessException("You cannot transfer money to the same card");
         }
      } else {
         throw new AtmProcessException("You cannot proceed operation");
      }
   }
}
