package backend;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class RegistrationTest {
    String baseURL = "http://uniorder.loc/api";

    @Test(groups = {"account", "backend" })
    public void registration() {
        JsonFixture jsonFixture = new JsonFixture();
        String jsonForRegistration = jsonFixture.jsonForRegistration();
        ValidatableResponse registration = given().
                header("Content-Type", "application/json").
                body(jsonForRegistration).
                when().
                post(baseURL + "/register").
                then().
                log().all().
                statusCode(200).
                assertThat().
                body("user.full_name", equalTo("UserTest")).
                body("user.email", equalTo("test2@test.test"));


      String jsonForNewLogin = jsonFixture.jsonForNewLogin();
      String  token = given()
                .accept("application/json")
                .contentType("application/json")
                .body(jsonForNewLogin)
                .expect()
                .statusCode(200)
                .when()
                .post(baseURL + "/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("user.api_token").toString();


        String userProfile = String.valueOf(given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL+"/profile/self").
                then().
                log().all().
                body("user.email", equalTo("test2@test.test")).
                statusCode(200));

        String logout = String.valueOf(given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                post(baseURL+"/logout").
                then().
                log().all().
                body("success", equalTo("User logged out.")).
                statusCode(200));
    }
}
