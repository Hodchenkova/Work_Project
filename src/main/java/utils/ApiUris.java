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
}
