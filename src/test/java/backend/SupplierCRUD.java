package backend;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class SupplierCRUD {
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
    }

        @Test
        public void supplierCRUD(){

        String supplierId;

            JsonFixture jsonFixture = new JsonFixture();
            String supplier = jsonFixture.jsonForCreateSupplier();

            supplierId = given().
                    header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    body(supplier).
                    when().
                    post(baseURL + "/supplier").
                    then().
                    log().all().
                    extract().
                    path("_id").toString();

            String response = given().
                    header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    when().
                    get(baseURL + "/supplier").asString();

            assertTrue(response.contains(supplierId));


            ValidatableResponse getCreatedSupplier = given().header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    when().
                    get(baseURL + "/supplier/"+ supplierId).
                    then().statusCode(200).
                    log().all().
                    body("_id",equalTo(supplierId));

            String updateSupplier = jsonFixture.jsonForUpdateSupplier();

            ValidatableResponse updateSupplieer = given().header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    body(updateSupplier).
                    when().
                    put(baseURL + "/supplier/"+ supplierId).
                    then().statusCode(200).
                    log().all().
                    body("name",equalTo("Test Supplier"));



            ValidatableResponse delSupplier = given().header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    body(updateSupplier).
                    when().
                    delete(baseURL + "/supplier/"+ supplierId).
                    then().
                    log().all().
                    statusCode(200);

            ValidatableResponse confirmDelete = given().header("Content-Type", "application/json").
                    header("Authorization","Bearer "+ token).
                    when().
                    get(baseURL + "/supplier/"+ supplierId).
                    then().
                    log().all().
                    statusCode(404);
        }

    }
