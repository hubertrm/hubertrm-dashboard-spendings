//package be.hubertrm.dashboard.record.service.impl;
//
//import be.hubertrm.dashboard.record.model.Record;
//import be.hubertrm.dashboard.record.sample.SampleDataService;
//import be.hubertrm.dashboard.record.service.RecordService;
//import be.hubertrm.dashboard.record.spring.config.PersistenceJpaConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.test.context.ContextConfiguration;
//
//import javax.annotation.Resource;
//import javax.transaction.Transactional;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ContextConfiguration(classes = PersistenceJpaConfig.class)
//@SpringBootApplication(scanBasePackages ="be.hubertrm.dashboard.record")
//@Transactional
//class RecordServiceImplTest {
//
//    @Resource
//    private RecordService recordService;
//
//    @Test
//    void createOrUpdate() {
//        Record record = SampleDataService.createRecord();
//        record.setId(null);
//        final Record savedRecord = recordService.createOrUpdate(record);
//        assertThat(savedRecord).isNotNull().isEqualToIgnoringGivenFields(record, "id");
//    }
//
//    @Test
//    void createOrUpdate_whenTryingToUpdateNonExisting_shouldCreate() {
//        Record record = SampleDataService.createRecord();
//        final Record savedRecord = recordService.createOrUpdate(record);
//        assertThat(savedRecord).isNotNull().isEqualToComparingFieldByField(record);
//    }
//
//    @Test
//    void createOrUpdate_whenTryingToCreateEmpty_shouldReturnEmpty() {
//        final Record savedRecord = recordService.createOrUpdate(new Record());
//        assertThat(savedRecord).isNotNull().isEqualToIgnoringGivenFields(new Record(), "id");
//    }
//
//    @Test
//    void createOrUpdate_whenTryingToUpdate_shouldReturnUpdated() {
//        Record record = SampleDataService.createRecord();
//        record.setId(0L);
//        final Record savedRecord = recordService.createOrUpdate(record);
//        savedRecord.setAmount(10);
//        savedRecord.setPayDate(LocalDate.now());
//
//        final Record savedRecord2 = recordService.createOrUpdate(savedRecord);
//        assertThat(savedRecord2).isNotNull().isEqualToComparingFieldByField(savedRecord);
//    }
//
//    @Test
//    void getAllRecords() {
//        final Record savedRecord1 = recordService.createOrUpdate(SampleDataService.createRecord());
//        final Record savedRecord2 = recordService.createOrUpdate(SampleDataService.createRecord());
//
//        final List<Record> fetchedRecords = recordService.getAllRecords();
//
//        assertThat(fetchedRecords)
//                .usingFieldByFieldElementComparator()
//                .containsExactlyElementsOf(Arrays.asList(savedRecord1, savedRecord2));
//    }
//
//    @Test
//    void getAllRecords_whenNoRecords_shouldReturnEmptyList() {
//        final List<Record> fetchedRecords = recordService.getAllRecords();
//
//        assertThat(fetchedRecords).isEmpty();
//    }
//
//    @Test
//    void getRecordById() {
//        final Record savedRecord = recordService.createOrUpdate(SampleDataService.createRecord());
//
//        final Optional<Record> fetchedRecordOptional = recordService.getRecordById(savedRecord.getId());
//
//        assertThat(fetchedRecordOptional).isPresent()
//                .usingFieldByFieldValueComparator().isEqualTo(savedRecord);
//    }
//
////    @Test
////    void getRecordById_whenTryingToFetchNull_ReturnEmptyOptional() {
////        final Optional<Record> fetchedRecordOptional = recordService.getRecordById(null);
////
////        assertThat(fetchedRecordOptional).isNotPresent();
////    }
//
//    @Test
//    void getRecordById_whenTryingToFetchNonExistingRecord_ShouldReturnEmptyOptional() {
//        final Optional<Record> fetchedRecordOptional = recordService.getRecordById(1L);
//
//        assertThat(fetchedRecordOptional).isNotPresent();
//    }
//}