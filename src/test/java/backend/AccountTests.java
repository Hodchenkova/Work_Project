package backend;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class AccountTests {

    String token = "";
    String baseURL = "http://uniorder.loc/api";


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

    @Test (groups = {"account", "backend" })
    public void account() {
        JsonFixture jsonFixture = new JsonFixture();

        String accountId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/account").
                then().
                log().all().
                extract().path("id").toString();

        String jsonForUpdateAccount = jsonFixture.jsonForUpdateAccount();

        ValidatableResponse updateAccount = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForUpdateAccount).
                when().
                put(baseURL + "/account/" + accountId).
                then().
                log().all().
                statusCode(200).
                assertThat().
                body("name", equalTo("test_std")).
                body("company", equalTo("TestStd")).
                body("site", equalTo("http://test.com"));

        ValidatableResponse deleteAccount = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                delete(baseURL + "/account/" + accountId).
                then().
                log().all().
                statusCode(200).
                assertThat().
                body("active", equalTo(0));
    }

        @Test (groups = {"users", "backend" })
        public void users() {
            JsonFixture jsonFixture = new JsonFixture();
            String jsonForAddUser = jsonFixture.jsonForCreateUser();

            String userId = given().
                    header("Content-Type", "application/json").
                    header("Authorization", "Bearer " + token).
                    body(jsonForAddUser).
                    when().
                    post(baseURL + "/user").
                    then().
                    log().all().
                    extract().path("id").toString();

            String response = given().
                    header("Content-Type", "application/json").
                    header("Authorization", "Bearer " + token).
                    when().
                    get(baseURL + "/user/" + userId).asString();

            assertTrue(response.contains(userId));

            String jsonForUpdateUser = jsonFixture.jsonForUpdateUser();
            ValidatableResponse updateUser = given().
                    header("Content-Type", "application/json").
                    header("Authorization", "Bearer " + token).
                    body(jsonForUpdateUser).
                    when().
                    put(baseURL + "/user/" + userId).
                    then().
                    log().all().
                    body("full_name", equalTo("Petr Petrov Petrovich")).
                    body("email", equalTo("petrov2@test.test")).
                    body("active", equalTo(true));

            ValidatableResponse deleteUser = given().
                    header("Content-Type", "application/json").
                    header("Authorization", "Bearer " + token).
                    when().
                    delete(baseURL + "/user/" + userId).
                    then().
                    log().all().
                    statusCode(200);


            ValidatableResponse confirmDeleteUser = given().
                    header("Content-Type", "application/json").
                    header("Authorization", "Bearer " + token).
                    when().
                    get(baseURL + "/user/" + userId).
                    then().
                    log().all().
                    statusCode(200).
                    assertThat().
                    body("active", equalTo(0));

        }

            @Test (groups = {"users", "backend" })
            public void loginByInvite(){

        JsonFixture jsonFixture = new JsonFixture();

                String jsonForAddUser = jsonFixture.jsonForCreateUser();

                String inviteCode = given().
                        header("Content-Type", "application/json").
                        header("Authorization", "Bearer " + token).
                        body(jsonForAddUser).
                        when().
                        post(baseURL + "/user").
                        then().
                        log().all().
                        extract().path("invite").toString();

        String jsonForLoginWithInvite = jsonFixture.jsonForLoginWithInvite(inviteCode);

        ValidatableResponse loginWithInviteCode = given().
        header("Content-Type", "application/json").
        body(jsonForLoginWithInvite).
        when().
        post(baseURL + "/invite").
        then().
        log().all().
        body("user.email", equalTo("ivanov3@test.test")).
        statusCode(200);





            }
    }
