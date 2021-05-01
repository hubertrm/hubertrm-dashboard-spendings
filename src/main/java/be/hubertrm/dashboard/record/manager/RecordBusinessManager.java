package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.dto.RecordDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.mapper.RecordMapper;
import be.hubertrm.dashboard.record.service.RecordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class RecordBusinessManager {

    @Resource
    private final RecordService recordService;

    @Resource
    private final RecordMapper recordMapper;

    public RecordBusinessManager(RecordService recordService, RecordMapper recordMapper) {
        this.recordService = recordService;
        this.recordMapper = recordMapper;
    }

    public List<RecordDto> getAllRecords() {
        return recordMapper.toDtoList(recordService.getAllRecords());
    }

    public RecordDto getRecordById(Long id) throws ResourceNotFoundException {
        return recordMapper.toDto(recordService.getRecordById(id));
    }

    public RecordDto createOrUpdate(RecordDto recordDto) throws ResourceNotFoundException {
        return recordMapper.toDto(recordService.createOrUpdate(recordMapper.toEntity(recordDto)));
    }

    public RecordDto createOrUpdate(RecordDto recordDto, Long id) throws ResourceNotFoundException {
        recordDto.setId(id);
        return createOrUpdate(recordDto);
    }

    public Map<String, Boolean> deleteRecordById(Long id) throws ResourceNotFoundException {
        return recordService.deleteRecordById(id);
    }
}
