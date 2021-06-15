package be.hubertrm.dashboard.record.files.enums;

public enum FileType {
    CSV("CSV_FILE_TYPE"),
    JSON("JSON_FILE_TYPE");

    private final String fileTypeId;

    FileType(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getFileTypeId() {
        return fileTypeId;
    }
}
