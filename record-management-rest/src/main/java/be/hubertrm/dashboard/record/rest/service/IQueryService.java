package be.hubertrm.dashboard.record.rest.service;

import be.hubertrm.dashboard.record.rest.model.Record;

import java.util.List;

public interface IQueryService {

    List<Record> getAllRecords();
}
