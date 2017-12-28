package utils;

public enum  ApiUris {

    LOGIN("/api/login"),
    CATEGORY("/api/categoty"),
    STORE("/api/warehouse"),
    PRODUCT("/api/product");


    ApiUris(String url) {

    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String uri;

    public static class Customer {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShop_id() {
            return shop_id;
        }

        public String setShop_id(String shop_id) {
            this.shop_id = shop_id;
            return shop_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        private String shop_id;
        private String phone;
    }
}
