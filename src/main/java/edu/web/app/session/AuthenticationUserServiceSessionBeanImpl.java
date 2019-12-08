package edu.web.app.session;

import edu.web.app.dto.Credentials;
import edu.web.app.dto.SignupRequest;
import edu.web.app.entity.CardEntity;
import edu.web.app.entity.CardType;
import edu.web.app.entity.ClientIdentityEntity;
import edu.web.app.exception.AuthenticationException;
import edu.web.app.exception.RegistrationCollectedException;
import edu.web.app.exception.RegistrationException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Stateless
public class AuthenticationUserServiceSessionBeanImpl implements AuthenticationUserServiceSessionBean {
   @PersistenceContext(unitName = "persistence")
   private EntityManager entityManager;

   @Override
   public ClientIdentityEntity login(Credentials credentials) {
      String hashOfPassword = DigestUtils.md5Hex(credentials.getPassword());
      List res;
      if (credentials.isEmail()) {
         res = entityManager.createQuery(
               "select client from ClientIdentityEntity client " +
                     "inner join client.privacy as privacy " +
                     "where privacy.email = ?1 and privacy.password = ?2")
               .setParameter(1, credentials.getLogin())
               .setParameter(2, hashOfPassword)
               .getResultList();
         if (CollectionUtils.isEmpty(res)) {
            throw new AuthenticationException("Invalid login or password");
         }
         return (ClientIdentityEntity) res.iterator().next();
      } else if (credentials.isPhone()) {
         res = entityManager.createQuery(
               "select client from ClientIdentityEntity client " +
                     "inner join client.privacy as privacy " +
                     "where privacy.phone = ?1 and privacy.password = ?2")
               .setParameter(1, credentials.getLogin())
               .setParameter(2, hashOfPassword)
               .getResultList();
         if (CollectionUtils.isEmpty(res)) {
            throw new AuthenticationException("Invalid login or password");
         }
         return (ClientIdentityEntity) res.iterator().next();
      } else {
         throw new AuthenticationException("Invalid login or password");
      }
   }


   @Override
   public void signup(SignupRequest signupRequest) {
      ClientIdentityEntity identityEntity = signupRequest.toIdentity();
      List<RegistrationException> registrationExceptions = checkIfClientMightBeCreated(signupRequest);

      if (!registrationExceptions.isEmpty()) {
         throw new RegistrationCollectedException(registrationExceptions);
      }

      CardEntity first = createCardEntity(signupRequest.getEmail());
      identityEntity.setCards(Collections.singletonList(first));
      first.setOwner(identityEntity);

      String password = identityEntity.getPrivacy().getPassword();
      identityEntity.getPrivacy().setPassword(DigestUtils.md5Hex(password));

      entityManager.persist(identityEntity);
      entityManager.flush();
   }

   private List<RegistrationException> checkIfClientMightBeCreated(SignupRequest request) {
      List<RegistrationException> violations = new LinkedList<>();

      checkIfExistAndThen(violations, "Such phone number is existing",
            entityManager.createQuery(
                  "select count(privacy) from UserPrivacyEntity privacy where privacy.phone = ?1")
                  .setParameter(1, request.getPhone()));

      checkIfExistAndThen(violations, "Such email is existing",
            entityManager.createQuery(
                  "select count(privacy) from UserPrivacyEntity privacy where privacy.email = ?1")
                  .setParameter(1, request.getEmail()));

      checkIfExistAndThen(violations, "Such serial is existing",
            entityManager.createQuery(
                  "select count(client) from ClientIdentityEntity client where client.serial = ?1")
                  .setParameter(1, request.getSerial()));

      checkIfExistAndThen(violations, "Such code is existing",
            entityManager.createQuery("select count(client) from ClientIdentityEntity client where client.code = ?1")
                  .setParameter(1, request.getCode()));

      return violations;
   }

   private void checkIfExistAndThen(List<RegistrationException> violations, String message, Query query) {
      List resultList = query.getResultList();
      long result = CollectionUtils.isEmpty(resultList) ? 0 : (Long) resultList.iterator().next();

      if (result > 0) {
         violations.add(new RegistrationException(message));
      }
   }

   private CardEntity createCardEntity(String email) {
      CardEntity entity = new CardEntity();
      entity.setActive(true);
      entity.setExpireDate(Timestamp.from(Instant.now().plus(6311385, ChronoUnit.SECONDS)));
      entity.setCurrency("USD");
      entity.setCvv(ThreadLocalRandom.current().nextInt(100, 1000));
      entity.setCurrentAmount(BigDecimal.ZERO);
      entity.setPinCode("0000");
      entity.setMeta("The first USD debit card of user " + email);
      entity.setType(CardType.DEBIT);
      entity.setCardNumber(RandomStringUtils.randomNumeric(16));

      return entity;
   }
}
