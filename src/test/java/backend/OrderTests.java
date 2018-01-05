package backend;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class OrderTests {
    String token = "";
    String baseURL = "http://uniorder.pro/api";

   static String shopId = "";
   public static String customerId = "";
   public static String productId = "";
   public static String orderId = "";

    @BeforeTest (groups = "backend")
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
                .post(baseURL +"/login")
                .then()
//                .log().all()
                .statusCode(200)
                .extract()
                .path("user.api_token").toString();

        System.out.println(token);

    }

 @Test (groups = { "orders", "backend"})
    public void createOrder() {

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


     String jsonForCreateOrder = jsonFixture.jsonForCreateOrder(shopId);

     orderId =  given().
             header("Content-Type", "application/json").
             header("Authorization","Bearer "+ token).
             body(jsonForCreateOrder).
             when().
             post(baseURL + "/order").
             then().
             log().all().
             extract().
             path("_id").toString();

     String response = given().
             header("Content-Type", "application/json").
             header("Authorization","Bearer "+ token).
             when().
             get(baseURL + "/order").asString();

     assertTrue(response.contains(orderId));


     ValidatableResponse getCreatedOrder = given().header("Content-Type", "application/json").
             header("Authorization","Bearer "+ token).
             when().
             get(baseURL + "/order/"+ orderId).
             then().statusCode(200).
             log().all().
             body("_id",equalTo(orderId));
 }

 @Test (groups = { "orders", "backend"})
    public void updateOrder(){
        JsonFixture jsonFixture = new JsonFixture();
        String jsonForUpdateOrder = jsonFixture.jsonForUpdateOrder(shopId);

     ValidatableResponse updateOrder = given().header("Content-Type", "application/json").
             header("Authorization","Bearer "+ token).
             body(jsonForUpdateOrder).
             when().
             put(baseURL + "/order/"+ orderId).
             then().
             statusCode(200).
             log().all().
             body("comment",equalTo("zzz")).
             body("status_id", equalTo(5));
 }

    @Test (groups = { "orders", "backend"})
    public void addCustomerToOrder(){
        JsonFixture jsonFixture = new JsonFixture();


        String jsonForCreateCustomer = jsonFixture.jsonForCreateCustomer(shopId);
        customerId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(jsonForCreateCustomer).
                when().
                post(baseURL + "/customer").
                then().
                log().all().
                extract().
                path("_id").toString();
        String jsonForAddCustomertoOrder = jsonFixture.jsonForAddCustomerToOrder(customerId);
        ValidatableResponse addCustomerToOrder = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(jsonForAddCustomertoOrder).
                when().
                post(baseURL + "/order/"+ orderId + "/customer").
                then().statusCode(200).
                log().all().
                body("customer_id",equalTo(customerId));
    }

    @Test (groups = { "orders", "backend"})
    public void addProductToOrder() {
        JsonFixture jsonFixture = new JsonFixture();

        String jsonForCreateProduct = jsonFixture.jsonForCreateProduct();
        String jsonForAddProductToOrder = jsonFixture.jsonForAddProductToOrder(productId);
        String jsonForUpdateProductToOrder = jsonFixture.jsonForUpdateProductToOrder(productId);

        productId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForCreateProduct).
                when().
                post(baseURL + "/product").
                then().
                log().all().
                extract().
                path("_id").toString();

        ValidatableResponse addProductToOrder = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddProductToOrder).
                when().
                post(baseURL + "/order/" + orderId + "/product").
                then().statusCode(200).
                log().all().
                body("product_id", equalTo(productId));

        ValidatableResponse updateProductToOrder = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForUpdateProductToOrder).
                when().
                post(baseURL + "/order/" + orderId + "/product/" + productId).
                then().statusCode(200).
                log().all().
                body("product_id", equalTo(productId)).
                body("quantity", equalTo(10)).
                body("price", equalTo(30.5));

        ValidatableResponse deleteProductFromOrder = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/order/" + orderId + "/product/" + productId).
                then().
                log().all().
                statusCode(200);


        ValidatableResponse confirmDeleteProductFromOrder = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/order/" + orderId).
                then().
                assertThat().
                body("product._id", not(hasItem(productId)));

    }

}
