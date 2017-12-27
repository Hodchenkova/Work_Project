package JsonForModules;

import org.json.simple.JSONObject;

public class Login {


    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;





    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String parentCategory;

    public String generateJSONForCategory(){
        JSONObject name = new JSONObject();
        JSONObject parent_id = new JSONObject();

        name.put("name", "JsonForModules.Category 1");

return name.toString();

    }
    public String generateJSONForChangeCategory(){
        JSONObject newName = new JSONObject();

        newName.put("name", "test JsonForModules.Category");

        return name.toString();

    }
    public String generateJSONForProduct() {

        JSONObject productData = new JSONObject();
        JSONObject fields = new JSONObject();
        JSONObject productName = new JSONObject();
        JSONObject status = new JSONObject();
        JSONObject type = new JSONObject();
        JSONObject sku = new JSONObject();
        JSONObject description = new JSONObject();
        JSONObject volume = new JSONObject();
        JSONObject weight = new JSONObject();
        JSONObject unit = new JSONObject();


        productName.put("name", "JsonForModules.Product 1");
        status.put("status", 1);
        type.put("type", 1);
        sku.put("sku", "sku1");
        description.put("description", "zzzzzz");
        volume.put("volume", "10x1x5:m");
        weight.put("weight", "110:kg");
        unit.put("unit", 1);

        fields.put("productName", productName);
        fields.put("status", status);
        fields.put("type", type);
        fields.put("sku", sku);
        fields.put("description", description);
        fields.put("volume", volume);
        fields.put("weight", weight);
        fields.put("unit", unit);

        productData.put("fields", fields);

        return productData.toString();

    }

    }
