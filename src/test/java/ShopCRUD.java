import JsonForModules.Login;
import JsonForModules.Shops;
import JsonForModules.UpdateShop;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class ShopCRUD {
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
    public void shopCRUD(){
        String shopId;

        Shops shops = new Shops();
        shops.setName("Магазин 1");
        shops.setStatus(1);
        shops.setType("magento2");

        shopId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(shops).
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


        ValidatableResponse getCreatedShop = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/shop/"+ shopId).
                then().statusCode(200).
                log().all().
                body("_id",equalTo(shopId));

        UpdateShop updateShop = new UpdateShop();
        updateShop.setName("Test Shop");
        updateShop.setStatus(0);
        updateShop.setType("magento2");

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
