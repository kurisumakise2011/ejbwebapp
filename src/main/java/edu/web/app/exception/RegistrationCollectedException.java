package edu.web.app.exception;

import java.util.List;

public class RegistrationCollectedException extends RuntimeException {
   private List<RegistrationException> violations;

   public RegistrationCollectedException(List<RegistrationException> violations) {
      this.violations = violations;
   }

   public List<RegistrationException> getViolations() {
      return violations;
   }
}
