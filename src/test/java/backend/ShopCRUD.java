import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.JsonFixture;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class ShopCRUD {
    String token = "";
    String baseURL = "http://uniorder.pro/api";
   public static String shopId;

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
    public void shopCRUD(){

        JsonFixture jsonFixture = new JsonFixture();
        String shop = jsonFixture.jsonForCreateShop();

        shopId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(shop).
                when().
                post(baseURL + "/shop").
                then().
                log().all().
                extract().
                path("_id").toString();

        String response = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/shop").asString();

        assertTrue(response.contains(shopId));

        String customer = jsonFixture.jsonForCreateCustomer(shopId);

        ValidatableResponse getCreatedShop = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/shop/"+ shopId).
                then().statusCode(200).
                log().all().
                body("_id",equalTo(shopId));


        String updateShop = jsonFixture.jsonForUpdateShop();

        ValidatableResponse updateShops = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateShop).
                when().
                put(baseURL + "/shop/"+ shopId).
                then().statusCode(200).
                log().all().
                body("name",equalTo("Test Shop")).
                body("status", equalTo(0)).
                body("type",equalTo("magento2"));


        ValidatableResponse delShop = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateShop).
                when().
                delete(baseURL + "/shop/"+ shopId).
                then().
                log().all().
                statusCode(200);

        ValidatableResponse confirmDelete = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/shop/"+ shopId).
                then().
                log().all().
                statusCode(404);



    }
}
