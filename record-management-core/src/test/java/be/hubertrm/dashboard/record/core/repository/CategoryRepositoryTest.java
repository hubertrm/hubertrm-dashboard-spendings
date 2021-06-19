package be.hubertrm.dashboard.record.core.repository;

import be.hubertrm.dashboard.record.core.model.Category;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class CategoryRepositoryTest {

    @Resource
    private CategoryRepository categoryRepository;

    @Test
    void testFindCategoryByName() {
        String newCategoryName = "category_name_1";
        assertThat(categoryRepository.findCategoryByName(newCategoryName)).isPresent().get()
                .extracting("id", "name").isEqualTo(Arrays.asList(1L, newCategoryName));
    }

    @Test
    void testFindAll() {
        List<Category> expectedCategories = Arrays.asList(
                SampleDataService.createCategory(1L, "category_name_1", LocalDate.of(2020, 10, 10)),
                SampleDataService.createCategory(2L, "category_name_2", LocalDate.of(2020, 11, 12))
        );

        assertThat(categoryRepository.findAll()).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedCategories);
    }

    @Test
    void testFindNonExistingCategoryByName() {
        String nonExistingName = "non_existing_category";
        assertThat(categoryRepository.findCategoryByName(nonExistingName))
                .isEmpty();
    }

    @Test
    void testCreate() {
        Category category = SampleDataService.createCategory();
        category.setId(null);
        final Category savedCategory = categoryRepository.save(category);
        assertThat(savedCategory).isNotNull().isEqualToIgnoringGivenFields(category, "id");
    }

    @Test
    void testFullElementUpdate() {
        Category category = SampleDataService.createCategory();
        category.setId(1L);
        final Category savedCategory = categoryRepository.save(category);
        assertThat(savedCategory).isNotNull().isEqualToComparingFieldByField(category);
    }
}