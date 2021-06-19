package be.hubertrm.dashboard.record.core.manager;

import be.hubertrm.dashboard.record.core.dto.CategoryDto;
import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.dto.SourceDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.TestConfig;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecordBusinessManagerTest extends TestConfig {

    @Autowired
    private RecordBusinessManager recordBusinessManager;

    private CategoryDto categoryDto1;
    private CategoryDto categoryDto2;
    private SourceDto sourceDto1;
    private SourceDto sourceDto2;

    @BeforeEach
    void setup() {
        categoryDto1 = SampleDataService.createCategoryDto(1L, "category_name_1", LocalDate.of(2020, 10, 10));
        categoryDto2 = SampleDataService.createCategoryDto(2L, "category_name_2", LocalDate.of(2020, 11, 12));
        sourceDto1 = SampleDataService.createSourceDto(1L, "source_name_1", LocalDate.of(2020, 10, 10), 1L);
        sourceDto2 = SampleDataService.createSourceDto(2L, "source_name_2", LocalDate.of(2020, 11, 12), 2L);
    }

    @Test
    void getAllRecordsTest() {
        LocalDate dateTest1 = LocalDate.of(2020, 10, 10);
        LocalDate dateTest2 = LocalDate.of(2020, 11, 12);
        List<RecordDto> expectedRecords = Arrays.asList(
                SampleDataService.createRecordDto(1L, dateTest1, 10.01F, categoryDto1, sourceDto1, "record_comments_1"),
                SampleDataService.createRecordDto(2L, dateTest2, 20F, categoryDto2, sourceDto2, "record_comments_2"));

        assertThat(recordBusinessManager.getAllRecords()).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedRecords);
    }

    @Test
    void elementExistsInDb() throws ResourceNotFoundException {
        LocalDate dateTest1 = LocalDate.of(2020, 10, 10);
        RecordDto actualRecordDto = SampleDataService.createRecordDto(1L, dateTest1, 10.01F, categoryDto1, sourceDto1, "record_comments_1");

        RecordDto expectedRecordDto = recordBusinessManager.getRecordById(1L);

        assertThat(expectedRecordDto).isEqualToComparingFieldByField(actualRecordDto);
    }

    @Test
    void elementDoesNotExistInDb() {
        assertThrows(ResourceNotFoundException.class, () -> recordBusinessManager.getRecordById(-1L));
    }

    @Test
    void createNewElement() throws ResourceNotFoundException {
        LocalDate dateTest1 = LocalDate.now();
        RecordDto recordDtoExpected = SampleDataService.createRecordDto(3L, dateTest1, 10.01F,
                categoryDto1, sourceDto1, "test");
        RecordDto recordDtoNew = SampleDataService.createRecordDto(null, dateTest1, 10.01F,
                categoryDto1, sourceDto1, "test");

        RecordDto actualRecordDto = recordBusinessManager.createOrUpdate(recordDtoNew);

        assertThat(recordDtoExpected).isEqualTo(actualRecordDto);
    }

    @Test
    void updateOldElement() throws ResourceNotFoundException {
        LocalDate dateTest1 = LocalDate.now();
        RecordDto expectedRecordDto = SampleDataService.createRecordDto(1L, dateTest1, 10.01F,
                categoryDto2, sourceDto2, "test");

        RecordDto actualRecordDto = recordBusinessManager.createOrUpdate(expectedRecordDto, 1L);

        assertThat(expectedRecordDto).isEqualTo(actualRecordDto);
    }

    @Test
    void whenDeletingExistingElement_shouldNotThrow() {
        RecordDto recordDto = SampleDataService.createRecordDto(1L, LocalDate.of(2020, 10, 10), 10.01F,
                categoryDto1, sourceDto1, "test");

        Assertions.assertDoesNotThrow(() -> recordBusinessManager.deleteRecordById(recordDto.getId()));
    }

    @Test
    void whenDeletingNonExistingElement_shouldThrow() {
        assertThrows(ResourceNotFoundException.class, () -> recordBusinessManager.deleteRecordById(3L));
    }
}