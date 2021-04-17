package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.model.Record;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface RecordService {

    Record createOrUpdate(final Record record);

    List<Record> getAllRecords();

    Optional<Record> getRecordById(long id);

    void deleteRecordById(Long id);
}
