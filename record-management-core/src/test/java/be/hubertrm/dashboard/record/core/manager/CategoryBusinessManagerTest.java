package be.hubertrm.dashboard.record.core.manager;

import be.hubertrm.dashboard.record.core.dto.CategoryDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import be.hubertrm.dashboard.record.core.spring.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryBusinessManagerTest extends TestConfig {

    @Autowired
    private CategoryBusinessManager categoryBusinessManager;

    @Test
    void givenElementsExistInDB_whenGetAll_shouldReturnAll() {
        List<CategoryDto> expectedCategories = Arrays.asList(
                SampleDataService.createCategoryDto(1L, "category_name_1", LocalDate.of(2020, 10, 10)),
                SampleDataService.createCategoryDto(2L, "category_name_2", LocalDate.of(2020, 11, 12))
        );

        assertThat(categoryBusinessManager.getAllCategories()).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedCategories);
    }

    @Test
    void givenElementExistsInDb_whenGetById_shouldReturnElement() throws ResourceNotFoundException {
        CategoryDto actualCategoryDto = SampleDataService
                .createCategoryDto(1L, "category_name_1", LocalDate.of(2020, 10, 10));

        CategoryDto expectedCategoryDto = categoryBusinessManager.getCategoryById(1L);

        assertThat(expectedCategoryDto).isEqualToComparingFieldByField(actualCategoryDto);
    }

    @Test
    void givenElementDoesNotExistInDb_whenGetById_shouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> categoryBusinessManager.getCategoryById(-1L));
    }

    @Test
    void givenElementExistsInDb_whenGetByName_shouldReturnElement() throws ResourceNotFoundException {
        CategoryDto actualCategoryDto = SampleDataService
                .createCategoryDto(1L, "category_name_1", LocalDate.of(2020, 10, 10));

        CategoryDto expectedCategoryDto = categoryBusinessManager.getCategoryByName("category_name_1");

        assertThat(expectedCategoryDto).isEqualToComparingFieldByField(actualCategoryDto);
    }

    @Test
    void givenElementDoesNotExistInDb_whenGetByName_shouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> categoryBusinessManager.getCategoryByName("category_non-existing-name_1"));
    }

    @Test
    void givenOtherElementsExistInDb_whenCreateNew_shouldCreateWithNewId() throws ResourceNotFoundException {
        CategoryDto expectedCategoryDto = SampleDataService
                .createCategoryDto(3L, "category_name_new", LocalDate.of(2020, 10, 10));
        CategoryDto newCategoryDto = SampleDataService
                .createCategoryDto(null, "category_name_new", LocalDate.of(2020, 10, 10));

        CategoryDto actualCategoryDto = categoryBusinessManager.createOrUpdate(newCategoryDto);

        assertThat(expectedCategoryDto).isEqualTo(actualCategoryDto);
    }

    @Test
    void givenOtherElementsExistInDb_whenCreateNew_shouldBeFindable() throws ResourceNotFoundException {
        Long expectedId = 4L;
        CategoryDto expectedCategoryDto = SampleDataService
                .createCategoryDto(expectedId, "category_name_new", LocalDate.of(2020, 10, 10));
        CategoryDto newCategoryDto = SampleDataService
                .createCategoryDto(null, "category_name_new", LocalDate.of(2020, 10, 10));

        assertThrows(ResourceNotFoundException.class, () -> categoryBusinessManager.getCategoryById(expectedId));
        categoryBusinessManager.createOrUpdate(newCategoryDto);

        assertThat(categoryBusinessManager.getCategoryById(expectedId)).isEqualTo(expectedCategoryDto);
    }

    @Test
    void givenElementDoesNotExistInDb_whenUpdate_shouldThrowException() {
        CategoryDto toBeUpdatedCategoryDto = SampleDataService
                .createCategoryDto(999L, "new_category_name_1", LocalDate.of(2020, 10, 10));
        assertThrows(ResourceNotFoundException.class, () -> categoryBusinessManager.createOrUpdate(toBeUpdatedCategoryDto, 999L));
    }

    @Test
    void givenOtherElementsExistsInDb_whenUpdateOld_shouldReturnUpdated() throws ResourceNotFoundException {
        CategoryDto expectedCategoryDto = SampleDataService
                .createCategoryDto(1L, "new_category_name_1", LocalDate.of(2020, 10, 10));

        CategoryDto actualCategoryDto = categoryBusinessManager.createOrUpdate(expectedCategoryDto, 1L);

        assertThat(expectedCategoryDto).isEqualTo(actualCategoryDto);
    }

    @Test
    void givenOtherElementsExistsInDb_whenUpdateOld_shouldBeFindable() throws ResourceNotFoundException {
        Long expectedId = 1L;
        CategoryDto oldCategoryDto = SampleDataService
                .createCategoryDto(1L, "category_name_1", LocalDate.of(2020, 10, 10));
        CategoryDto newCategoryDto = SampleDataService
                .createCategoryDto(1L, "new_category_name_1", LocalDate.of(2020, 10, 10));

        assertThat(categoryBusinessManager.getCategoryById(expectedId)).isEqualTo(oldCategoryDto);
        categoryBusinessManager.createOrUpdate(newCategoryDto);

        assertThat(categoryBusinessManager.getCategoryById(expectedId)).isEqualTo(newCategoryDto);
    }

    @Test
    void givenElementExistsInDb_whenDeleting_shouldNotThrow() {
        CategoryDto expectedCategoryDto = SampleDataService
                .createCategoryDto(1L, "category_name_1", LocalDate.of(2020, 10, 10));

        Assertions.assertDoesNotThrow(() -> categoryBusinessManager.deleteCategoryById(expectedCategoryDto.getId()));
    }

    @Test
    void givenElementExistsInDb_whenDeleting_shouldNotBeFindable() throws ResourceNotFoundException {
        CategoryDto expectedCategoryDto = SampleDataService
                .createCategoryDto(1L, "category_name_1", LocalDate.of(2020, 10, 10));

        categoryBusinessManager.deleteCategoryById(expectedCategoryDto.getId());
        assertThrows(ResourceNotFoundException.class, () -> categoryBusinessManager.getCategoryById(1L));
    }

    @Test
    void givenElementDoesNotExistInDb_whenDeleting_shouldThrow() {
        assertThrows(ResourceNotFoundException.class, () -> categoryBusinessManager.deleteCategoryById(3L));
    }
}