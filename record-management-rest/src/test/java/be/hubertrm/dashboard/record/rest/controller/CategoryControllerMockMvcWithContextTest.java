package be.hubertrm.dashboard.record.rest.controller;

import be.hubertrm.dashboard.record.rest.dto.CategoryDto;
import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.manager.CategoryBusinessManager;
import be.hubertrm.dashboard.record.rest.model.Category;
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
@WebMvcTest(CategoryController.class)
class CategoryControllerMockMvcWithContextTest {

    private Long id;
    private CategoryDto categoryDto;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryBusinessManager categoryBusinessManager;

    @Autowired
    private JacksonTester<CategoryDto> jsonCategory;

    @Autowired
    private JacksonTester<List<CategoryDto>> jsonCategoryList;

    @BeforeEach
    void setup() {
        id = 1L;
        categoryDto = new CategoryDto(id, "test", LocalDate.now());
    }

    @Test
    void givenCategoryExists_whenRequestById_shouldReturnElement() throws Exception {
        // given
        given(categoryBusinessManager.getCategoryById(id)).willReturn(categoryDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/category/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonCategory.write(categoryDto).getJson());
    }

    @Test
    void givenCategoryDoesNotExist_whenRequestById_shouldReturnNotFoundException() throws Exception {
        // given
        given(categoryBusinessManager.getCategoryById(id)).willThrow(ResourceNotFoundException.class);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/categories/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotInstanceOf(Category.class);
    }

    @Test
    void givenCategoryExists_whenRequestAll_shouldReturnSingletonList() throws Exception {
        // given
        given(categoryBusinessManager.getAllCategories()).willReturn(Collections.singletonList(categoryDto));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/categories").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonCategoryList.write(Collections.singletonList(categoryDto)).getJson());
    }

    @Test
    void givenMultipleCategoriesExist_whenRequestAll_shouldReturnList() throws Exception {
        CategoryDto category_2 = new CategoryDto(2L, "test", LocalDate.now());
        // given
        given(categoryBusinessManager.getAllCategories()).willReturn(Arrays.asList(categoryDto, category_2));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/categories").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonCategoryList.write(Arrays.asList(categoryDto, category_2)).getJson());
    }

    @Test
    void givenNoCategoryExists_whenRequestAll_shouldReturnEmptyList() throws Exception {
        // given
        given(categoryBusinessManager.getAllCategories()).willReturn(Collections.emptyList());

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/categories").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonCategoryList.write(Collections.emptyList()).getJson());
    }

    @Test
    void givenCategoryDoesNotExist_whenRequestCreation_shouldReturnElement() throws Exception {
        // given
        given(categoryBusinessManager.createOrUpdate(any(CategoryDto.class))).willReturn(categoryDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(post("/api/v1/category")
                        .content(jsonCategory.write(categoryDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonCategory.write(categoryDto).getJson());
    }

    @Test
    void givenCategoryExists_whenRequestUpdate_shouldReturnUpdatedElement() throws Exception {
        // given
        given(categoryBusinessManager.createOrUpdate(any(CategoryDto.class), anyLong())).willReturn(categoryDto);

        // when
        MockHttpServletResponse response =
                mvc.perform(put(String.format("/api/v1/category/%s", id))
                        .content(jsonCategory.write(categoryDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonCategory.write(categoryDto).getJson());
    }

    @Test
    void givenCategoryExists_whenDelete_shouldReturnDeletedMessage() throws Exception {
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted", Boolean.TRUE);
        // given
        given(categoryBusinessManager.deleteCategoryById(id)).willReturn(result);

        // when
        MockHttpServletResponse response =
                mvc.perform(delete(String.format("/api/v1/category/%s", id))
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void givenCategoryDoesNotExist_whenDelete_shouldReturnNotFoundException() throws Exception {
        // given
        given(categoryBusinessManager.getCategoryById(id)).willThrow(ResourceNotFoundException.class);

        // when
        MockHttpServletResponse response =
                mvc.perform(get(String.format("/api/v1/category/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotInstanceOf(Category.class);
    }
}