package be.hubertrm.dashboard.record.service.impl;

import be.hubertrm.dashboard.record.model.Record;
import be.hubertrm.dashboard.record.repository.RecordRepository;
import be.hubertrm.dashboard.record.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Resource
    private RecordRepository recordRepository;

    private static final String RECORD_NOT_FOUND_MESSAGE = "Record not found for this id :: ";
    private static final String RECORD_NOT_FOUND_BY_NAME_MESSAGE = "Record not found for this name :: ";

    @Override
    public Record createOrUpdate(final Record record) {
        final Record savedRecord = recordRepository.save(record);

        LOGGER.debug("Created/Updated record with id [{}]", savedRecord.getId());
        return savedRecord;
    }

    @Override
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    @Override
    public Optional<Record> getRecordById(long id) {
        return recordRepository.findById(id);
    }

    @Override
    public void deleteRecordById(Long id) { recordRepository.deleteById(id); }
}
