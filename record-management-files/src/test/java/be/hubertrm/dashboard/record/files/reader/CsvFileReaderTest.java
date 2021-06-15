package be.hubertrm.dashboard.record.files.reader;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import be.hubertrm.dashboard.record.files.exception.FileNotFoundException;
import be.hubertrm.dashboard.record.files.service.RecordDtoService;
import be.hubertrm.dashboard.record.files.spring.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class CsvFileReaderTest extends TestConfig {

    @MockBean
    private RecordDtoService recordDtoService;

    @InjectMocks
    @Autowired
    private CsvFileReader csvFileReader;

    private String[] fields;
    private String line;
    private Locale locale;
    private RecordDto expected;
    private String filename = "src/test/resources/reader/recordsUnitTest.csv";

    @BeforeEach
    void setup() {
        fields = new String[]{"date", "price", "category", "source", "note"};
        line = "01-09-2021,\"1,0 €\",test,test,test";
        locale = Locale.ROOT;
        expected = SampleDataService.createRecordDto()
                .setId(null)
                .setPayDate(LocalDate.of(2021, 9, 1));
        given(recordDtoService.create(fields, line, locale)).willReturn(this.expected);
    }

    @Test
    void whenSpecifyingFilename_shouldReturnContentAsRecordDtoList() throws FileNotFoundException {
        Collection<RecordDto> recordDtoList = csvFileReader.read(filename);

        assertThat(recordDtoList).hasSize(1).element(0).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenSpecifyingPath_shouldReturnContentAsRecordDtoList() throws FileNotFoundException {
        Path path = Path.of(filename);
        Collection<RecordDto> recordDtoList = csvFileReader.read(path);

        assertThat(recordDtoList).hasSize(1).element(0).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenSpecifyingLocale_shouldReturnContentAsRecordDtoList() throws FileNotFoundException {
        locale = Locale.FRANCE;
        given(recordDtoService.create(fields, line, locale)).willReturn(expected);

        Collection<RecordDto> recordDtoList = csvFileReader.read(filename);

        assertThat(recordDtoList).hasSize(1).element(0).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenMultipleRecords_shouldReturnAllContentAsRecordDtoList() throws FileNotFoundException {
        filename = "src/test/resources/reader/multipleRecordsUnitTest.csv";
        Collection<RecordDto> recordDtoList = csvFileReader.read(filename);

        assertThat(recordDtoList).hasSize(3).containsOnly(expected);
    }

    @Test
    void givenMultipleRecordsWithEmptyLines_shouldReturnOnlyNonEmptyRecords() throws FileNotFoundException {
        filename = "src/test/resources/reader/multipleRecordsWithEmptyLinesUnitTest.csv";
        Collection<RecordDto> recordDtoList = csvFileReader.read(filename);

        assertThat(recordDtoList).hasSize(2);
    }

    @Test
    void givenReadingWithFilename_whenFileDoesNotExist_shouldThrowFileNotFoundException() {
        assertThrows(FileNotFoundException.class,
                () -> csvFileReader.read("reader/non_existing_file.csv"));
    }

    @Test
    void givenReadingWithPath_whenFileDoesNotExist_shouldThrowFileNotFoundException() {
        Path path = Paths.get("reader/non_existing_file.csv");
        assertThrows(FileNotFoundException.class,
                () -> csvFileReader.read(path));
    }

    @ParameterizedTest(name = "With {0}")
    @ValueSource(strings = {"emptyRecordsUnitTest.csv", "onlyHeadersUnitTest.csv", "noHeaderUnitTest.csv"})
    void givenInvalidFile_whenReading_shouldReturnEmptyCollection(String filename) throws FileNotFoundException {
        String filepath = "src/test/resources/reader/" + filename;
        Collection<RecordDto> recordDtoList = csvFileReader.read(filepath);

        assertThat(recordDtoList).isEmpty();
    }
}