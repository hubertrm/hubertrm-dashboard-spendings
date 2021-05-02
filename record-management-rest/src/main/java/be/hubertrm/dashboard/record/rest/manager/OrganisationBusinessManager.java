package be.hubertrm.dashboard.record.rest.manager;

import be.hubertrm.dashboard.record.rest.dto.OrganisationDto;
import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.mapper.OrganisationMapper;
import be.hubertrm.dashboard.record.rest.service.OrganisationService;
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

    public OrganisationDto createOrUpdate(OrganisationDto organisationDto) throws ResourceNotFoundException {
        return organisationMapper.toDto(organisationService.createOrUpdate(organisationMapper.toEntity(organisationDto)));
    }

    public OrganisationDto createOrUpdate(OrganisationDto organisationDto, Long id) throws ResourceNotFoundException {
        return organisationMapper.toDto(organisationService.updateOrganisation(id, organisationMapper.toEntity(organisationDto)));
    }
    
    public Map<String, Boolean> deleteOrganisationById(Long id) throws ResourceNotFoundException {
        return organisationService.deleteOrganisationById(id);
    }
}
