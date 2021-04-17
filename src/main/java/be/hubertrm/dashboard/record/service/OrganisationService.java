package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Organisation;

import java.util.List;
import java.util.Map;

public interface OrganisationService {

    List<Organisation> getAllOrganisations();

    Organisation getOrganisationById(Long organisationId) throws ResourceNotFoundException;

    Organisation createOrganisation(Organisation organisation);

    Organisation updateOrganisation(Long organisationId, Organisation organisationDetails)
            throws ResourceNotFoundException;

    Map<String, Boolean> deleteOrganisation(Long organisationId) throws ResourceNotFoundException;
}
