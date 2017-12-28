import utils.CategoryForProduct;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ProductTests {
    String token = "";
    String baseURL = "http://uniorder.pro/api";
    String productId;
    String categoryId;

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
    public void addCategoryToProduct() {

        JsonFixture jsonFixture = new JsonFixture();
        String product = jsonFixture.jsonForCreateProduct();
        String category = jsonFixture.JsonForCategory();

        productId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(product).
                when().
                post(baseURL + "/product").
                then().
                log().all().
                extract().
                path("_id").toString();



        categoryId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(category).
                when().
                post(baseURL + "/category").
                then().
                log().all().
                extract().
                path("_id").toString();

        CategoryForProduct categoryForProduct = new CategoryForProduct();
        categoryForProduct.setCategory_id(categoryId);

        String addCategoryToProduct = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(categoryForProduct).
                when().
                post(baseURL + "/product/" + productId + "/category").
                then().
                log().all().
                extract().
                path("_id").toString();

        String response = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId + "/category").asString();

        assertTrue(response.contains(categoryId));


        ValidatableResponse delShop = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                delete(baseURL + "/product/" + productId + "/category/" + categoryId).
                then().
                log().all().
                statusCode(200);

        ValidatableResponse confirmDelete = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId + "/category").
                then().
                log().all().
                statusCode(200);
        assertNotEquals("_id",categoryId);
    }
    }

