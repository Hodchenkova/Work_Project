import JsonForModules.Login;
import JsonForModules.Stores;
import JsonForModules.UpdateStore;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class StoreCRUD {

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
    public void storeCRUD(){
        String storeID;
        Stores stores = new Stores();
        stores.setName("Склад 1");
        stores.setCan_local_pickup(true);
        stores.setPhone("380637737373");
        stores.setAddress("Пушкинская, 1");

        storeID = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(stores).
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

        UpdateStore updateStore = new UpdateStore();
        updateStore.setName("Склад 11");
        updateStore.setCan_local_pickup(false);
        updateStore.setPhone("380637737374");
        updateStore.setAddress("Пушкинская, 2");


        ValidatableResponse updateStores = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateStore).
                when().
                put(baseURL + "/warehouse/"+ storeID).
                then().statusCode(200).
                log().all().
                body("_id",equalTo(storeID)).
                body("name", equalTo("Склад 11")).
                body("can_local_pickup", equalTo(false));


        ValidatableResponse delStores = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateStore).
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
