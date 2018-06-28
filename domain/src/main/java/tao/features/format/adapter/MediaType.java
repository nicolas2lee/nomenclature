package tao.features.format.adapter;

public enum MediaType {
    APPLICATION_XML_VALUE("application/xml"),
    TEXT_PLAIN_VALUE("text/plain"),
    CSV_VALUE("text/csv"),
    APPLICATION_JSON_VALUE("application/json");

    private String value;

    MediaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
