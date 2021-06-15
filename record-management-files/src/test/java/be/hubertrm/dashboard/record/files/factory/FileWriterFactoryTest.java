package be.hubertrm.dashboard.record.files.factory;

import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.ReadWriteException;
import be.hubertrm.dashboard.record.files.spring.TestConfig;
import be.hubertrm.dashboard.record.files.writer.FileWriter;
import be.hubertrm.dashboard.record.files.writer.impl.CsvFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class FileWriterFactoryTest extends TestConfig {

    private FileWriter expectedFileWriter;
    private FileWriterFactory fileWriterFactory;

    @BeforeEach
    void setup() {
        expectedFileWriter = new CsvFileWriter();
        Set<FileWriter> writerSet = Set.of(expectedFileWriter);
        fileWriterFactory = new FileWriterFactory(writerSet);
    }

    @Test
    void createCsvFileWriter() throws ReadWriteException {
        assertThat(fileWriterFactory.create(FileType.CSV)).isEqualTo(expectedFileWriter);
    }

    @Test
    void nonSupportedFileType_shouldThrowException() {
        assertThatThrownBy(() -> fileWriterFactory.create(FileType.JSON)).isInstanceOf(ReadWriteException.class);
    }
}