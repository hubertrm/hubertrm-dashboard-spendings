package be.hubertrm.dashboard.record.repository;

import be.hubertrm.dashboard.record.model.Organisation;
import be.hubertrm.dashboard.record.sample.SampleDataService;
import be.hubertrm.dashboard.record.spring.config.PersistenceTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = PersistenceTestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:scripts/sql_tests_organisation.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, statements = {"DROP TABLE organisation;"})
@Transactional
class OrganisationRepositoryTest {

    @Autowired
    private OrganisationRepository organisationRepository;

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