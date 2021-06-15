package be.hubertrm.dashboard.record.files.manager;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.FileNotFoundException;
import be.hubertrm.dashboard.record.files.exception.ReadWriteException;
import be.hubertrm.dashboard.record.files.factory.FileReaderFactory;
import be.hubertrm.dashboard.record.files.reader.FileReader;
import be.hubertrm.dashboard.record.files.spring.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class ReadManagerTest extends TestConfig {

    @MockBean
    private FileReader fileReader;
    @MockBean
    private FileReaderFactory fileReaderFactory;

    @InjectMocks
    @Autowired
    private ReadManager readManager;

    private Collection<RecordDto> expected;

    @BeforeEach
    void setup() {
        expected = Collections.singletonList(SampleDataService.createRecordDto()
                .setId(null)
                .setPayDate(LocalDate.of(2021, 9, 1)));
    }

    @ParameterizedTest(name = "With {1} Record(s)")
    @CsvSource({"recordsUnitTest.csv,1", "multipleRecordsUnitTest.csv,3"})
    void givenAllRecordsAreRead_thenReturnsAllRecords(String filename, int size)
            throws ReadWriteException, FileNotFoundException {
        expected = Collections.nCopies(size, SampleDataService.createRecordDto());
        given(fileReader.read(filename)).willReturn(expected);
        given(fileReaderFactory.create(FileType.CSV)).willReturn(fileReader);

        Collection<RecordDto> actualRecordDtoCollection = readManager.readFile(filename, FileType.CSV);

        assertThat(actualRecordDtoCollection).hasSize(size)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void givenSomeRecordsCouldNotBeRead_thenReturnIncompleteRecordList() throws ReadWriteException, FileNotFoundException {
        String filename = "invalidRecordsUnitTest.csv";
        given(fileReader.read(filename)).willReturn(expected);
        given(fileReaderFactory.create(FileType.CSV)).willReturn(fileReader);

        Collection<RecordDto> actualRecordDtoCollection = readManager.readFile(filename, FileType.CSV);

        assertThat(actualRecordDtoCollection).hasSize(1)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void givenAllRecordsCouldNotBeWritten_thenReturnEmptyRecordList() throws ReadWriteException, FileNotFoundException {
        String filename = "onlyInvalidRecordsUnitTest.csv";
        given(fileReader.read(filename)).willReturn(Collections.emptyList());
        given(fileReaderFactory.create(FileType.CSV)).willReturn(fileReader);

        Collection<RecordDto> actualRecordDtoCollection = readManager.readFile(filename, FileType.CSV);

        assertThat(actualRecordDtoCollection).isEmpty();
    }

    @Test
    void givenFileReaderFactoryThrows_thenShouldThrow() throws ReadWriteException {
        String filename = "test";
        given(fileReaderFactory.create(FileType.CSV)).willThrow(ReadWriteException.class);

        assertThatThrownBy(() -> readManager.readFile(filename, FileType.CSV)).isInstanceOf(ReadWriteException.class);
    }

    @Test
    void givenFileReaderThrows_thenShouldThrow() throws FileNotFoundException, ReadWriteException {
        String filename = "test";
        given(fileReader.read(filename)).willThrow(FileNotFoundException.class);
        given(fileReaderFactory.create(FileType.CSV)).willReturn(fileReader);

        assertThatThrownBy(() -> readManager.readFile(filename, FileType.CSV)).isInstanceOf(FileNotFoundException.class);
    }
}