import JsonForModules.Category;
import JsonForModules.Login;
import JsonForModules.UpdateCategory;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class CategoriesCRUD {
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
                .post(baseURL +"/login")
                .then()
//                .log().all()
                .statusCode(200)
                .extract()
                .path("user.api_token").toString();

        System.out.println(token);

    }

    @Test
    public void categoryCRUD(){
    String categoryId;
    Category category = new Category();
    category.setName("Category 1");
    category.setParentCategory(null);



    categoryId = given().
            header("Content-Type", "application/json").
            header("Authorization","Bearer "+ token).
            body(category).
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

        UpdateCategory updateCategory = new UpdateCategory();
        updateCategory.setName("Test Category");
        updateCategory.setParentCategory(null);

        ValidatableResponse updateCategories = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateCategory).
                when().
                put(baseURL + "/category/"+ categoryId).
                then().statusCode(200).
                log().all().
                body("name",equalTo("Test Category"));


        ValidatableResponse delCategory = given().header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(updateCategory).
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
