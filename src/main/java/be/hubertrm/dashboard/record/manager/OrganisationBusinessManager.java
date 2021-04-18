package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.dto.OrganisationDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.mapper.OrganisationMapper;
import be.hubertrm.dashboard.record.service.OrganisationService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class OrganisationBusinessManager {

    @Resource
    private final OrganisationService organisationService;

    private final OrganisationMapper organisationMapper = Mappers.getMapper(OrganisationMapper.class);

    public OrganisationBusinessManager(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    public List<OrganisationDto> getAllOrganisations() {
        return organisationMapper.toDtoList(organisationService.getAllOrganisations());
    }

    public OrganisationDto getOrganisationById(Long id) throws ResourceNotFoundException {
        return organisationMapper.toDto(organisationService.getOrganisationById(id));
    }

    public OrganisationDto createOrUpdate(OrganisationDto organisationDto) {
        return organisationMapper.toDto(organisationService.createOrUpdate(organisationMapper.toEntity(organisationDto)));
    }

    public OrganisationDto createOrUpdate(OrganisationDto organisationDto, Long id) {
        organisationDto.setId(id);
        return createOrUpdate(organisationDto);
    }
    
    public Map<String, Boolean> deleteOrganisationById(Long id) throws ResourceNotFoundException {
        return organisationService.deleteOrganisationById(id);
    }
}
