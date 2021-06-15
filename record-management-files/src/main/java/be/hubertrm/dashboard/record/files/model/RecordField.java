package be.hubertrm.dashboard.record.files.model;

public enum RecordField {
    DATE("date"),
    PRICE("price"),
    CATEGORY("category"),
    SOURCE("source"),
    NOTE("note"),
    NOT_SUPPORTED("notSupported");

    private final String value;

    RecordField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
