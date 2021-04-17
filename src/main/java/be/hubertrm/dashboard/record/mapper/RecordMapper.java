package be.hubertrm.dashboard.record.mapper;

import be.hubertrm.dashboard.record.dto.CategoryDto;
import be.hubertrm.dashboard.record.dto.OrganisationDto;
import be.hubertrm.dashboard.record.dto.RecordDto;
import be.hubertrm.dashboard.record.dto.SourceDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Category;
import be.hubertrm.dashboard.record.model.Organisation;
import be.hubertrm.dashboard.record.model.Record;
import be.hubertrm.dashboard.record.model.Source;
import be.hubertrm.dashboard.record.service.OrganisationService;
import be.hubertrm.dashboard.record.service.SourceService;
import be.hubertrm.dashboard.record.service.impl.CategoryServiceImpl;
import org.mapstruct.Mapper;

import javax.annotation.Resource;

/**
 * The abstract class RecordMapper provides the methods for mapping Record to RecordDto and back.
 */
@Mapper(uses = {CategoryMapper.class, SourceMapper.class, OrganisationMapper.class})
public abstract class RecordMapper implements GenericMapper<Record, RecordDto> {

    @Resource
    private CategoryServiceImpl categoryService;
    @Resource
    private SourceService sourceService;
    @Resource
    private OrganisationService organisationService;

    protected Category mapCategory(long categoryId) throws ResourceNotFoundException {
        return categoryService.getCategoryById(categoryId);
    }

    protected Source mapSource(long sourceId) throws ResourceNotFoundException {
        return sourceService.getSourceById(sourceId);
    }

    protected Organisation mapOrganisation(long organisationId) throws ResourceNotFoundException {
        return organisationService.getOrganisationById(organisationId);
    }

    protected long mapCategoryDto(CategoryDto categoryDto) {
        return categoryDto.getId();
    }

    protected long mapSourceDto(SourceDto sourceDto) {
        return sourceDto.getId();
    }

    protected long mapOrganisationDto(OrganisationDto organisationDto) {
        return organisationDto.getId();
    }
}
