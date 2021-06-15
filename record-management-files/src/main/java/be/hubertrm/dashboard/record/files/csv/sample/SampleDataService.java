package be.hubertrm.dashboard.record.files.csv.sample;

import be.hubertrm.dashboard.record.files.csv.model.Record;

public class SampleDataService {

    private SampleDataService() {}

    public static Record createRecord(String line) {
        return new Record(line);
    }
}