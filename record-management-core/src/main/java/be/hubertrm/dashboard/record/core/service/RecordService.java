package be.hubertrm.dashboard.record.core.service;

import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.model.Record;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Transactional
public interface RecordService {

    Record createOrUpdate(final Record rec);

    Collection<Record> createOrUpdate(final Collection<Record> records);

    List<Record> getAllRecords();

    Record getRecordById(Long id) throws ResourceNotFoundException;

    void deleteRecordById(Long id) throws ResourceNotFoundException;
}
