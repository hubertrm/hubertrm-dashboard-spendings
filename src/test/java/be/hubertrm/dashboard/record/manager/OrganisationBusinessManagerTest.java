package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.dto.OrganisationDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.sample.SampleDataService;
import be.hubertrm.dashboard.record.spring.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrganisationBusinessManagerTest extends TestConfig {

    @Autowired
    private OrganisationBusinessManager organisationBusinessManager;

    @Test
    void givenElementsExistInDB_whenGetAll_shouldReturnAll() {
        List<OrganisationDto> expectedOrganisations = Arrays.asList(
                SampleDataService.createOrganisationDto(1L, "organisation_name_1", "organisation_address_1"),
                SampleDataService.createOrganisationDto(2L, "organisation_name_2", "organisation_address_2")
        );

        assertThat(organisationBusinessManager.getAllOrganisations()).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedOrganisations);
    }

    @Test
    void givenElementExistsInDb_whenGetById_shouldReturnElement() throws ResourceNotFoundException {
        OrganisationDto actualOrganisationDto = SampleDataService
                .createOrganisationDto(1L, "organisation_name_1", "organisation_address_1");

        OrganisationDto expectedOrganisationDto = organisationBusinessManager.getOrganisationById(1L);

        assertThat(expectedOrganisationDto).isEqualToComparingFieldByField(actualOrganisationDto);
    }

    @Test
    void givenElementDoesNotExistInDb_whenGetById_shouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> organisationBusinessManager.getOrganisationById(-1L));
    }

    @Test
    void givenOtherElementsExistInDb_whenCreateNew_shouldCreateWithNewId() throws ResourceNotFoundException {
        OrganisationDto expectedOrganisationDto = SampleDataService
                .createOrganisationDto(3L, "organisation_name_new", "organisation_address_new");
        OrganisationDto newOrganisationDto = SampleDataService
                .createOrganisationDto(null, "organisation_name_new", "organisation_address_new");

        OrganisationDto actualOrganisationDto = organisationBusinessManager.createOrUpdate(newOrganisationDto);

        assertThat(expectedOrganisationDto).isEqualTo(actualOrganisationDto);
    }

    @Test
    void givenOtherElementsExistInDb_whenCreateNew_shouldBeFindable() throws ResourceNotFoundException {
        OrganisationDto expectedOrganisationDto = SampleDataService
                .createOrganisationDto(4L, "organisation_name_new", "organisation_address_new");
        OrganisationDto newOrganisationDto = SampleDataService
                .createOrganisationDto(null, "organisation_name_new", "organisation_address_new");

        assertThrows(ResourceNotFoundException.class, () -> organisationBusinessManager.getOrganisationById(4L));
        organisationBusinessManager.createOrUpdate(newOrganisationDto);

        assertThat(organisationBusinessManager.getOrganisationById(4L)).isEqualTo(expectedOrganisationDto);
    }

    @Test
    void givenElementDoesNotExistInDb_whenUpdate_shouldThrowException() {
        OrganisationDto toBeUpdatedOrganisationDto = SampleDataService
                .createOrganisationDto(999L, "organisation_name_new", "organisation_address_new");
        assertThrows(ResourceNotFoundException.class, () -> organisationBusinessManager.createOrUpdate(toBeUpdatedOrganisationDto, 999L));
    }

    @Test
    void givenOtherElementsExistsInDb_whenUpdateOld_shouldReturnUpdated() throws ResourceNotFoundException {
        OrganisationDto expectedOrganisationDto = SampleDataService
                .createOrganisationDto(1L, "new_organisation_name_1", "organisation_address_1");

        OrganisationDto actualOrganisationDto = organisationBusinessManager.createOrUpdate(expectedOrganisationDto, 1L);

        assertThat(expectedOrganisationDto).isEqualTo(actualOrganisationDto);
    }

    @Test
    void givenOtherElementsExistsInDb_whenUpdateOld_shouldBeFindable() throws ResourceNotFoundException {
        Long expectedId = 1L;
        OrganisationDto oldOrganisationDto = SampleDataService
                .createOrganisationDto(1L, "organisation_name_1", "organisation_address_1");
        OrganisationDto newOrganisationDto = SampleDataService
                .createOrganisationDto(1L, "new_organisation_name_1", "organisation_address_1");

        assertThat(organisationBusinessManager.getOrganisationById(expectedId)).isEqualTo(oldOrganisationDto);
        organisationBusinessManager.createOrUpdate(newOrganisationDto, 1L);

        assertThat(organisationBusinessManager.getOrganisationById(expectedId)).isEqualTo(newOrganisationDto);
    }

    @Test
    void givenElementExistsInDb_whenDeleting_shouldNotThrow() {
        OrganisationDto expectedOrganisationDto = SampleDataService
                .createOrganisationDto(1L, "organisation_name_1", "organisation_address_1");

        assertDoesNotThrow(() -> organisationBusinessManager.deleteOrganisationById(expectedOrganisationDto.getId()));
    }

    @Test
    void givenElementExistsInDb_whenDeleting_shouldNotBeFindable() throws ResourceNotFoundException {
        OrganisationDto expectedOrganisationDto = SampleDataService
                .createOrganisationDto(1L, "organisation_name_1", "organisation_address_1");

        organisationBusinessManager.deleteOrganisationById(expectedOrganisationDto.getId());
        assertThrows(ResourceNotFoundException.class, () -> organisationBusinessManager.deleteOrganisationById(expectedOrganisationDto.getId()));
    }

    @Test
    void givenElementDoesNotExistInDb_whenDeleting_shouldThrow() {
        assertThrows(ResourceNotFoundException.class, () -> organisationBusinessManager.deleteOrganisationById(3L));
    }
}