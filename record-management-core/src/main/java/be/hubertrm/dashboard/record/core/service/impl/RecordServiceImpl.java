package be.hubertrm.dashboard.record.core.service.impl;

import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.model.Record;
import be.hubertrm.dashboard.record.core.repository.RecordRepository;
import be.hubertrm.dashboard.record.core.service.RecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Resource
    private RecordRepository recordRepository;

    private static final String RECORD_NOT_FOUND_MESSAGE = "Record not found for this id :: ";

    @Override
    public Record createOrUpdate(final Record rec) {
        final var savedRecord = recordRepository.save(rec);

        LOGGER.debug("Created/Updated record with id [{}]", savedRecord.getId());
        return savedRecord;
    }

    @Override
    public Collection<Record> createOrUpdate(final Collection<Record> records) {
        final List<Record> savedRecords = recordRepository.saveAll(records);

        LOGGER.debug("Created/Updated records with ids [{}]", savedRecords.stream().map(Record::getId));
        return CollectionUtils.disjunction(records, savedRecords);
    }

    @Override
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    @Override
    public Record getRecordById(Long id) throws ResourceNotFoundException {
        return recordRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(RECORD_NOT_FOUND_MESSAGE + id)
        );
    }

    @Override
    public void deleteRecordById(Long id) throws ResourceNotFoundException {
        Record rec = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND_MESSAGE + id));

        recordRepository.delete(rec);
    }
}
