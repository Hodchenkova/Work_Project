import JsonForModules.Login;
import JsonForModules.PriceTypes;
import JsonForModules.UpdatePriceType;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertTrue;

public class PriceTypeCRUD {

    String token = "";
    String baseURL = "http://uniorder.pro/api";

    @BeforeTest
    public void authenticate() {
        Login login = new Login();
        login.setEmail("testuser@test.test");
        login.setPassword("123456");

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
        String priceTypeId;
        PriceTypes priceTypes = new PriceTypes();
        priceTypes.setName("Розница");
        priceTypes.setCurrency("USD");

        priceTypeId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(priceTypes).
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

        UpdatePriceType updatePriceType = new UpdatePriceType();
        updatePriceType.setName("Test PriceType");
        updatePriceType.setCurrency("UAH");


        ValidatableResponse updatePriceTypes = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updatePriceType).
                when().
                put(baseURL + "/price-type/"+ priceTypeId).
                then().statusCode(200).
                log().all().
                body("name",equalTo("Test PriceType"));


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

