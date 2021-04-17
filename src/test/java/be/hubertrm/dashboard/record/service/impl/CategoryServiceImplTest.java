package be.hubertrm.dashboard.record.service.impl;

import be.hubertrm.dashboard.record.model.Category;
import be.hubertrm.dashboard.record.sample.SampleDataService;
import be.hubertrm.dashboard.record.service.CategoryService;
import be.hubertrm.dashboard.record.spring.config.PersistenceTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@ContextConfiguration(classes = PersistenceTestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:scripts/sql_tests_category.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, statements = {"DROP TABLE category;"})
@Transactional
class CategoryServiceImplTest {

    @Resource
    private CategoryService categoryService;

    @Test
    void createOrUpdate() {
        Category category = SampleDataService.createCategory();
        category.setId(null);
        final Category savedCategory = categoryService.createOrUpdate(category);
        assertThat(savedCategory).isNotNull().isEqualToIgnoringGivenFields(category, "id");
    }
}