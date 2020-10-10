package be.hubertrm.dashboard.spending.controller;

import be.hubertrm.dashboard.spending.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.spending.model.Organisation;
import be.hubertrm.dashboard.spending.service.OrganisationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class OrganisationController {

    @Resource
    private OrganisationService organisationService;

    @GetMapping("/organisations")
    public List<Organisation> getAllOrganisations() {
        return organisationService.getAllOrganisations();
    }

    @GetMapping("/organisations/{id}")
    public ResponseEntity<Organisation> getOrganisationById(@PathVariable(value = "id") Long organisationId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(organisationService.getOrganisationById(organisationId));
    }

    @PostMapping("/organisations")
    public Organisation createOrganisation(@RequestBody Organisation organisation) {
        return organisationService.createOrganisation(organisation);
    }

    @PutMapping("/organisations/{id}")
    public ResponseEntity<Organisation> updateOrganisation(@PathVariable(value = "id") Long organisationId,
        @RequestBody Organisation organisationDetails) throws ResourceNotFoundException {
        return ResponseEntity.ok(organisationService.updateOrganisation(organisationId, organisationDetails));
    }

    @DeleteMapping("/organisations/{id}")
    public Map<String, Boolean> deleteOrganisation(@PathVariable(value = "id") Long organisationId)
        throws ResourceNotFoundException {
        return organisationService.deleteOrganisation(organisationId);
    }
}
