package backend;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class ProductCRUD {
    String token = "";
    String baseURL = "http://uniorder.pro/api";

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

        System.out.println(token);

    }

    @Test
    public void productCRUD(){
        String productId;

        JsonFixture jsonFixture = new JsonFixture();
        String createProduct = jsonFixture.jsonForCreateProduct();

        productId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(createProduct).
                when().
                post(baseURL + "/product").
                then().
                log().all().
                extract().
                path("_id").toString();


        String response = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/product").asString();

        assertTrue(response.contains(productId));


        ValidatableResponse getCreatedProduct = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/product/"+ productId).
                then().statusCode(200).
                log().all().
                body("_id",equalTo(productId));

        String updateProduct = jsonFixture.jsonForUpdateProduct();

        ValidatableResponse updateProductt = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateProduct).
                when().
                put(baseURL + "/product/"+ productId).
                then().statusCode(200).
                log().all().
                body("name",equalTo("Test Product")).
                body("sku", equalTo("sku1")).
                body("status",equalTo(0));


        ValidatableResponse delProduct = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateProduct).
                when().
                delete(baseURL + "/product/"+ productId).
                then().
                log().all().
                statusCode(200);

        ValidatableResponse confirmDelete = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/product/"+ productId).
                then().
                log().all().
                statusCode(404);


    }

}
