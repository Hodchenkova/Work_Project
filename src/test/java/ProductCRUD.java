import JsonForModules.Login;
import JsonForModules.Product;
import JsonForModules.UpdateProduct;
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

        System.out.println(token);

    }

    @Test
    public void productCRUD(){
        String productId;
        Product product = new Product();
        product.setName("Product 1");
        product.setStatus(1);
        product.setType(1);
        product.setSku("sku1111");
        product.setUnit(1);

        productId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(product).
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

        UpdateProduct updateProduct = new UpdateProduct();
        updateProduct.setName("Test Product");
        updateProduct.setStatus(0);
        updateProduct.setType(1);
        updateProduct.setSku("testsku");
        updateProduct.setUnit(1);

        ValidatableResponse updateProducts = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateProduct).
                when().
                put(baseURL + "/product/"+ productId).
                then().statusCode(200).
                log().all().
                body("name",equalTo("Test Product")).
                body("sku", equalTo("testsku")).
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
