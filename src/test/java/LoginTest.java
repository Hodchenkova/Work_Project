import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.JsonFixture;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoginTest {
    String token = "";
    String headerToken = "Bearer " + token;


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
                .post("http://uniorder.pro/api/login")
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
                get("http://uniorder.pro/api/profile/self").
                then().
                log().all().
                body("user.email", equalTo("vasily@test.test")).
                        statusCode(200));

    }

}

