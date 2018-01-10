package backend;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ProductTests {
    String token = "";
    String baseURL = "http://uniorder.loc/api";
    String productId = "";
    String categoryId = "";
    String priceTypeId = "";
    String shopId = "";
    String supplierId = "";

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


            productId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product").
                    then().
//                log().all().
                extract().
                        path("data._id[1]").toString();

        System.out.println(productId);
    }

    @Test (groups = {"products", "backend" })
    public void addCategoryToProduct() {

        JsonFixture jsonFixture = new JsonFixture();
        String product = jsonFixture.jsonForCreateProduct();
        String category = jsonFixture.JsonForCategory();

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

        String jsonForAddCategoryToProduct = jsonFixture.jsonForAddCategoryToProduct(categoryId);

        String addCategoryToProduct = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddCategoryToProduct).
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

    @Test (groups = {"products","backend"})
    public void addPriceToProduct(){
        JsonFixture jsonFixture = new JsonFixture();
        String jsonForCreatePrice = jsonFixture.jsonForCreatePriceType();

        priceTypeId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(jsonForCreatePrice).
                when().
                post(baseURL + "/price-type").
                then().
                log().all().
                extract().
                path("_id").toString();

        String jsonForAddPriceToProduct = jsonFixture.jsonForAddPriceToProduct(priceTypeId);

        String addPriceToProduct = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddPriceToProduct).
                when().
                post(baseURL + "/product/" + productId + "/price").
                then().
                log().all().
                extract().
                path("_id").toString();

        String newPriceId = addPriceToProduct;

        System.out.println(newPriceId);


        String response = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).asString();

        assertTrue(response.contains(priceTypeId));

        ValidatableResponse addOneMorePriceToProduct = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddPriceToProduct).
                when().
                post(baseURL + "/product/" + productId + "/price").
                then().
                log().all().
                statusCode(500);

        String jsonForUpdatePriceToProduct = jsonFixture.jsonForUpdatePriceToProduct(priceTypeId);

        ValidatableResponse updatePriceToProduct = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForUpdatePriceToProduct).
                when().
                put(baseURL + "/product/" + productId + "/price/"+ newPriceId).
                then().
                log().all().
                body("price", equalTo(50));

        ValidatableResponse delPrice = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                delete(baseURL + "/product/" + productId + "/price/" + newPriceId).
                then().
                log().all().
                statusCode(200);

        ValidatableResponse confirmDelete = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).
                then().
                log().all().
                statusCode(200);

        assertNotEquals("price_type_id",priceTypeId);

    }


    @Test (groups = {"products","backend"})
    public void addPhotoProductByUrl(){
        JsonFixture jsonFixture = new JsonFixture();
        String jsonForAddPhoto = jsonFixture.jsonForAddPhotoByURLToProduct();
        String imageId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddPhoto).
                when().
                post(baseURL + "/product/" + productId + "/image/url").
                then().
                log().all().
                extract().
                path("_id").toString();

        System.out.println(imageId);

        String response = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).asString();

        assertTrue(response.contains(imageId));

        ValidatableResponse deletePhotoFromProduct = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                delete(baseURL + "/product/" + productId + "/image/" + imageId).
                then().
                log().all().
                statusCode(200);


        String confirmDelete = String.valueOf(given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).
                then().
                log().all().
                statusCode(200));

        assertFalse(confirmDelete.contains(imageId));

    }

    @Test (groups = {"products","backend"})
    public void addCustomAttributeToProduct(){
        JsonFixture jsonFixture = new JsonFixture();
        String jsonForAddCustomAttributetoProduct = jsonFixture.jsonForAddCustomAttributetoProduct();
        String attributeId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddCustomAttributetoProduct).
                when().
                post(baseURL + "/product/" + productId + "/custom-attribute").
                then().
                log().all().
                extract().
                path("_id").toString();

        System.out.println(attributeId);

        String response = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).asString();

        assertTrue(response.contains(attributeId));

        ValidatableResponse deleteCustomAttributeFromProduct = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                delete(baseURL + "/product/" + productId + "/custom-attribute/" + attributeId).
                then().
                log().all().
                statusCode(200);


        String confirmDelete = String.valueOf(given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).
                then().
                log().all().
                statusCode(200));

        assertFalse(confirmDelete.contains(attributeId));

    }

    @Test (groups = {"products","backend"})
    public void addShopInfoToProduct(){
        JsonFixture jsonFixture = new JsonFixture();
        String jsonForCreateShop = jsonFixture.jsonForCreateShop();
        String jsonForCreatePriceType = jsonFixture.jsonForCreatePriceType();

        shopId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(jsonForCreateShop).
                when().
                post(baseURL + "/shop").
                then().
                log().all().
                extract().
                path("_id").toString();

        priceTypeId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(jsonForCreatePriceType).
                when().
                post(baseURL + "/price-type").
                then().
                log().all().
                extract().
                path("_id").toString();

        String jsonForAddShopInfoToProduct = jsonFixture.jsonForAddShopInfoToProduct(shopId, priceTypeId);

        String newShopId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddShopInfoToProduct).
                when().
                post(baseURL + "/product/" + productId + "/shop").
                then().
                log().all().
                extract().
                path("_id").toString();

        System.out.println(newShopId);

        String response = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).asString();

        assertTrue(response.contains(newShopId));

    String jsonForUpdateShopInfoToProduct = jsonFixture.jsonForUpdateShopInfoToProduct(shopId, priceTypeId);

        ValidatableResponse updatePriceToProduct = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForUpdateShopInfoToProduct).
                when().
                put(baseURL + "/product/" + productId + "/shop/"+ newShopId).
                then().
                log().all().
                body("promotion_price", equalTo(350)).
                body("active",  equalTo(true));


        ValidatableResponse deleteShopInfoFromProduct = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                delete(baseURL + "/product/" + productId + "/shop/" + newShopId).
                then().
                log().all().
                statusCode(200);


        String confirmDelete = String.valueOf(given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).
                then().
                log().all().
                statusCode(200));

        assertFalse(confirmDelete.contains(newShopId));




    }
    @Test (groups = {"products","backend"})
    public void addSupplierInfoToProduct(){
        JsonFixture jsonFixture = new JsonFixture();
        String jsonForCreateSupplier = jsonFixture.jsonForCreateSupplier();


        supplierId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(jsonForCreateSupplier).
                when().
                post(baseURL + "/supplier").
                then().
                log().all().
                extract().
                path("_id").toString();

        System.out.println(supplierId);


        String jsonForAddSupplierInfoToProduct = jsonFixture.jsonForAddSupplierInfoToProduct(supplierId);

        String newSupplierId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddSupplierInfoToProduct).
                when().
                post(baseURL + "/product/" + productId + "/supplier").
                then().
                log().all().
                extract().
                path("_id").toString();

        System.out.println(newSupplierId);

        String response = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).asString();

        assertTrue(response.contains(newSupplierId));

        String jsonForUpdateSupplierInfoToProduct = jsonFixture.jsonForUpdateSupplierInfoToProduct(supplierId);

        ValidatableResponse updatePriceToProduct = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForUpdateSupplierInfoToProduct).
                when().
                put(baseURL + "/product/" + productId + "/supplier/"+ newSupplierId).
                then().
                log().all().
                body("qty", equalTo(20)).
                body("sale_price",  equalTo(200)).
                body("purchase_price",  equalTo(180));


        ValidatableResponse deleteSupplierInfoFromProduct = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                delete(baseURL + "/product/" + productId + "/supplier/" + newSupplierId).
                then().
                log().all().
                statusCode(200);


        String confirmDelete = String.valueOf(given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).
                then().
                log().all().
                statusCode(200));

        assertFalse(confirmDelete.contains(newSupplierId));




    }

    @Test (groups = {"products","backend"})
    public void addStockToProduct(){
        JsonFixture jsonFixture = new JsonFixture();
        String jsonForCreateWarwhouse = jsonFixture.jsonForCreateWarehouse();


       String warehouseId = given().
                header("Content-Type", "application/json").
                header("Authorization","Bearer "+ token).
                body(jsonForCreateWarwhouse).
                when().
                post(baseURL + "/warehouse").
                then().
                log().all().
                extract().
                path("_id").toString();

        System.out.println(warehouseId);


        String jsonForAddStockToProduct = jsonFixture.jsonToAddStockToProduct(warehouseId);

        String stockId = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddStockToProduct).
                when().
                post(baseURL + "/product/" + productId + "/stock").
                then().
                log().all().
                extract().
                path("_id").toString();

        System.out.println(stockId);

        String response = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).asString();

        assertTrue(response.contains(stockId));

        String jsonForUpdateStockToProduct = jsonFixture.jsonToUpdateStockToProduct(warehouseId);

        ValidatableResponse updateStockToProduct = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForUpdateStockToProduct).
                when().
                put(baseURL + "/product/" + productId + "/stock/"+ stockId).
                then().
                log().all().
                body("qty", equalTo(10));


        ValidatableResponse addMoreOneWarehouseStock = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(jsonForAddStockToProduct).
                when().
                post(baseURL + "/product/" + productId + "/stock").
                then().
                log().all().
               statusCode(500);

        ValidatableResponse deleteStockFromProduct = given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                delete(baseURL + "/product/" + productId + "/stock/" + stockId).
                then().
                log().all().
                statusCode(200);


        String confirmDelete = String.valueOf(given().header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get(baseURL + "/product/" + productId).
                then().
                log().all().
                statusCode(200));

        assertFalse(confirmDelete.contains(stockId));




    }

}



