package be.hubertrm.dashboard.record.rest.controller;

import be.hubertrm.dashboard.record.core.dto.OrganisationDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.manager.OrganisationBusinessManager;
import be.hubertrm.dashboard.record.core.model.Organisation;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(OrganisationController.class)
class OrganisationControllerMockMvcWithContextTest {

    private Long id;
    private OrganisationDto organisationDto;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrganisationBusinessManager organisationBusinessManager;

    @Autowired
    private JacksonTester<OrganisationDto> jsonOrganisation;

    @Autowired
    private JacksonTester<List<OrganisationDto>> jsonOrganisationList;

    @BeforeEach
    void setup() {
        id = 1L;
        organisationDto = SampleDataService.createOrganisationDto();
    }

    @Test
    void givenOrganisationExists_whenRequestById_shouldReturnElement() throws Exception {
        // given
        given(organisationBusinessManager.getOrganisationById(id)).willReturn(organisationDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/organisation/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonOrganisation.write(organisationDto).getJson());
    }

    @Test
    void givenOrganisationDoesNotExist_whenRequestById_shouldReturnNotFoundException() throws Exception {
        // given
        given(organisationBusinessManager.getOrganisationById(id)).willThrow(ResourceNotFoundException.class);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/organisations/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotInstanceOf(Organisation.class);
    }

    @Test
    void givenOrganisationExists_whenRequestAll_shouldReturnSingletonList() throws Exception {
        // given
        given(organisationBusinessManager.getAllOrganisations()).willReturn(Collections.singletonList(organisationDto));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/organisations").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonOrganisationList.write(Collections.singletonList(organisationDto)).getJson());
    }

    @Test
    void givenMultipleOrganisationsExist_whenRequestAll_shouldReturnList() throws Exception {
        OrganisationDto organisation_2 = new OrganisationDto(2L, "test", "test");
        // given
        given(organisationBusinessManager.getAllOrganisations()).willReturn(Arrays.asList(organisationDto, organisation_2));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/organisations").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonOrganisationList.write(Arrays.asList(organisationDto, organisation_2)).getJson());
    }

    @Test
    void givenNoOrganisationExists_whenRequestAll_shouldReturnEmptyList() throws Exception {
        // given
        given(organisationBusinessManager.getAllOrganisations()).willReturn(Collections.emptyList());

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/organisations").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonOrganisationList.write(Collections.emptyList()).getJson());
    }

    @Test
    void givenOrganisationDoesNotExist_whenRequestCreation_shouldReturnElement() throws Exception {
        // given
        given(organisationBusinessManager.createOrUpdate(any(OrganisationDto.class))).willReturn(organisationDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(post("/api/v1/organisation")
                        .content(jsonOrganisation.write(organisationDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonOrganisation.write(organisationDto).getJson());
    }

    @Test
    void givenOrganisationExists_whenRequestUpdate_shouldReturnUpdatedElement() throws Exception {
        // given
        given(organisationBusinessManager.createOrUpdate(any(OrganisationDto.class), anyLong())).willReturn(organisationDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(put(String.format("/api/v1/organisation/%s", id))
                        .content(jsonOrganisation.write(organisationDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonOrganisation.write(organisationDto).getJson());
    }

    @Test
    void givenOrganisationExists_whenDelete_shouldReturnDeletedMessage() throws Exception {
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted", Boolean.TRUE);
        // given
        given(organisationBusinessManager.deleteOrganisationById(id)).willReturn(result);

        // when
        MockHttpServletResponse response =
                mvc.perform(delete(String.format("/api/v1/organisation/%s", id))
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void givenOrganisationDoesNotExist_whenDelete_shouldReturnNotFoundException() throws Exception {
        // given
        given(organisationBusinessManager.getOrganisationById(id)).willThrow(ResourceNotFoundException.class);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/organisation/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotInstanceOf(Organisation.class);
    }
}