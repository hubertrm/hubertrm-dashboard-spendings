package be.hubertrm.dashboard.record.core.controller;

import be.hubertrm.dashboard.record.core.dto.SourceDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.manager.SourceBusinessManager;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
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

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(SourceController.class)
class SourceControllerMockMvcWithContextTest {

    private Long id;
    private SourceDto sourceDto;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SourceBusinessManager sourceBusinessManager;

    @Autowired
    private JacksonTester<SourceDto> jsonSource;

    @Autowired
    private JacksonTester<List<SourceDto>> jsonSourceList;

    @BeforeEach
    void setup() {
        id = 1L;
        sourceDto = SampleDataService.createSourceDto();
    }

    @Test
    void givenSourceExists_whenRequestById_shouldReturnElement() throws Exception {
        // given
        given(sourceBusinessManager.getSourceById(id)).willReturn(sourceDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/source/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonSource.write(sourceDto).getJson());
    }

    @Test
    void givenSourceDoesNotExist_whenRequestById_shouldReturnNotFoundException() throws Exception {
        // given
        given(sourceBusinessManager.getSourceById(id)).willThrow(ResourceNotFoundException.class);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/source/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotInstanceOf(SourceDto.class);
    }

    @Test
    void givenSourceExists_whenRequestAll_shouldReturnSingletonList() throws Exception {
        // given
        given(sourceBusinessManager.getAllSources()).willReturn(Collections.singletonList(sourceDto));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/sources").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonSourceList.write(Collections.singletonList(sourceDto)).getJson());
    }

    @Test
    void givenMultipleSourcesExist_whenRequestAll_shouldReturnList() throws Exception {
        SourceDto source_2 = new SourceDto(2L, "test", LocalDate.now(), 1L);
        // given
        given(sourceBusinessManager.getAllSources()).willReturn(Arrays.asList(sourceDto, source_2));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/sources").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonSourceList.write(Arrays.asList(sourceDto, source_2)).getJson());
    }

    @Test
    void givenNoSourceExists_whenRequestAll_shouldReturnEmptyList() throws Exception {
        // given
        given(sourceBusinessManager.getAllSources()).willReturn(Collections.emptyList());

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/sources").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonSourceList.write(Collections.emptyList()).getJson());
    }

    @Test
    void givenSourceDoesNotExist_whenRequestCreation_shouldReturnElement() throws Exception {
        // given
        given(sourceBusinessManager.createOrUpdate(any(SourceDto.class))).willReturn(sourceDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(post("/api/v1/source")
                        .content(jsonSource.write(sourceDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonSource.write(sourceDto).getJson());
    }

    @Test
    void givenSourceExists_whenRequestUpdate_shouldReturnUpdatedElement() throws Exception {
        // given
        given(sourceBusinessManager.createOrUpdate(any(SourceDto.class), anyLong())).willReturn(sourceDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(put(String.format("/api/v1/source/%s", id))
                        .content(jsonSource.write(sourceDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonSource.write(sourceDto).getJson());
    }

    @Test
    void givenSourceExists_whenDelete_shouldReturnDeletedMessage() throws Exception {
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted", Boolean.TRUE);
        // given
        given(sourceBusinessManager.deleteSourceById(id)).willReturn(result);

        // when
        MockHttpServletResponse response =
                mvc.perform(delete(String.format("/api/v1/source/%s", id))
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void givenSourceDoesNotExist_whenDelete_shouldReturnNotFoundException() throws Exception {
        // given
        given(sourceBusinessManager.deleteSourceById(id)).willThrow(ResourceNotFoundException.class);

        // when
        MockHttpServletResponse response =
                mvc.perform(delete(String.format("/api/v1/source/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotInstanceOf(SourceDto.class);
    }
}