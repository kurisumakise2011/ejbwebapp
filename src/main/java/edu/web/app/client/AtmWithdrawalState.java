package edu.web.app.client;

import java.util.Arrays;
import java.util.List;

public class AtmWithdrawalState implements AtmState {
   private static final List<String> OPERATIONS = Arrays.asList(
         "/atm/withdrawal/100",
         "/atm/withdrawal/200",
         "/atm/withdrawal/500",
         "/atm/withdrawal/1000");


   @Override
   public void next(Atm atm) {
      atm.setState(new AtmTakeCache());
   }

   @Override
   public void prev(Atm atm) {
      atm.setState(new AtmStateStarted());
   }

   @Override
   public void exec(Atm atm) {
      atm.setMessage("Please choose sum or enter other one that aliquot to 100");
      atm.setLinks(OPERATIONS);
   }
}
