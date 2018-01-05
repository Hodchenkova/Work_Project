import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.JsonFixture;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertTrue;

public class PriceTypeCRUD {

    String token = "";
    String baseURL = "http://uniorder.pro/api";
    String priceTypeId;

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
//                .log().all()
                .statusCode(200)
                .extract()
                .path("user.api_token").toString();
    }

    @Test
    public void priceTypeCRUD(){


        JsonFixture jsonFixture = new JsonFixture();
        String priceType = jsonFixture.jsonForCreatePriceType();

        priceTypeId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(priceType).
                when().
                post(baseURL + "/price-type").
                then().
                log().all().
                extract().
                path("_id").toString();

        String response = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/price-type").asString();

        assertTrue(response.contains(priceTypeId));

        ValidatableResponse getCreatedPriceType = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/price-type/"+ priceTypeId).
                then().statusCode(200).
                log().all().
                body("_id",equalTo(priceTypeId));


        String updatePriceType = jsonFixture.jsonForUpdatePriceType();


        ValidatableResponse updatePriceTypes = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updatePriceType).
                when().
                put(baseURL + "/price-type/"+ priceTypeId).
                then().statusCode(200).
                log().all().
                body("name",equalTo("Test Price Type"));


        ValidatableResponse delPriceType = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updatePriceType).
                when().
                delete(baseURL + "/price-type/"+ priceTypeId).
                then().
                log().all().
                statusCode(200);

        ValidatableResponse confirmDelete = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/price-type/"+ priceTypeId).
                then().
                log().all().
                statusCode(404);
   }
    }

