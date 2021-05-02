package be.hubertrm.dashboard.record.rest.mapper;

import be.hubertrm.dashboard.record.rest.dto.CategoryDto;
import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.model.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.*;

class CategoryMapperTest {

    CategoryMapper mapper = Mappers.getMapper(CategoryMapper.class);

    @Test
    void nullToCategory() {
        Assertions.assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void nullToCategoryDto() throws ResourceNotFoundException {
        Assertions.assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    void emptyToCategory() {
        Category categoryExpected = new Category(null, null, null);
        Assertions.assertThat(mapper.toEntity(new CategoryDto(null, null, null)))
                .isEqualToComparingFieldByField(categoryExpected);
    }

    @Test
    void emptyToCategoryDto() throws ResourceNotFoundException {
        CategoryDto categoryDtoExpected = new CategoryDto(null, null, null);
        Assertions.assertThat(mapper.toDto(new Category()))
                .isEqualToComparingFieldByField(categoryDtoExpected);
    }

    @Test
    void categoryDtoToCategory() {
        LocalDate dateTest = LocalDate.now();
        CategoryDto categoryDto = new CategoryDto(1L, "test", dateTest);
        Category categoryExpected = new Category(1L, "test", dateTest);

        Assertions.assertThat(mapper.toEntity(categoryDto))
                .isEqualToComparingFieldByField(categoryExpected);
    }

    @Test
    void categoryToCategoryDto() throws ResourceNotFoundException {
        LocalDate dateTest = LocalDate.now();
        Category category = new Category(1L, "test", dateTest);
        CategoryDto categoryDtoExpected = new CategoryDto(1L, "test", dateTest);

        Assertions.assertThat(mapper.toDto(category))
                .isEqualToComparingFieldByField(categoryDtoExpected);
    }

    @Test
    void nullToCategoryList() {
        Assertions.assertThat(mapper.toEntityList(Collections.singletonList(null))).contains((Category) null);
    }

    @Test
    void nullToCategoryDtoList() {
        Assertions.assertThat(mapper.toDtoList(Collections.singletonList(null))).contains((CategoryDto) null);
    }

    @Test
    void emptyToCategoryList() {
        Assertions.assertThat(mapper.toEntityList(new ArrayList<>())).isEmpty();
    }

    @Test
    void emptyToCategoryDtoList() {
        Assertions.assertThat(mapper.toDtoList(new ArrayList<>())).isEmpty();
    }

    @Test
    void categoryDtoListToCategoryList() {
        LocalDate dateTest1 = LocalDate.now();
        LocalDate dateTest2 = LocalDate.now().minusDays(2);
        List<CategoryDto> categoryDtoList = Arrays.asList(new CategoryDto(1L, "test1", dateTest1),
                new CategoryDto(2L, "test2", dateTest2));
        Category categoryExpected1 = new Category(1L, "test1", dateTest1);
        Category categoryExpected2 = new Category(2L, "test2", dateTest2);

        Assertions.assertThat(mapper.toEntityList(categoryDtoList))
                .usingElementComparatorOnFields("id", "name", "creationDate")
                .containsExactlyElementsOf(Arrays.asList(categoryExpected1, categoryExpected2));
    }

    @Test
    void categoryListToCategoryDtoList() {
        LocalDate dateTest1 = LocalDate.now();
        LocalDate dateTest2 = LocalDate.now().minusDays(2);
        List<Category> categoryList = Arrays.asList(new Category(1L, "test1", dateTest1),
                new Category(2L, "test2", dateTest2));
        CategoryDto categoryDtoExpected1 = new CategoryDto(1L, "test1", dateTest1);
        CategoryDto categoryDtoExpected2 = new CategoryDto(2L, "test2", dateTest2);

        Assertions.assertThat(mapper.toDtoList(categoryList))
                .usingElementComparatorOnFields("id", "name", "creationDate")
                .containsExactlyElementsOf(Arrays.asList(categoryDtoExpected1, categoryDtoExpected2));
    }
}