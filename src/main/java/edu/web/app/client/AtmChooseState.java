package edu.web.app.client;

import edu.web.app.exception.AtmProcessException;

import java.util.Arrays;
import java.util.List;

public class AtmChooseState implements AtmState {
   private static final List<String> OPERATIONS = Arrays.asList(
         "/atm/withdrawal",
         "/atm/balance",
         "/atm/transfer",
         "/atm/replenish");

   @Override
   public void next(Atm atm) {
      Atm.AtmOption optionKey = atm.getOption();

      switch (optionKey) {
         case WITHDRAWAL:
            atm.setState(new AtmWithdrawalState());
            break;
         case BALANCE:
            atm.setState(new AtmCheckBalanceState());
            break;
         case TRANSFER:
            atm.setState(new AtmTransferState());
            break;
         case REPLENISH:
            atm.setState(new AtmReplenishState());
            break;
         default:
            throw new AtmProcessException("Could not resolve option for atm please try again!");
      }
   }

   @Override
   public void prev(Atm atm) {
      atm.setState(new AtmStateStarted());
   }

   @Override
   public void exec(Atm atm) {
      atm.setMessage("Please chose operation");
      atm.setLinks(OPERATIONS);
   }
}
