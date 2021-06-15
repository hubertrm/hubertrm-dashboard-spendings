package be.hubertrm.dashboard.record.rest.controller;

import be.hubertrm.dashboard.record.core.dto.OrganisationDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.manager.OrganisationBusinessManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class OrganisationController {

    @Resource
    private OrganisationBusinessManager organisationBusinessManager;

    @GetMapping("/organisations")
    public List<OrganisationDto> getAllOrganisations() {
        return organisationBusinessManager.getAllOrganisations();
    }

    @GetMapping("/organisation/{id}")
    public OrganisationDto getOrganisationById(@PathVariable(value = "id") Long organisationId)
            throws ResourceNotFoundException {
        return organisationBusinessManager.getOrganisationById(organisationId);
    }

    @PostMapping("/organisation")
    public OrganisationDto createOrganisation(@RequestBody OrganisationDto organisationDto) throws ResourceNotFoundException {
        return organisationBusinessManager.createOrUpdate(organisationDto);
    }

    @PutMapping("/organisation/{id}")
    public OrganisationDto updateOrganisation(@PathVariable(value = "id") Long id,
                                              @RequestBody OrganisationDto organisationDto) throws ResourceNotFoundException {
        return organisationBusinessManager.createOrUpdate(organisationDto, id);
    }

    @DeleteMapping("/organisation/{id}")
    public Map<String, Boolean> deleteOrganisation(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        return organisationBusinessManager.deleteOrganisationById(id);
    }
}
