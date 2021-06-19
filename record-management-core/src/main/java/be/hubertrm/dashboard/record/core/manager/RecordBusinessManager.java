package be.hubertrm.dashboard.record.core.manager;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.mapper.RecordMapper;
import be.hubertrm.dashboard.record.core.service.RecordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

    public List<RecordDto> createOrUpdate(List<RecordDto> recordDto) {
        return recordMapper.toDtoList(recordService.createOrUpdate(recordMapper.toEntityList(recordDto)));
    }

    public void deleteRecordById(Long id) throws ResourceNotFoundException {
        recordService.deleteRecordById(id);
    }
}
