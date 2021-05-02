package be.hubertrm.dashboard.record.rest.service.impl;

import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.model.Record;
import be.hubertrm.dashboard.record.rest.repository.RecordRepository;
import be.hubertrm.dashboard.record.rest.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordServiceImpl implements RecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Resource
    private RecordRepository recordRepository;

    private static final String RECORD_NOT_FOUND_MESSAGE = "Record not found for this id :: ";

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
    public Record getRecordById(Long id) throws ResourceNotFoundException {
        return recordRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(RECORD_NOT_FOUND_MESSAGE + id)
        );
    }

    @Override
    public Map<String, Boolean> deleteRecordById(Long id) throws ResourceNotFoundException {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND_MESSAGE + id));

        recordRepository.delete(record);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
