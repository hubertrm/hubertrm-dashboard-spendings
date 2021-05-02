package be.hubertrm.dashboard.record.rest.service;

import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.model.Record;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
public interface RecordService {

    Record createOrUpdate(final Record record);

    List<Record> getAllRecords();

    Record getRecordById(Long id) throws ResourceNotFoundException;

    Map<String, Boolean> deleteRecordById(Long id) throws ResourceNotFoundException;
}
