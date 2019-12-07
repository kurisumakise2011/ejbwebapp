package rest;

import edu.web.app.dto.SignupRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class RestHelper {

   @Test
   public void should_signup_user() {
      SignupRequest signupRequest = new SignupRequest();

      signupRequest.setEmail(RandomStringUtils.randomAlphabetic(3) + "@gmail.com");
      signupRequest.setPassword("1");
      signupRequest.setName("Username");
      signupRequest.setAddress("Line 123");
      signupRequest.setCode(RandomStringUtils.randomNumeric(6));
      signupRequest.setSerial("ET" + RandomStringUtils.randomNumeric(6));
      signupRequest.setDescription("Description");
      signupRequest.setPhone(RandomStringUtils.randomAlphanumeric(10));

      given().
            contentType("application/json").
            body(signupRequest).
            when().
            post("http://localhost:8080/signup").
            then().
            assertThat().statusCode(201);

      System.out.println(signupRequest);
   }

}
