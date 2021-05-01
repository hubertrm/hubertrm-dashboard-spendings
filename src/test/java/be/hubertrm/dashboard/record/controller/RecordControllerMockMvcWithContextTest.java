package be.hubertrm.dashboard.record.controller;

import be.hubertrm.dashboard.record.dto.RecordDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.manager.RecordBusinessManager;
import be.hubertrm.dashboard.record.sample.SampleDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(RecordController.class)
class RecordControllerMockMvcWithContextTest {

    private Long id;
    private RecordDto recordDto;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RecordBusinessManager recordBusinessManager;

    @Autowired
    private JacksonTester<RecordDto> jsonRecord;

    @Autowired
    private JacksonTester<List<RecordDto>> jsonRecordList;

    @BeforeEach
    void setup() {
        id = 1L;
        recordDto = SampleDataService.createRecordDto();
    }

    @Test
    void givenRecordExists_whenRequestById_shouldReturnElement() throws Exception {
        // given
        given(recordBusinessManager.getRecordById(id)).willReturn(recordDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/record/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonRecord.write(recordDto).getJson());
    }

    @Test
    void givenRecordDoesNotExist_whenRequestById_shouldReturnNotFoundException() throws Exception {
        // given
        given(recordBusinessManager.getRecordById(id)).willThrow(ResourceNotFoundException.class);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/record/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotInstanceOf(RecordDto.class);
    }

    @Test
    void givenRecordExists_whenRequestAll_shouldReturnSingletonList() throws Exception {
        // given
        given(recordBusinessManager.getAllRecords()).willReturn(Collections.singletonList(recordDto));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/records").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonRecordList.write(Collections.singletonList(recordDto)).getJson());
    }

    @Test
    void givenMultipleRecordsExist_whenRequestAll_shouldReturnList() throws Exception {
        RecordDto record_2 = SampleDataService.createRecordDto();
        // given
        given(recordBusinessManager.getAllRecords()).willReturn(Arrays.asList(recordDto, record_2));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/records").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonRecordList.write(Arrays.asList(recordDto, record_2)).getJson());
    }

    @Test
    void givenNoRecordExists_whenRequestAll_shouldReturnEmptyList() throws Exception {
        // given
        given(recordBusinessManager.getAllRecords()).willReturn(Collections.emptyList());

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/records").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonRecordList.write(Collections.emptyList()).getJson());
    }

    @Test
    void givenRecordDoesNotExist_whenRequestCreation_shouldReturnElement() throws Exception {
        // given
        given(recordBusinessManager.createOrUpdate(any(RecordDto.class))).willReturn(recordDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(post("/api/v1/record")
                        .content(jsonRecord.write(recordDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonRecord.write(recordDto).getJson());
    }

    @Test
    void givenRecordExists_whenRequestUpdate_shouldReturnUpdatedElement() throws Exception {
        // given
        given(recordBusinessManager.createOrUpdate(any(RecordDto.class), anyLong())).willReturn(recordDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(put(String.format("/api/v1/record/%s", id))
                        .content(jsonRecord.write(recordDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonRecord.write(recordDto).getJson());
    }

    @Test
    void givenRecordExists_whenDelete_shouldReturnDeletedMessage() throws Exception {
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted", Boolean.TRUE);
        // given
        given(recordBusinessManager.deleteRecordById(id)).willReturn(result);

        // when
        MockHttpServletResponse response =
                mvc.perform(delete(String.format("/api/v1/record/%s", id))
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void givenRecordDoesNotExist_whenDelete_shouldReturnNotFoundException() throws Exception {
        // given
        given(recordBusinessManager.deleteRecordById(id)).willThrow(ResourceNotFoundException.class);

        // when
        MockHttpServletResponse response =
                mvc.perform(delete(String.format("/api/v1/record/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotInstanceOf(RecordDto.class);
    }
}