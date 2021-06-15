package be.hubertrm.dashboard.record.files.manager;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.ReadWriteException;
import be.hubertrm.dashboard.record.files.factory.FileWriterFactory;
import be.hubertrm.dashboard.record.files.spring.TestConfig;
import be.hubertrm.dashboard.record.files.writer.FileWriter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class WriteManagerTest extends TestConfig {

    @MockBean
    private FileWriter fileWriter;
    @MockBean
    private FileWriterFactory fileWriterFactory;

    @InjectMocks
    @Autowired
    private WriteManager writeManager;

    @ParameterizedTest(name = "With {1} Record(s)")
    @MethodSource("provideValidRecordsCollection")
    void givenAllRecordsAreWritten_thenReturnsAllRecords(Collection<RecordDto> expectedRecordDtoCollection, int size) throws IOException, ReadWriteException {
        String filename = "test.csv";
        given(fileWriter.write(filename, expectedRecordDtoCollection)).willReturn(expectedRecordDtoCollection);
        given(fileWriterFactory.create(FileType.CSV)).willReturn(fileWriter);

        Collection<RecordDto> actualRecordDtoCollection = writeManager.writeFile(filename, expectedRecordDtoCollection, FileType.CSV);

        assertThat(actualRecordDtoCollection).hasSize(size)
                .containsExactlyInAnyOrderElementsOf(expectedRecordDtoCollection);
    }

    @ParameterizedTest(name = "With {2} Invalid Record(s)")
    @MethodSource("provideInvalidRecordsCollection")
    void givenSomeRecordsCouldNotBeWritten_thenReturnIncompleteRecordList(Collection<RecordDto> source,
                                                                          Collection<RecordDto> expected, int size)
            throws IOException, ReadWriteException {
        String filename = "test.csv";
        given(fileWriter.write(filename, source)).willReturn(expected);
        given(fileWriterFactory.create(FileType.CSV)).willReturn(fileWriter);

        Collection<RecordDto> actualRecordDtoCollection = writeManager.writeFile(filename, source, FileType.CSV);

        assertThat(actualRecordDtoCollection).hasSize(expected.size())
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @ParameterizedTest(name = "With {1} Invalid Record(s)")
    @MethodSource("provideOnlyInvalidRecordsCollection")
    void givenAllRecordsCouldNotBeWritten_thenReturnEmptyRecordList(Collection<RecordDto> actual, int size)
            throws IOException, ReadWriteException {
        String filename = "test.csv";
        given(fileWriter.write(filename, actual)).willReturn(Collections.emptyList());
        given(fileWriterFactory.create(FileType.CSV)).willReturn(fileWriter);

        Collection<RecordDto> actualRecordDtoCollection = writeManager.writeFile(filename, actual, FileType.CSV);

        assertThat(actualRecordDtoCollection).isEmpty();
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
        return Stream.of(
                Arguments.of(Arrays.asList(recordDto, invalidRecordDto), Collections.singletonList(recordDto), 1),
                Arguments.of(Arrays.asList(invalidRecordDto, recordDto), Collections.singletonList(recordDto), 1),
                Arguments.of(Arrays.asList(recordDto, invalidRecordDto, recordDto), Arrays.asList(recordDto, recordDto), 1),
                Arguments.of(Arrays.asList(invalidRecordDto, recordDto, invalidRecordDto), Collections.singletonList(recordDto), 2)
        );
    }

    private static Stream<Arguments> provideOnlyInvalidRecordsCollection() {
        RecordDto invalidRecordDto = buildInvalidRecordDto();
        return Stream.of(
                Arguments.of(Collections.singletonList(invalidRecordDto), 1),
                Arguments.of(Collections.nCopies(2, invalidRecordDto), 2),
                Arguments.of(Collections.nCopies(3, invalidRecordDto), 3)
        );
    }

    private static RecordDto buildInvalidRecordDto() {
        return SampleDataService.createRecordDto(null, null, Float.NaN, null, null, null);
    }
}