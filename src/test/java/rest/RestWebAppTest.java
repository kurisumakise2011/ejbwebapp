package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.internal.RestAssuredResponseImpl;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import com.jayway.restassured.response.Cookie;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import edu.web.app.dto.CardDto;
import edu.web.app.dto.ClientDto;
import edu.web.app.dto.Credentials;
import edu.web.app.dto.SignupRequest;
import edu.web.app.entity.ClientIdentityEntity;
import edu.web.app.entity.UserPrivacyEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RestWebAppTest {
   private static EntityManager entityManager;
   private static Gson gson =  new GsonBuilder()
         .serializeNulls()
         .setDateFormat("yyyy-MM-dd HH:mm:ss")
         .setPrettyPrinting()
         .create();

   @BeforeClass
   public static void beforeClass() {
      RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)
            .gsonObjectMapperFactory((aClass, s) -> gson));

      entityManager = Persistence.createEntityManagerFactory("test").createEntityManager();
   }


   @Before
   public void beforeEach() {
      Set<String> statements = new LinkedHashSet<>(Arrays.asList(
            "DELETE FROM PaymentTransactionEntity entity",
            "DELETE FROM CardEntity entity",
            "DELETE FROM ClientIdentityEntity entity",
            "DELETE FROM UserPrivacyEntity entity"
      ));

      for (String query : statements) {
         entityManager.getTransaction().begin();
         entityManager.createQuery(query).executeUpdate();
         entityManager.getTransaction().commit();
      }
   }

   @Test
   public void should_return_invalid_login_or_password_error() {
      Credentials credentials = new Credentials();

      credentials.setLogin("abc");
      credentials.setPassword("123");

      given().
            contentType("application/json").
            body(credentials).
            when().
            post("http://localhost:8080/signin").
            then().
            assertThat().statusCode(401).and().
            assertThat().body("message", equalTo("Invalid login or password"));

   }

   @Test
   public void should_return_invalid_login_or_password_error_with_phone_such_user_does_not_exist() {
      Credentials credentials = new Credentials();

      credentials.setLogin("+380674892810");
      credentials.setPassword("123456");

      given().
            contentType("application/json").
            body(credentials).
            when().
            post("http://localhost:8080/signin").
            then().
            assertThat().statusCode(401).and().
            assertThat().body("message", equalTo("Invalid login or password"));
   }

   @Test
   public void should_return_invalid_login_or_password_error_with_email_such_user_does_not_exist() {
      Credentials credentials = new Credentials();

      credentials.setLogin("simplemail@gmail.com");
      credentials.setPassword("123456");

      given().
            contentType("application/json").
            body(credentials).
            when().
            post("http://localhost:8080/signin").
            then().
            assertThat().statusCode(401).and().
            assertThat().body("message", equalTo("Invalid login or password"));
   }

   @Test
   public void should_signup_user() {
      SignupRequest signupRequest = new SignupRequest();

      signupRequest.setEmail("simplemail@gmail.com");
      signupRequest.setPassword("123456");
      signupRequest.setName("Username");
      signupRequest.setAddress("Line 123");
      signupRequest.setCode("123456");
      signupRequest.setSerial("ET1000000");
      signupRequest.setDescription("Description");
      signupRequest.setPhone("380674892810");

      given().
            contentType("application/json").
            body(signupRequest).
            when().
            post("http://localhost:8080/signup").
            then().
            assertThat().statusCode(201);

      List<ClientIdentityEntity> list = entityManager.createQuery("select client from ClientIdentityEntity client", ClientIdentityEntity.class).getResultList();
      ClientIdentityEntity next = list.iterator().next();
      assertThat(next.getName(), is(signupRequest.getName()));
      assertThat(next.getAddress(), is(signupRequest.getAddress()));
      assertThat(next.getCode(), is(signupRequest.getCode()));
      assertThat(next.getSerial(), is(signupRequest.getSerial()));
      assertThat(next.getDescription(), is(signupRequest.getDescription()));
      assertThat(next.getBanned(), is(false));

      UserPrivacyEntity privacy = next.getPrivacy();
      assertThat(privacy.getEmail(), is(signupRequest.getEmail()));
      assertThat(privacy.getPhone(), is(signupRequest.getPhone()));
      assertThat(privacy.getPassword(), is(DigestUtils.md5Hex(signupRequest.getPassword())));
   }

   @Test
   public void should_get_user_info() {
      SignupRequest signupRequest = new SignupRequest();

      signupRequest.setEmail("simplemail@gmail.com");
      signupRequest.setPassword("123456");
      signupRequest.setName("Username");
      signupRequest.setAddress("Line 123");
      signupRequest.setCode("123456");
      signupRequest.setSerial("ET1000000");
      signupRequest.setDescription("Description");
      signupRequest.setPhone("380674892810");

      given().
            contentType("application/json").
            body(signupRequest).
            when().
            post("http://localhost:8080/signup").
            then().
            assertThat().statusCode(201);

      Credentials credentials = new Credentials();

      credentials.setLogin(signupRequest.getPhone());
      credentials.setPassword(signupRequest.getPassword());

      Response response =
      given().
            contentType("application/json").
            body(credentials).
      when().
            post("http://localhost:8080/signin").
            thenReturn();

      ClientDto body = response.getBody().as(ClientDto.class);
      ClientDto actual = given().
            cookie("JSESSIONID", response.getSessionId()).
      when().
            get("http://localhost:8080/client").
      thenReturn().getBody().as(ClientDto.class);

      assertThat(body, is(actual));
   }

   @Test
   public void should_get_user_cards() {
      SignupRequest signupRequest = new SignupRequest();

      signupRequest.setEmail("simplemail@gmail.com");
      signupRequest.setPassword("123456");
      signupRequest.setName("Username");
      signupRequest.setAddress("Line 123");
      signupRequest.setCode("123456");
      signupRequest.setSerial("ET1000000");
      signupRequest.setDescription("Description");
      signupRequest.setPhone("380674892810");

      given().
            contentType("application/json").
            body(signupRequest).
      when().
            post("http://localhost:8080/signup").
      then()
            .assertThat().statusCode(201);

      Credentials credentials = new Credentials();

      credentials.setLogin(signupRequest.getPhone());
      credentials.setPassword(signupRequest.getPassword());

      Response response = given().
            contentType("application/json").
            body(credentials).
            when().
            post("http://localhost:8080/signin").
            thenReturn();

      ResponseBody body =
      given().
            cookie("JSESSIONID", response.getSessionId()).
      when().
            get("http://localhost:8080/cards").
      thenReturn().
            getBody();

      String json = body.prettyPrint();
      Type listType = new TypeToken<ArrayList<CardDto>>(){}.getType();
      List<CardDto> list = gson.fromJson(json, listType);
      assertThat(list.size(), is(1));
   }

}
