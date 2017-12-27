import JsonForModules.Login;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertNotNull;

public class LoginTest {
    String token = "";
    String headerToken = "Bearer " + token;
//    @Test
//    public static void login() {
//
//
//       String response = given().
//                header("Content-Type", "application/json").
//                        body( "{\n" + " \"email\": \"vasily@test.test\",\n" +
//                                "    \"password\": \"123456\"\n" + "} ").
//                when().
//                post("http://uniorder.pro/api/login").
//                then().extract().
//                 asString();
//        System.out.println(response);
//    }

    @BeforeTest
    public void authenticate() {
        Login login = new Login();
        login.setEmail("vasily@test.test");
        login.setPassword("123456");

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

