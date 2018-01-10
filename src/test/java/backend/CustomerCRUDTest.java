package backend;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class CustomerCRUDTest {

        String token = "";
        String baseURL = "http://uniorder.pro/api";
        String customerId;
        String shopId;

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
    public void customerCRUD(){


                JsonFixture jsonFixture = new JsonFixture();
//                String shopId = ShopCRUD.shopId;
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



                String createCustomer = jsonFixture.jsonForCreateCustomer(shopId);

            customerId = given().
                    header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    body(createCustomer).
                    when().
                    post(baseURL + "/customer").
                    then().
                    log().all().
                    extract().
                    path("_id").toString();

            String response = given().
                    header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    when().
                    get(baseURL + "/customer").asString();

            assertTrue(response.contains(customerId));


            ValidatableResponse getCreatedCustomer = given().header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    when().
                    get(baseURL + "/customer/"+ customerId).
                    then().statusCode(200).
                    log().all().
                    body("_id",equalTo(customerId));


                String updateCustomer = jsonFixture.jsonForUpdateCustomer(shopId);

            ValidatableResponse updateCustomerr = given().header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    body(updateCustomer).
                    when().
                    put(baseURL + "/customer/"+ customerId).
                    then().statusCode(200).
                    log().all().
                    body("name",equalTo("Test Customer"));



            ValidatableResponse delCustomer = given().header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    body(updateCustomer).
                    when().
                    delete(baseURL + "/customer/"+ customerId).
                    then().
                    log().all().
                    statusCode(200);

            ValidatableResponse confirmDelete = given().header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    when().
                    get(baseURL + "/customer/"+ customerId).
                    then().
                    log().all().
                    statusCode(404);

        }

}
