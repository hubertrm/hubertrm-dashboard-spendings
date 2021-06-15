package be.hubertrm.dashboard.record.files.factory;

import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.ReadWriteException;
import be.hubertrm.dashboard.record.files.reader.CsvFileReader;
import be.hubertrm.dashboard.record.files.reader.FileReader;
import be.hubertrm.dashboard.record.files.spring.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileReaderFactoryTest extends TestConfig {
    private FileReader expectedFileReader;
    private FileReaderFactory fileReaderFactory;

    @BeforeEach
    void setup() {
        expectedFileReader = new CsvFileReader();
        Set<FileReader> writerSet = Set.of(expectedFileReader);
        fileReaderFactory = new FileReaderFactory(writerSet);
    }

    @Test
    void createCsvFileReader() throws ReadWriteException {
        assertThat(fileReaderFactory.create(FileType.CSV)).isEqualTo(expectedFileReader);
    }

    @Test
    void nonSupportedFileType_shouldThrowException() {
        assertThatThrownBy(() -> fileReaderFactory.create(FileType.JSON)).isInstanceOf(ReadWriteException.class);
    }

}