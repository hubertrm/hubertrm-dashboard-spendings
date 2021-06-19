package be.hubertrm.dashboard.record.core.repository;

import be.hubertrm.dashboard.record.core.model.Organisation;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class OrganisationRepositoryTest {

    @Autowired
    private OrganisationRepository organisationRepository;

    @Test
    void testFindOrganisationByName() {
        String newOrganisationName = "organisation_name_1";
        String newOrganisationAddress = "organisation_address_1";
        assertThat(organisationRepository.findOrganisationByName(newOrganisationName))
                .isPresent().get()
                .extracting("id", "name", "address").isEqualTo(Arrays.asList(1L, newOrganisationName, newOrganisationAddress));
    }
    
    @Test
    void testFindNonExistingOrganisationByName() {
        String nonExistingOrganisation = "non_existing_organisation";
        assertThat(organisationRepository.findOrganisationByName(nonExistingOrganisation))
                .isNotPresent();
    }

    @Test
    void testFindAll() {
        List<Organisation> expectedOrganisations = Arrays.asList(
                SampleDataService.createOrganisation(1L, "organisation_name_1", "organisation_address_1"),
                SampleDataService.createOrganisation(2L, "organisation_name_2", "organisation_address_2")
        );

        assertThat(organisationRepository.findAll()).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedOrganisations);
    }

    @Test
    void testCreate() {
        Organisation organisation = SampleDataService.createOrganisation();
        organisation.setId(null);
        final Organisation savedOrganisation = organisationRepository.save(organisation);
        assertThat(savedOrganisation).isNotNull().isEqualToIgnoringGivenFields(organisation, "id");
    }

    @Test
    void testUpdate() {
        Organisation organisation = SampleDataService.createOrganisation();
        organisation.setId(1L);
        final Organisation savedOrganisation = organisationRepository.save(organisation);
        assertThat(savedOrganisation).isNotNull().isEqualToComparingFieldByField(organisation);
    }
}