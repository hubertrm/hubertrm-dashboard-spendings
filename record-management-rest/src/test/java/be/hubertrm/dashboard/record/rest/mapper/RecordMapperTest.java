package be.hubertrm.dashboard.record.rest.mapper;

import be.hubertrm.dashboard.record.rest.dto.RecordDto;
import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.model.Record;
import be.hubertrm.dashboard.record.rest.sample.SampleDataService;
import be.hubertrm.dashboard.record.rest.service.CategoryService;
import be.hubertrm.dashboard.record.rest.service.SourceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class RecordMapperTest {

    @MockBean
    private SourceService sourceService;
    @MockBean
    private CategoryService categoryService;

    RecordMapper mapper = Mappers.getMapper(RecordMapper.class);

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(mapper, "categoryService", categoryService);
        ReflectionTestUtils.setField(mapper, "sourceService", sourceService);
    }

    @Test
    void nullToRecord() {
        Assertions.assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void nullToRecordDto() throws ResourceNotFoundException {
        Assertions.assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    void emptyToRecord() {
        Record recordExpected = new Record(null, null, 0, null, null, null);
        Assertions.assertThat(mapper.toEntity(new RecordDto(null, null, 0, null, null, null)))
                .isEqualToComparingFieldByField(recordExpected);
    }

    @Test
    void emptyToRecordDto() throws ResourceNotFoundException {
        RecordDto recordDtoExpected = new RecordDto(null, null, 0, null, null, null);
        Assertions.assertThat(mapper.toDto(new Record()))
                .isEqualToComparingFieldByField(recordDtoExpected);
    }

    @Test
    void recordDtoToRecord() {
        RecordDto recordDto = SampleDataService.createRecordDto();
        Record recordExpected = SampleDataService.createRecord();

        Assertions.assertThat(mapper.toEntity(recordDto))
                .isEqualToComparingFieldByField(recordExpected);
    }

    @Test
    void recordToRecordDto() throws ResourceNotFoundException {
        given(sourceService.getSourceById(anyLong())).willReturn(SampleDataService.createSource());
        given(categoryService.getCategoryById(anyLong())).willReturn(SampleDataService.createCategory());

        Record record = SampleDataService.createRecord();
        RecordDto recordDtoExpected = SampleDataService.createRecordDto();

        Assertions.assertThat(mapper.toDto(record))
                .isEqualToComparingFieldByField(recordDtoExpected);
    }

    @Test
    void nullToRecordList() {
        Assertions.assertThat(mapper.toEntityList(Collections.singletonList(null))).contains((Record) null);
    }

    @Test
    void nullToRecordDtoList() {
        Assertions.assertThat(mapper.toDtoList(Collections.singletonList(null))).contains((RecordDto) null);
    }

    @Test
    void emptyToRecordList() {
        Assertions.assertThat(mapper.toEntityList(new ArrayList<>())).isEmpty();
    }

    @Test
    void emptyToRecordDtoList() {
        Assertions.assertThat(mapper.toDtoList(new ArrayList<>())).isEmpty();
    }

    @Test
    void recordDtoListToRecordList() {
        LocalDate dateTest1 = LocalDate.now();
        LocalDate dateTest2 = LocalDate.now().minusDays(2);
        List<RecordDto> recordDtoList = Arrays.asList(
                SampleDataService.createRecordDto(1L, dateTest1, 1F, SampleDataService.createCategoryDto(1L),
                        SampleDataService.createSourceDto(1L), "test"),
                SampleDataService.createRecordDto(2L, dateTest2, 2F, SampleDataService.createCategoryDto(2L),
                        SampleDataService.createSourceDto(2L), "test2"));
        Record recordExpected1 = SampleDataService
                .createRecord(1L, dateTest1, 1F, 1L, 1L, "test");
        Record recordExpected2 = SampleDataService
                .createRecord(2L, dateTest2, 2F , 2L, 2L, "test2");

        Assertions.assertThat(mapper.toEntityList(recordDtoList))
                .usingFieldByFieldElementComparator()
                .containsExactlyElementsOf(Arrays.asList(recordExpected1, recordExpected2));
    }

    @Test
    void recordListToRecordDtoList() throws ResourceNotFoundException {
        given(sourceService.getSourceById(anyLong())).willReturn(SampleDataService.createSource());
        given(categoryService.getCategoryById(anyLong())).willReturn(SampleDataService.createCategory());
        LocalDate dateTest1 = LocalDate.now();
        LocalDate dateTest2 = LocalDate.now().minusDays(2);
        List<Record> recordList = Arrays.asList(
                SampleDataService.createRecord(1L, dateTest1, 1F, 1L, 1L, "test"),
                SampleDataService.createRecord(2L, dateTest2, 2F , 2L, 2L, "test2"));
        RecordDto recordDtoExpected1 = SampleDataService.createRecordDto(1L, dateTest1, 1F,
                SampleDataService.createCategoryDto(1L), SampleDataService.createSourceDto(1L), "test");
        RecordDto recordDtoExpected2 = SampleDataService.createRecordDto(2L, dateTest2, 2F,
                SampleDataService.createCategoryDto(2L), SampleDataService.createSourceDto(2L), "test2");

        Assertions.assertThat(mapper.toDtoList(recordList))
                .usingFieldByFieldElementComparator()
                .containsExactlyElementsOf(Arrays.asList(recordDtoExpected1, recordDtoExpected2));
    }
}