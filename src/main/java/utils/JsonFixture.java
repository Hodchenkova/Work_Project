package utils;

import org.json.simple.JSONObject;

public class JsonFixture {



    public String jsonForLogin(){

        JSONObject jsonForLogin = new JSONObject();
        jsonForLogin.put("email", "testuser@test.test");
        jsonForLogin.put("password", "123456");
        return jsonForLogin.toJSONString();
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

    public String jsonForCreateProduct(){

        JSONObject jsonForCreateProduct = new JSONObject();
        jsonForCreateProduct.put("name", "Product 1");
        jsonForCreateProduct.put("status", 1);
        jsonForCreateProduct.put("type", 1);
        jsonForCreateProduct.put("sku", "skuuu");
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

    public String jsonForCreateCustomer(){

        JSONObject jsonForCreateCustomer = new JSONObject();
        jsonForCreateCustomer.put("name","Иванов Иван Иванович");
        jsonForCreateCustomer.put("shop_id","");
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

}
