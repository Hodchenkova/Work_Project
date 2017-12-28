import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.JsonFixture;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class WarehouseCRUD {

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
    public void storeCRUD(){
        String storeID;

        JsonFixture jsonFixture = new JsonFixture();
        String warehouse = jsonFixture.jsonForCreateWarehouse();

        storeID = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(warehouse).
                when().
                post(baseURL + "/warehouse").
                then().
                log().all().
                extract().
                path("_id").toString();

        String response = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get( baseURL + "/warehouse").asString();

        assertTrue(response.contains(storeID));

        ValidatableResponse response1 = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/warehouse/"+ storeID).
                then().statusCode(200).
                body("_id",equalTo(storeID));


        String updateWarehouse = jsonFixture.jsonForUpdateWarehouse();


        ValidatableResponse updateStores = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateWarehouse).
                when().
                put(baseURL + "/warehouse/"+ storeID).
                then().statusCode(200).
                log().all().
                body("_id",equalTo(storeID)).
                body("name", equalTo("Склад 11")).
                body("can_local_pickup", equalTo(false));


        ValidatableResponse delStores = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateWarehouse).
                when().
                delete(baseURL + "/warehouse/"+ storeID).
                then().
                log().all().
                statusCode(200);

        ValidatableResponse confirmDelete = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get( baseURL + "/warehouse/"+ storeID).
                then().
                log().all().
                statusCode(404);
    }
}
