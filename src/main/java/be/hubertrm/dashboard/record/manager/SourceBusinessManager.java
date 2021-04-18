package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.dto.SourceDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.mapper.SourceMapper;
import be.hubertrm.dashboard.record.service.SourceService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class SourceBusinessManager {

    @Resource
    private final SourceService sourceService;

    private final SourceMapper sourceMapper = Mappers.getMapper(SourceMapper.class);

    public SourceBusinessManager(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    public List<SourceDto> getAllSources() {
        return sourceMapper.toDtoList(sourceService.getAllSources());
    }

    public SourceDto getSourceById(Long id) throws ResourceNotFoundException {
        return sourceMapper.toDto(sourceService.getSourceById(id));
    }

    public SourceDto createOrUpdate(SourceDto sourceDto) {
        return sourceMapper.toDto(sourceService.createOrUpdate(sourceMapper.toEntity(sourceDto)));
    }

    public SourceDto createOrUpdate(SourceDto sourceDto, Long id) {
        sourceDto.setId(id);
        return createOrUpdate(sourceDto);
    }
    
    public Map<String, Boolean> deleteSourceById(Long id) throws ResourceNotFoundException {
        return sourceService.deleteSourceById(id);
    }
}
