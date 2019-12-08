package edu.web.app.client;

import edu.web.app.entity.CardEntity;
import edu.web.app.entity.ClientIdentityEntity;

import java.util.ArrayList;
import java.util.List;

public class AtmTransferState implements AtmState {
   @Override
   public void next(Atm atm) {
      atm.setState(new AtmTransferMoneyState());
   }

   @Override
   public void prev(Atm atm) {
      atm.setState(new AtmStateStarted());
   }

   @Override
   public void exec(Atm atm) {
      CardEntity card = atm.getCard();
      ClientIdentityEntity owner = card.getOwner();
      List<CardEntity> cards = owner.getCards();

      List<String> links = new ArrayList<>(cards.size() - 1);
      for (CardEntity cardEntity : cards) {
         if (!cardEntity.getId().equals(card.getId())) {
            links.add("atm/transfer/" + cardEntity.getCardNumber());
         }
      }

      atm.setLinks(links);
      atm.setMessage("Please choose card and amount to transfer");
   }
}
