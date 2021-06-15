package be.hubertrm.dashboard.record.files.enums;

public enum DatePattern {
    DEFAULT_DATE_PATTERN("default-date-pattern");

    private final String value;

    DatePattern(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
