package be.hubertrm.dashboard.record.rest.mapper;

import be.hubertrm.dashboard.record.rest.dto.CategoryDto;
import be.hubertrm.dashboard.record.rest.dto.RecordDto;
import be.hubertrm.dashboard.record.rest.dto.SourceDto;
import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.model.Record;
import be.hubertrm.dashboard.record.rest.service.CategoryService;
import be.hubertrm.dashboard.record.rest.service.SourceService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * The abstract class RecordMapper provides the methods for mapping Record to RecordDto and back.
 */
@Mapper(componentModel="spring", uses= {CategoryService.class, SourceService.class})
public abstract class RecordMapper implements GenericMapper<Record, RecordDto> {

    public final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    public final SourceMapper sourceMapper = Mappers.getMapper(SourceMapper.class);

    @Autowired
    public CategoryService categoryService;
    @Autowired
    public SourceService sourceService;

    @Mapping(target = "categoryDto", expression = "java(mapCategory(entity.getCategoryId()))")
    @Mapping(target = "sourceDto", expression = "java(mapSource(entity.getSourceId()))")
    public abstract RecordDto toDto(Record entity) throws ResourceNotFoundException;

    @Mapping(source = "categoryDto.id", target = "categoryId")
    @Mapping(source = "sourceDto.id", target = "sourceId")
    public abstract Record toEntity(RecordDto dto);

    @Mapping(source = "categoryDto.id", target = "categoryId")
    @Mapping(source = "sourceDto.id", target = "sourceId")
    public abstract List<Record> toEntityList(Collection<RecordDto> dtoList);

    protected CategoryDto mapCategory(Long categoryId) throws ResourceNotFoundException {
        return categoryMapper.toDto(categoryService.getCategoryById(categoryId));
    }

    protected SourceDto mapSource(Long sourceId) throws ResourceNotFoundException {
        return sourceMapper.toDto(sourceService.getSourceById(sourceId));
    }
}
