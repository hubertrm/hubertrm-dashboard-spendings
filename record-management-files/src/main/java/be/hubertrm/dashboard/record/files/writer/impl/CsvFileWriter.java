package be.hubertrm.dashboard.record.files.writer.impl;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.writer.FileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
public class CsvFileWriter implements FileWriter {

    private static final String DELIMITER = ",";
    private static final FileType fileType = FileType.CSV;
    private static final StandardOpenOption[] options = {StandardOpenOption.CREATE,
            StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING};

    @Override
    public FileType getSupportedFileType() {
        return fileType;
    }

    @Override
    public Collection<RecordDto> write(String filename, Collection<RecordDto> recordDtoCollection) throws IOException {
        var path = Paths.get(filename);
        Collection<RecordDto> writtenLines = new ArrayList<>();
        try (var writer = Files.newBufferedWriter(path, options)) {
            for (var recordDto: recordDtoCollection) {
                writeRecordDto(filename, writtenLines, writer, recordDto);
            }
        } catch (IOException e) {
            log.debug("Could not write to file {}", filename, e);
            throw new IOException("Could not write to file " + filename, e);
        }
        if (writtenLines.isEmpty()) {
            log.info("No records have been successfully saved");
        } else {
            log.info("{} out of {} records have been successfully saved", writtenLines.size(), recordDtoCollection.size());
        }
        return writtenLines;
    }

    private void writeRecordDto(String filename, Collection<RecordDto> writtenLines, BufferedWriter writer, RecordDto recordDto) {
        try {
            writer.write(convert(recordDto));
            writtenLines.add(recordDto);
            writer.newLine();
        } catch (Exception e) {
            log.debug("Error while writing element {} to file {}", recordDto, filename, e);
        }
    }

    private String convert(RecordDto recordDto) {
        return String.join(DELIMITER,
                normalize(recordDto.getDate().toString()),
                normalize(Float.toString(recordDto.getAmount())),
                normalize(recordDto.getCategoryDto().getName()),
                normalize(recordDto.getSourceDto().getName()),
                normalize(recordDto.getComments()));
    }

    private String normalize(String value) {
        return value.trim();
    }
}
