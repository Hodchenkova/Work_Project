import JsonForModules.Customer;
import JsonForModules.Login;
import JsonForModules.Shops;
import JsonForModules.UpdateCustomer;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class CustomerCRUD {

        String token = "";
        String baseURL = "http://uniorder.pro/api";
        String customerId;
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
    public void customerCRUD(){
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

            Customer customer = new Customer();
            customer.setName("Иванов Иван");
            customer.setShop_id(shopId);
            customer.setPhone("+380672222222");

            customerId = given().
                    header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    body(customer).
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

            UpdateCustomer updateCustomer = new UpdateCustomer();
            updateCustomer.setName("Test Customer");
            updateCustomer.setShop_id(shopId);
            updateCustomer.setPhone("+380671111111");

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
