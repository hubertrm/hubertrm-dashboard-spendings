package be.hubertrm.dashboard.record.controller;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Category;
import be.hubertrm.dashboard.record.service.CategoryService;
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

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(CategoryController.class)
class CategoryControllerMockMvcWithContextTest {

    private Long id;
    private Category category;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private JacksonTester<Category> jsonCategory;

    @Autowired
    private JacksonTester<List<Category>> jsonCategoryList;

    @BeforeEach
    void setup() {
        id = 1L;
        category = new Category(id, "test", new Timestamp(1L));
    }

    @Test
    void givenCategoryExists_whenRequestById_shouldReturnElement() throws Exception {
        // given
        given(categoryService.getCategoryById(id)).willReturn(category);

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/categories/%s".formatted(id)).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonCategory.write(category).getJson());
    }

    @Test
    void givenCategoryDoesNotExist_whenRequestById_shouldReturnNotFoundException() throws Exception {
        // given
        given(categoryService.getCategoryById(id)).willThrow(ResourceNotFoundException.class);

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/categories/%s".formatted(id)).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotInstanceOf(Category.class);
    }

    @Test
    void givenCategoryExists_whenRequestAll_shouldReturnSingletonList() throws Exception {
        // given
        given(categoryService.getAllCategories()).willReturn(Collections.singletonList(category));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/categories").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonCategoryList.write(Collections.singletonList(category)).getJson());
    }

    @Test
    void givenMultipleCategoriesExist_whenRequestAll_shouldReturnList() throws Exception {
        Category category_2 = new Category(2L, "test", new Timestamp(1L));
        // given
        given(categoryService.getAllCategories()).willReturn(Arrays.asList(category, category_2));

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/categories").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonCategoryList.write(Arrays.asList(category, category_2)).getJson());
    }

    @Test
    void givenNoCategoryExists_whenRequestAll_shouldReturnEmptyList() throws Exception {
        // given
        given(categoryService.getAllCategories()).willReturn(Collections.emptyList());

        // when
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/categories").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonCategoryList.write(Collections.emptyList()).getJson());
    }
}