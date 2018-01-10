package backend;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoginTest {
    String token = "";
    String baseURL = "http://uniorder.loc/api";

    @BeforeTest
    public void authenticate() {
        JsonFixture jsonFixture = new JsonFixture();
        String login = jsonFixture.jsonForLogin();
        token = given()
                .accept("application/json")
                .contentType("application/json")
                .body(login)
                .expect()
                .statusCode(200)
                .when()
                .post(baseURL + "/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("user.api_token").toString();

        System.out.println(token);

    }
    @Test
    public void profileMyself() {

        String userProfile = String.valueOf(given().
                header("Content-Type", "application/json").
        header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/profile/self").
                then().
                log().all().
                body("user.email", equalTo("testuser@test.test")).
                        statusCode(200));

    }


}

