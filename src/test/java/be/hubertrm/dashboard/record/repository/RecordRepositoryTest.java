package be.hubertrm.dashboard.record.repository;

import be.hubertrm.dashboard.record.model.Record;
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
class RecordRepositoryTest {

    @Autowired
    private RecordRepository recordRepository;

    @Test
    void testFindAll() {
        List<Record> expectedRecords = Arrays.asList(
                SampleDataService.createRecord(1L, LocalDate.of(2020, 10, 10), 10.01F,
                        1, 1, "record_comments_1"),
                SampleDataService.createRecord(2L, LocalDate.of(2020, 11, 12), 20F,
                        2, 2, "record_comments_2")
        );

        assertThat(recordRepository.findAll()).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedRecords);
    }

    @Test
    void testCreate() {
        Record record = SampleDataService.createRecord();
        record.setId(null);
        final Record savedRecord = recordRepository.save(record);
        assertThat(savedRecord).isNotNull().isEqualToIgnoringGivenFields(record, "id");
    }

    @Test
    void testFullElementUpdate() {
        Record record = SampleDataService.createRecord();
        record.setId(1L);
        final Record savedRecord = recordRepository.save(record);
        assertThat(savedRecord).isNotNull().isEqualToComparingFieldByField(record);
    }
}