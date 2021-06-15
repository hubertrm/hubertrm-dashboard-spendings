package be.hubertrm.dashboard.record.files.writer;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import be.hubertrm.dashboard.record.files.spring.TestConfig;
import be.hubertrm.dashboard.record.files.writer.impl.CsvFileWriter;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class CsvFileWriterTest extends TestConfig {

    @Rule
    static TemporaryFolder tempDir = new TemporaryFolder();
    private String filename;
    private Path expectedPath;

    @Autowired
    private CsvFileWriter csvFileWriter;

    @BeforeEach
    void setup() throws IOException {
        tempDir.create();
        expectedPath = tempDir.getRoot().toPath().resolve("test.csv");
        filename = expectedPath.toString();
    }

    @Test
    void shouldThrowException_ifIOException() {
        expectedPath = tempDir.getRoot().toPath();
        filename = expectedPath.toString();
        assertThatThrownBy(() -> csvFileWriter.write(filename, Collections.singletonList(SampleDataService.createRecordDto())))
                .isInstanceOf(IOException.class);
    }

    @ParameterizedTest(name = "With {1} Record(s)")
    @MethodSource("provideValidRecordsCollection")
    void givenRecordsAreWritten_thenFileIsCreated(Collection<RecordDto> expected, int size) throws IOException {
        csvFileWriter.write(filename, expected);

        assertThat(expectedPath).exists();
    }

    @ParameterizedTest(name = "With {1} Record(s)")
    @MethodSource("provideValidRecordsCollection")
    void givenAllRecordsAreWritten_thenReturnsAllRecords(Collection<RecordDto> expected, int size) throws IOException {
        Collection<RecordDto> actual = csvFileWriter.write(filename, expected);

        assertThat(actual).hasSize(size).containsExactlyInAnyOrderElementsOf(expected);
    }

    @ParameterizedTest()
    @MethodSource("provideInvalidRecordsCollection")
    void givenSomeRecordsCouldNotBeWritten_thenReturnIncompleteRecordList(Collection<RecordDto> actual,
                                                                          Collection<RecordDto> expected) throws IOException {
        Collection<RecordDto> recordCollection = csvFileWriter.write(filename, actual);

        assertThat(recordCollection).containsExactlyInAnyOrderElementsOf(expected);
    }

    @ParameterizedTest()
    @MethodSource("provideInvalidRecordsCollection")
    void givenSomeRecordsCouldNotBeWritten_thenFileDoesNotContainThem(Collection<RecordDto> actual,
                                                                      Collection<RecordDto> expected,
                                                                      String expectedContent) throws IOException {
        Collection<RecordDto> recordCollection = csvFileWriter.write(filename, actual);

        assertThat(recordCollection).containsExactlyInAnyOrderElementsOf(expected);
        assertThat(expectedPath).exists();
        assertThat(contentOf(expectedPath.toFile())).contains(expectedContent);
    }

    @ParameterizedTest(name = "With Only {index} Invalid Record(s)")
    @MethodSource("provideOnlyInvalidRecordsCollection")
    void givenAllRecordsCouldNotBeWritten_thenReturnEmptyRecordList(Collection<RecordDto> actual) throws IOException {
        Collection<RecordDto> recordCollection = csvFileWriter.write(filename, actual);

        assertThat(recordCollection).isEmpty();
    }

    @ParameterizedTest(name = "With Only {index} Invalid Record(s)")
    @MethodSource("provideOnlyInvalidRecordsCollection")
    void givenAllRecordsCouldNotBeWritten_thenFileDoesNotContainAnything(Collection<RecordDto> actual) throws IOException {
        Collection<RecordDto> recordCollection = csvFileWriter.write(filename, actual);

        assertThat(recordCollection).isEmpty();
        assertThat(expectedPath).exists();
        assertThat(contentOf(expectedPath.toFile())).isEmpty();
    }

    private static Stream<Arguments> provideValidRecordsCollection() {
        RecordDto recordDto = SampleDataService.createRecordDto();
        return Stream.of(
                Arguments.of(Collections.emptyList(), 0),
                Arguments.of(Collections.singletonList(recordDto), 1),
                Arguments.of(Collections.nCopies(2, recordDto), 2),
                Arguments.of(Collections.nCopies(3, recordDto), 3)
        );
    }

    private static Stream<Arguments> provideInvalidRecordsCollection() {
        RecordDto recordDto = SampleDataService.createRecordDto().setPayDate(LocalDate.of(2021, 9, 1));
        RecordDto invalidRecordDto = buildInvalidRecordDto();
        String expectedContent = "2021-09-01,1.0,test,test,test";
        return Stream.of(
                Arguments.of(Arrays.asList(recordDto, invalidRecordDto), Collections.singletonList(recordDto), expectedContent),
                Arguments.of(Arrays.asList(invalidRecordDto, recordDto), Collections.singletonList(recordDto), expectedContent),
                Arguments.of(Arrays.asList(recordDto, invalidRecordDto, recordDto), Arrays.asList(recordDto, recordDto), expectedContent),
                Arguments.of(Arrays.asList(invalidRecordDto, recordDto, invalidRecordDto), Collections.singletonList(recordDto), expectedContent)
        );
    }

    private static Stream<Arguments> provideOnlyInvalidRecordsCollection() {
        RecordDto invalidRecordDto = buildInvalidRecordDto();
        return Stream.of(
                Arguments.of(Collections.singletonList(invalidRecordDto)),
                Arguments.of(Collections.nCopies(2, invalidRecordDto)),
                Arguments.of(Collections.nCopies(3, invalidRecordDto))
        );
    }

    private static RecordDto buildInvalidRecordDto() {
        return SampleDataService.createRecordDto(null, null, Float.NaN, null, null, null);
    }
}