package be.hubertrm.dashboard.record.repository;

import be.hubertrm.dashboard.record.model.Source;
import be.hubertrm.dashboard.record.sample.SampleDataService;
import be.hubertrm.dashboard.record.spring.config.PersistenceTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = PersistenceTestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class SourceRepositoryTest {

    @Autowired
    private SourceRepository sourceRepository;

    @Test
    void testFindSourceByName() {
        String newSourceName = "source_name_1";
        assertThat(sourceRepository.findSourceByName(newSourceName))
                .isPresent().get()
                .extracting("id", "name", "organisationId").isEqualTo(Arrays.asList(1L, newSourceName, 1L));
    }


    @Test
    void testFindNonExistingSourceByName() {
        String nonExistingSource = "non_existing_source";
        assertThat(sourceRepository.findSourceByName(nonExistingSource))
                .isNotPresent();
    }

    @Test
    void testFindAll() {
        List<Source> expectedSources = Arrays.asList(
                SampleDataService.createSource(1L, "source_name_1",
                        LocalDate.of(2020, 10, 10),1L),
                SampleDataService.createSource(2L, "source_name_2",
                        LocalDate.of(2020, 11, 12),2L)
        );

        assertThat(sourceRepository.findAll()).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedSources);
    }

    @Test
    void testCreate() {
        Source source = SampleDataService.createSource();
        source.setId(null);
        final Source savedSource = sourceRepository.save(source);
        assertThat(savedSource).isNotNull().isEqualToIgnoringGivenFields(source, "id");
    }

    @Test
    void testUpdate() {
        Source source = SampleDataService.createSource();
        source.setId(1L);
        final Source savedSource = sourceRepository.save(source);
        assertThat(savedSource).isNotNull().isEqualToComparingFieldByField(source);
    }
}