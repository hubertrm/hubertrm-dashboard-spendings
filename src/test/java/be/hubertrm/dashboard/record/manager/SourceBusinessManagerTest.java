package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.dto.SourceDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.sample.SampleDataService;
import be.hubertrm.dashboard.record.spring.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SourceBusinessManagerTest extends TestConfig {

    @Autowired
    private SourceBusinessManager sourceBusinessManager;

    @Test
    void givenElementsExistInDB_whenGetAll_shouldReturnAll() {
        List<SourceDto> expectedSources = Arrays.asList(
                SampleDataService.createSourceDto(1L, "source_name_1", LocalDate.of(2020, 10, 10), 1L),
                SampleDataService.createSourceDto(2L, "source_name_2", LocalDate.of(2020, 11, 12), 2L)
        );

        assertThat(sourceBusinessManager.getAllSources()).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedSources);
    }

    @Test
    void givenElementExistsInDb_whenGetById_shouldReturnElement() throws ResourceNotFoundException {
        SourceDto actualSourceDto = SampleDataService
                .createSourceDto(1L, "source_name_1", LocalDate.of(2020, 10, 10), 1L);

        SourceDto expectedSourceDto = sourceBusinessManager.getSourceById(1L);

        assertThat(expectedSourceDto).isEqualToComparingFieldByField(actualSourceDto);
    }

    @Test
    void givenElementDoesNotExistInDb_whenGetById_shouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> sourceBusinessManager.getSourceById(-1L));
    }

    @Test
    void givenElementExistsInDb_whenGetByName_shouldReturnElement() throws ResourceNotFoundException {
        SourceDto actualSourceDto = SampleDataService
                .createSourceDto(1L, "source_name_1", LocalDate.of(2020, 10, 10), 1L);

        SourceDto expectedSourceDto = sourceBusinessManager.getSourceByName("source_name_1");

        assertThat(expectedSourceDto).isEqualToComparingFieldByField(actualSourceDto);
    }

    @Test
    void givenElementDoesNotExistInDb_whenGetByName_shouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> sourceBusinessManager.getSourceByName("source_non-existing-name_1"));
    }

    @Test
    void givenOtherElementsExistInDb_whenCreateNew_shouldCreateWithNewId() throws ResourceNotFoundException {
        SourceDto expectedSourceDto = SampleDataService
                .createSourceDto(3L, "source_name_new", LocalDate.of(2020, 10, 10), 1L);
        SourceDto newSourceDto = SampleDataService
                .createSourceDto(null, "source_name_new", LocalDate.of(2020, 10, 10), 1L);

        SourceDto actualSourceDto = sourceBusinessManager.createOrUpdate(newSourceDto);

        assertThat(expectedSourceDto).isEqualTo(actualSourceDto);
    }

    @Test
    void givenOtherElementsExistInDb_whenCreateNew_shouldBeFindable() throws ResourceNotFoundException {
        Long expectedId = 4L;
        SourceDto expectedSourceDto = SampleDataService
                .createSourceDto(expectedId, "source_name_new", LocalDate.of(2020, 10, 10), 1L);
        SourceDto newSourceDto = SampleDataService
                .createSourceDto(null, "source_name_new", LocalDate.of(2020, 10, 10), 1L);

        assertThrows(ResourceNotFoundException.class, () -> sourceBusinessManager.getSourceById(expectedId));
        sourceBusinessManager.createOrUpdate(newSourceDto);

        assertThat(sourceBusinessManager.getSourceById(expectedId)).isEqualTo(expectedSourceDto);
    }

    @Test
    void givenElementDoesNotExistInDb_whenUpdate_shouldThrowException() {
        SourceDto toBeUpdatedSourceDto = SampleDataService
                .createSourceDto(999L, "new_source_name_1", LocalDate.of(2020, 10, 10), 2L);
        assertThrows(ResourceNotFoundException.class, () -> sourceBusinessManager.createOrUpdate(toBeUpdatedSourceDto, 999L));
    }

    @Test
    void givenOtherElementsExistsInDb_whenUpdateOld_shouldReturnUpdated() throws ResourceNotFoundException {
        SourceDto expectedSourceDto = SampleDataService
                .createSourceDto(1L, "new_source_name_1", LocalDate.of(2020, 10, 10), 2L);

        SourceDto actualSourceDto = sourceBusinessManager.createOrUpdate(expectedSourceDto, 1L);

        assertThat(expectedSourceDto).isEqualTo(actualSourceDto);
    }

    @Test
    void givenOtherElementsExistsInDb_whenUpdateOld_shouldBeFindable() throws ResourceNotFoundException {
        Long expectedId = 1L;
        SourceDto oldSourceDto = SampleDataService
                .createSourceDto(expectedId, "source_name_1", LocalDate.of(2020, 10, 10), 1L);
        SourceDto newSourceDto = SampleDataService
                .createSourceDto(expectedId, "new_source_name_1", LocalDate.of(2020, 10, 10), 2L);

        assertThat(sourceBusinessManager.getSourceById(expectedId)).isEqualTo(oldSourceDto);
        sourceBusinessManager.createOrUpdate(newSourceDto, 1L);

        assertThat(sourceBusinessManager.getSourceById(expectedId)).isEqualTo(newSourceDto);
    }

    @Test
    void givenElementExistsInDb_whenDeleting_shouldNotThrow() {
        SourceDto expectedSourceDto = SampleDataService
                .createSourceDto(1L, "source_name_1", LocalDate.of(2020, 10, 10), 1L);

        assertDoesNotThrow(() -> sourceBusinessManager.deleteSourceById(expectedSourceDto.getId()));
    }

    @Test
    void givenElementExistsInDb_whenDeleting_shouldNotBeFindable() throws ResourceNotFoundException {
        SourceDto expectedSourceDto = SampleDataService
                .createSourceDto(1L, "source_name_1", LocalDate.of(2020, 10, 10), 1L);

        sourceBusinessManager.deleteSourceById(expectedSourceDto.getId());
        assertThrows(ResourceNotFoundException.class, () -> sourceBusinessManager.deleteSourceById(expectedSourceDto.getId()));
    }

    @Test
    void givenElementDoesNotExistInDb_whenDeleting_shouldThrow() {
        assertThrows(ResourceNotFoundException.class, () -> sourceBusinessManager.deleteSourceById(3L));
    }
}