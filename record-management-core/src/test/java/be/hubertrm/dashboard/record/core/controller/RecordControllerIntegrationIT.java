package be.hubertrm.dashboard.record.core.controller;

import be.hubertrm.dashboard.record.core.RecordCoreBaseIntegrationTest;
import be.hubertrm.dashboard.record.core.dto.CategoryDto;
import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.dto.SourceDto;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class RecordControllerIntegrationIT extends RecordCoreBaseIntegrationTest {

    private Long id;
    private RecordDto recordDto;
    private List<RecordDto> recordDtoList;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<RecordDto> jsonRecord;

    @Autowired
    private JacksonTester<List<RecordDto>> jsonRecordList;

    @BeforeEach
    void setup() {
        id = 1L;
        recordDto = getRecordDto();
        recordDtoList = getRecordDtoList();
    }

    @Test
    void givenRecordExists_whenRequestById_shouldReturnElement() throws Exception {
        mvc.perform(get(String.format("/api/v1/record/%s", id)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(jsonRecord.write(recordDto).getJson()));
    }

    @Test
    void givenRecordDoesNotExist_whenRequestById_shouldReturnNotFoundException() throws Exception {
        mvc.perform(get(String.format("/api/v1/record/%s", 999L)))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenRecordExists_whenRequestAll_shouldAll() throws Exception {
        mvc.perform(get("/api/v1/records"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(jsonRecordList.write(recordDtoList).getJson()));
    }

    @Test
    void givenRecordDoesNotExist_whenRequestCreation_shouldCreate() throws Exception {
        RecordDto newRecordDto = getRecordDto().setId(null);

        mvc.perform(post("/api/v1/record")
            .content(jsonRecord.write(newRecordDto).getJson())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenRecordExists_whenRequestCreation_shouldUpdateElement() throws Exception {
        RecordDto newRecordDto = getRecordDto();

        mvc.perform(post("/api/v1/record")
                .content(jsonRecord.write(newRecordDto).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonRecord.write(newRecordDto).getJson()));
    }

    @Test
    void givenRecordExists_whenRequestUpdate_shouldReturnUpdatedElement() throws Exception {
        recordDto.setComments("Updated comment");

        mvc.perform(put(String.format("/api/v1/record/%s", id))
            .content(jsonRecord.write(recordDto).getJson())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(jsonRecord.write(recordDto).getJson()));
    }

    @Test
    void givenRecordDoesNotExist_whenRequestUpdate_shouldCreate() throws Exception {
        recordDto.setComments("Updated comment").setId(null);

        mvc.perform(put(String.format("/api/v1/record/%s", 999L))
                .content(jsonRecord.write(recordDto).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenRecordExists_whenDelete_shouldReturnDeletedMessage() throws Exception {
        mvc.perform(delete(String.format("/api/v1/record/%s", id)))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void givenRecordDoesNotExist_whenDelete_shouldReturnNotFoundException() throws Exception {
        mvc.perform(delete(String.format("/api/v1/record/%s", 999L)))
                .andExpect(status().isNotFound());
    }

    private RecordDto getRecordDto() {
        LocalDate localDate = LocalDate.of(2020, 10, 10);
        CategoryDto categoryDto = SampleDataService.createCategoryDto()
                .setId(1L)
                .setCreationDate(localDate)
                .setName("category_name_1");
        SourceDto sourceDto = SampleDataService.createSourceDto()
                .setId(1L)
                .setCreationDate(localDate)
                .setOrganisationId(1L)
                .setName("source_name_1");
        return SampleDataService.createRecordDto()
                .setPayDate(LocalDate.of(2020, 10, 10))
                .setAmount(10.01f)
                .setCategoryDto(categoryDto)
                .setSourceDto(sourceDto)
                .setComments("record_comments_1");
    }

    private List<RecordDto> getRecordDtoList() {
        LocalDate dateTest1 = LocalDate.of(2020, 10, 10);
        LocalDate dateTest2 = LocalDate.of(2020, 11, 12);

        CategoryDto categoryDto1 = SampleDataService.createCategoryDto()
                .setId(1L)
                .setCreationDate(dateTest1)
                .setName("category_name_1");

        CategoryDto categoryDto2 = SampleDataService.createCategoryDto()
                .setId(2L)
                .setCreationDate(dateTest2)
                .setName("category_name_2");

        SourceDto sourceDto1 = SampleDataService.createSourceDto()
                .setId(1L)
                .setCreationDate(dateTest1)
                .setOrganisationId(1L)
                .setName("source_name_1");

        SourceDto sourceDto2 = SampleDataService.createSourceDto()
                .setId(2L)
                .setCreationDate(dateTest2)
                .setOrganisationId(2L)
                .setName("source_name_2");

        return Arrays.asList(
                SampleDataService.createRecordDto(1L, dateTest1, 10.01F, categoryDto1, sourceDto1, "record_comments_1"),
                SampleDataService.createRecordDto(2L, dateTest2, 20F, categoryDto2, sourceDto2, "record_comments_2")
        );
    }
}