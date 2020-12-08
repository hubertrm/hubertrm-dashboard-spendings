package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Organisation;
import be.hubertrm.dashboard.record.repository.OrganisationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class OrganisationService {

    @Resource
    private OrganisationRepository organisationRepository;

    private static final String SPENDING_NOT_FOUND_MESSAGE = "Organisation not found for this id :: ";

    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public Organisation getOrganisationById(Long organisationId)
            throws ResourceNotFoundException {
        return organisationRepository.findById(organisationId).orElseThrow(
                () -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + organisationId)
        );
    }

    public Organisation createOrganisation(Organisation organisation) {
        return organisationRepository.save(organisation);
    }

    public Organisation updateOrganisation(Long organisationId, Organisation organisationDetails)
            throws ResourceNotFoundException {
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + organisationId));

        organisation.setName(organisationDetails.getName());

        return organisationRepository.save(organisation);
    }

    public Map<String, Boolean> deleteOrganisation(Long organisationId) throws ResourceNotFoundException {
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + organisationId));

        organisationRepository.delete(organisation);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
