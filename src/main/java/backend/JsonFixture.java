package utils;

import org.json.simple.JSONObject;

public class JsonFixture {


public  String jsonForRegistration(){
    JSONObject jsonForRegistration = new JSONObject();
    jsonForRegistration.put("user_name", "test1");
    jsonForRegistration.put("email", "test1@test.test");
    jsonForRegistration.put("company_name", "test1Co");
    jsonForRegistration.put("password", "123456");
    jsonForRegistration.put("password_confirmation", "123456");

    return jsonForRegistration.toJSONString();

}

public String jsonForLoginWithInvite(){
    JSONObject jsonForLoginWithInvite = new JSONObject();
    jsonForLoginWithInvite.put("email", "");
    jsonForLoginWithInvite.put("code","");
    jsonForLoginWithInvite.put("password","");

    return jsonForLoginWithInvite.toJSONString();
}
    public String jsonForLogin(){

        JSONObject jsonForLogin = new JSONObject();
        jsonForLogin.put("email", "testuser@test.test");
        jsonForLogin.put("password", "123456");
        return jsonForLogin.toJSONString();
    }

    public String jsonRequestRorPasswordReset(){
        JSONObject jsonRequestRorPasswordReset = new JSONObject();
        jsonRequestRorPasswordReset.put("email","testuser@test.test");

        return jsonRequestRorPasswordReset.toJSONString();

    }

    public String jsonForPasswordReset(){
        JSONObject jsonForPasswordReset = new JSONObject();
        jsonForPasswordReset.put("email", "testuser@test.test");
        jsonForPasswordReset.put("token","");
        jsonForPasswordReset.put("password", "123456");
        jsonForPasswordReset.put("password_confirmation", "123456");

        return jsonForPasswordReset.toJSONString();

    }

    public String JsonForCategory(){

        JSONObject jsonForCreateCategory = new JSONObject();
        jsonForCreateCategory.put("name", "Category 1");
        jsonForCreateCategory.put("parent_category", null);

        return jsonForCreateCategory.toJSONString();
    }

    public String jsonForUpdateCategory(){

        JSONObject jsonForUpdateCategory = new JSONObject();
        jsonForUpdateCategory.put("name", "Test Category");
        return jsonForUpdateCategory.toJSONString();
    }

    public String jsonForCreateCustomer(String shopId){

        JSONObject jsonForCreateCustomer = new JSONObject();
        jsonForCreateCustomer.put("name","Иванов Иван Иванович");
        jsonForCreateCustomer.put("shop_id",shopId);
        jsonForCreateCustomer.put("phone","");
        return jsonForCreateCustomer.toJSONString();
    }

    public String jsonForCreateShop(){
        JSONObject jsonForCreateShop = new JSONObject();
        jsonForCreateShop.put("name","Магазин 1");
        jsonForCreateShop.put("status", 1);
        jsonForCreateShop.put("type","magento2");
        return jsonForCreateShop.toJSONString();
    }

    public String jsonForUpdateShop(){
        JSONObject jsonForUpdateShop = new JSONObject();
        jsonForUpdateShop.put("name","Test Shop");
        jsonForUpdateShop.put("status", 0);
        jsonForUpdateShop.put("type","magento2");
        return  jsonForUpdateShop.toJSONString();
    }

    public String jsonForCreateWarehouse(){
        JSONObject jsonForCreateWarehouse = new JSONObject();
        jsonForCreateWarehouse.put("name","Склад 1");
        jsonForCreateWarehouse.put("can_local_pickup", true);
        jsonForCreateWarehouse.put("phone", "380637737373");
        jsonForCreateWarehouse.put("address","Пушкинская, 1");
        return jsonForCreateWarehouse.toJSONString();

    }

    public String jsonForUpdateWarehouse(){
        JSONObject jsonForUpdateWarehouse = new JSONObject();
        jsonForUpdateWarehouse.put("name","Склад 11");
        jsonForUpdateWarehouse.put("can_local_pickup", false);
        jsonForUpdateWarehouse.put("phone", "380637737373");
        jsonForUpdateWarehouse.put("address","Пушкинская, 2");
        return jsonForUpdateWarehouse.toJSONString();

    }

    public String jsonForCreateSupplier(){
        JSONObject jsonForCreateSupplier = new JSONObject();
        jsonForCreateSupplier.put("name","Zara");
        return jsonForCreateSupplier.toJSONString();
    }

    public String jsonForUpdateSupplier(){
        JSONObject jsonForUpdateSupplier = new JSONObject();
        jsonForUpdateSupplier.put("name","Test Supplier");
        return jsonForUpdateSupplier.toJSONString();
    }

    public String jsonForCreatePriceType(){
        JSONObject jsonForCreatePriceType = new JSONObject();
        jsonForCreatePriceType.put("name","Розница");
        jsonForCreatePriceType.put("currency","USD");
        return jsonForCreatePriceType.toJSONString();
    }
    public String jsonForUpdatePriceType() {
        JSONObject jsonForUpdatePriceType = new JSONObject();
        jsonForUpdatePriceType.put("name", "Test Price Type");
        jsonForUpdatePriceType.put("currency", "UAH");
        return jsonForUpdatePriceType.toJSONString();
    }

    ///Account

    public String jsonForUpdateAccount() {
        JSONObject jsonForUpdateAccount = new JSONObject();
        jsonForUpdateAccount.put("name", "test_std");
        jsonForUpdateAccount.put("company", "TestStd");
        jsonForUpdateAccount.put("contact", "Тест Тест");
        jsonForUpdateAccount.put("phone", "+381234567890");
        jsonForUpdateAccount.put("site", "http://test.com");
        jsonForUpdateAccount.put("address", "Одесса, Пушкинская, 1");

        return jsonForUpdateAccount.toJSONString();

    }

    // Users

    public String jsonForCreateUser(){
        JSONObject jsonForCreateUser = new JSONObject();
        jsonForCreateUser.put("user_name", "Inan Ivanov");
        jsonForCreateUser.put("email", "ivanov@test.test");
        jsonForCreateUser.put("password", "123456");

        return jsonForCreateUser.toJSONString();
    }

    public String jsonForUpdateUser(){
        JSONObject jsonForUpdateUser = new JSONObject();
        jsonForUpdateUser.put("full_name", "Petr Petrov Petrovich");
        jsonForUpdateUser.put("email", "petrov@test.test");
        jsonForUpdateUser.put("phone", "+123456789012");
        jsonForUpdateUser.put("active", true);

        return jsonForUpdateUser.toJSONString();
    }

    //Orders

    public String jsonForCreateOrder(String shopId){
        JSONObject jsonForCreateOrder = new JSONObject();
        jsonForCreateOrder.put("order_date", "1513343976000000");
        jsonForCreateOrder.put("shop_id",shopId);

        return jsonForCreateOrder.toJSONString();
    }

    public String jsonForUpdateOrder(String shopId){
        JSONObject jsonForUpdateOrder = new JSONObject();
        jsonForUpdateOrder.put("order_date", "1513343976000111");
        jsonForUpdateOrder.put("shop_id",shopId);

        return jsonForUpdateOrder.toJSONString();
    }
    public String jsonForCnangeOrderStatus(){
    JSONObject jsonForCnangeOrderStatus = new JSONObject();
    jsonForCnangeOrderStatus.put("status_id", 5);

    return jsonForCnangeOrderStatus.toJSONString();
}

   public String jsonForAddCustomerToOrder(String customerId){
        JSONObject jsonForAddCustomerToOrder = new JSONObject();
        jsonForAddCustomerToOrder.put("customer_id", customerId);

        return jsonForAddCustomerToOrder.toJSONString();
   }

   public String jsonForAddProductToOrder(String productId){
       JSONObject jsonForAddProductToOrder = new JSONObject();
       jsonForAddProductToOrder.put("product_id", productId);
       jsonForAddProductToOrder.put("quantity", 5);
       jsonForAddProductToOrder.put("price", 20.5);

       return jsonForAddProductToOrder.toJSONString();
   }

   //Products


    public String jsonForCreateProduct(){

        JSONObject jsonForCreateProduct = new JSONObject();
        jsonForCreateProduct.put("name", "Product 1");
        jsonForCreateProduct.put("status", 1);
        jsonForCreateProduct.put("type", 1);
        jsonForCreateProduct.put("sku", "1212");
        jsonForCreateProduct.put("description", "zzzzzz");
        jsonForCreateProduct.put("volume", "10x1x5:m");
        jsonForCreateProduct.put("weight", "110:kg");
        jsonForCreateProduct.put("unit", 1);

        return jsonForCreateProduct.toJSONString();

    }

    public String jsonForUpdateProduct(){

        JSONObject jsonForUpdateProduct = new JSONObject();
        jsonForUpdateProduct.put("name", "Test Product");
        jsonForUpdateProduct.put("status", 0);
        jsonForUpdateProduct.put("type", 1);
        jsonForUpdateProduct.put("sku", "sku1");
        jsonForUpdateProduct.put("description", "xxxxx");
        jsonForUpdateProduct.put("volume", "10x1x5:m");
        jsonForUpdateProduct.put("weight", "110:kg");
        jsonForUpdateProduct.put("unit", 1);

        return jsonForUpdateProduct.toJSONString();

    }

  public String jsonForAddCategoryToProduct(String categoryId){
        JSONObject jsonForAddCategoryToProduct = new JSONObject();
        jsonForAddCategoryToProduct.put("category_id", categoryId);

        return jsonForAddCategoryToProduct.toJSONString();
  }

  public String jsonForAddPriceToProduct(String priceTypeId){
      JSONObject jsonForAddPriceToProduct = new JSONObject();
      jsonForAddPriceToProduct.put("price_type_id",priceTypeId);
      jsonForAddPriceToProduct.put("price", 25);

      return jsonForAddPriceToProduct.toJSONString();
  }

    public String jsonForUpdatePriceToProduct(String priceTypeId) {
        JSONObject jsonForUpdatePriceToProduct = new JSONObject();
        jsonForUpdatePriceToProduct.put("price_type_id", priceTypeId);
        jsonForUpdatePriceToProduct.put("price", 50);

        return jsonForUpdatePriceToProduct.toJSONString();
    }

    public String jsonForAddPhotoByURLToProduct(){
      JSONObject jsonForAddPhotoToProduct = new JSONObject();
      jsonForAddPhotoToProduct.put("url","");

      return jsonForAddPhotoToProduct.toJSONString();
    }

    public String jsonForAddCustomAttributetoProduct(){
        JSONObject jsonForAddCustomAttributetoProduct = new JSONObject();
        jsonForAddCustomAttributetoProduct.put("name", "Бренд");
        jsonForAddCustomAttributetoProduct.put("type","select");
        jsonForAddCustomAttributetoProduct.put("value","Adidas");

        return jsonForAddCustomAttributetoProduct.toJSONString();
    }

    public String jsonForAddShopInfoToProduct(String shopId, String priceTypeId){
        JSONObject jsonForAddShopInfoToProduct = new JSONObject();
        jsonForAddShopInfoToProduct.put("shop_id", shopId);
        jsonForAddShopInfoToProduct.put("shop_product_id", "222");
        jsonForAddShopInfoToProduct.put("shop_sku","3");
        jsonForAddShopInfoToProduct.put("price_type_id", priceTypeId);
        jsonForAddShopInfoToProduct.put("promotion_price", 300);
        jsonForAddShopInfoToProduct.put("active", false);

        return jsonForAddShopInfoToProduct.toJSONString();
    }

    public String jsonForUpdateShopInfoToProduct(String shopId, String priceTypeId){
        JSONObject jsonForUpdateShopInfoToProduc = new JSONObject();
        jsonForUpdateShopInfoToProduc.put("shop_id", shopId);
        jsonForUpdateShopInfoToProduc.put("shop_product_id", "222");
        jsonForUpdateShopInfoToProduc.put("shop_sku","3");
        jsonForUpdateShopInfoToProduc.put("price_type_id", priceTypeId);
        jsonForUpdateShopInfoToProduc.put("promotion_price", 350);
        jsonForUpdateShopInfoToProduc.put("active", true);

        return jsonForUpdateShopInfoToProduc.toJSONString();
    }

    public String jsonForAddSupplierInfoToProduct(String supplierId){
        JSONObject jsonForAddSupplierInfoToProduct = new JSONObject();
        jsonForAddSupplierInfoToProduct.put("supplier_id", supplierId);
        jsonForAddSupplierInfoToProduct.put("name","Test Product");
        jsonForAddSupplierInfoToProduct.put("sku", "3");
        jsonForAddSupplierInfoToProduct.put("row_number","3");
        jsonForAddSupplierInfoToProduct.put("uploaded_at", 0);
        jsonForAddSupplierInfoToProduct.put("category", "Shoes");
        jsonForAddSupplierInfoToProduct.put("qty", 10);
        jsonForAddSupplierInfoToProduct.put("sale_price",100);
        jsonForAddSupplierInfoToProduct.put("purchase_price", 80);
        jsonForAddSupplierInfoToProduct.put("stock", "dropship");

        return jsonForAddSupplierInfoToProduct.toJSONString();
    }

    public String jsonForUpdateSupplierInfoToProduct(String supplierId){
        JSONObject jsonForUpdateSupplierInfoToProduct = new JSONObject();
        jsonForUpdateSupplierInfoToProduct.put("supplier_id", supplierId);
        jsonForUpdateSupplierInfoToProduct.put("name","Test Test");
        jsonForUpdateSupplierInfoToProduct.put("sku", "3");
        jsonForUpdateSupplierInfoToProduct.put("row_number","3");
        jsonForUpdateSupplierInfoToProduct.put("uploaded_at", 0);
        jsonForUpdateSupplierInfoToProduct.put("category", "Shoes");
        jsonForUpdateSupplierInfoToProduct.put("qty", 20);
        jsonForUpdateSupplierInfoToProduct.put("sale_price",200);
        jsonForUpdateSupplierInfoToProduct.put("purchase_price", 180);
        jsonForUpdateSupplierInfoToProduct.put("stock", "dropship");

        return jsonForUpdateSupplierInfoToProduct.toJSONString();
    }

    public String jsonToAddStockToProduct(String warehouseId){
        JSONObject jsonToAddStockToProduct = new JSONObject();
        jsonToAddStockToProduct.put("warehouse_id", warehouseId);
        jsonToAddStockToProduct.put("qty", 30);

        return jsonToAddStockToProduct.toJSONString();
    }

    public String jsonToUpdateStockToProduct(String warehouseId){
        JSONObject jsonToUpdateStockToProduct = new JSONObject();
        jsonToUpdateStockToProduct.put("warehouse_id", warehouseId);
        jsonToUpdateStockToProduct.put("qty", 10);

        return jsonToUpdateStockToProduct.toJSONString();
    }

    public String jsonForAddSimpleProductToСonfigurable(String productId){
        JSONObject jsonForAddSimpleProductToСonfigurable = new JSONObject();
        jsonForAddSimpleProductToСonfigurable.put("id", productId);

        return jsonForAddSimpleProductToСonfigurable.toJSONString();
    }
}
