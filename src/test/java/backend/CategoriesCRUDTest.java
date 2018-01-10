package backend;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class CategoriesCRUDTest {
    String token = "";
    String baseURL = "http://uniorder.pro/api";

    @BeforeTest (groups = {"category","backend"})
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

    @Test(groups = {"category","backend"})
    public void categoryCRUD(){
    String categoryId;

    JsonFixture jsonFixture = new JsonFixture();
        String createCategoryJson = jsonFixture.JsonForCategory();

        categoryId = given().
            header("Content-Type", "application/json").
            header("Authorization","Bearer "+ token).
            body(createCategoryJson).
            when().
            post(baseURL + "/category").
            then().
            log().all().
            extract().
            path("_id").toString();

    String response = given().
            header("Content-Type", "application/json").
            header("Authorization","Bearer "+ token).
            when().
            get(baseURL + "/category").asString();

        assertTrue(response.contains(categoryId));

        ValidatableResponse getCreatedCategory = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get(baseURL + "/category/"+ categoryId).
                then().statusCode(200).
                body("_id",equalTo(categoryId));



        String updateCategotyJson = jsonFixture.jsonForUpdateCategory();

        ValidatableResponse updateCategory = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateCategotyJson).
                when().
                put(baseURL + "/category/"+ categoryId).
                then().statusCode(200).
                log().all().
                body("name",equalTo("Test Category"));


        ValidatableResponse delCategory = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateCategotyJson).
                when().
                delete("http://uniorder.pro/api/category/"+ categoryId).
                then().
                log().all().
                statusCode(200);

        ValidatableResponse confirmDelete = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                when().
                get("http://uniorder.pro/api/category/"+ categoryId).
                then().
                log().all().
                statusCode(404);



    }

}
