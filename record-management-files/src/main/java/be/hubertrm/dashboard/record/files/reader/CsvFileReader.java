package be.hubertrm.dashboard.record.files.reader;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.files.csv.converter.Converter;
import be.hubertrm.dashboard.record.files.csv.converter.enums.OutputType;
import be.hubertrm.dashboard.record.files.csv.factory.ConverterFactory;
import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.FieldsNotFoundException;
import be.hubertrm.dashboard.record.files.exception.FileNotFoundException;
import be.hubertrm.dashboard.record.files.service.RecordDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class CsvFileReader implements FileReader {

    @Resource
    private RecordDtoService recordDtoService;
    @Resource
    private ConverterFactory converterFactory;

    private static final FileType fileType = FileType.CSV;

    @Override
    public FileType getSupportedFileType() {
        return fileType;
    }

    public Collection<RecordDto> read(String filename) throws FileNotFoundException {
        return read(Paths.get(filename));
    }

    public Collection<RecordDto> read(String filename, Locale locale) throws FileNotFoundException {
        return read(Paths.get(filename), locale);
    }

    public Collection<RecordDto> read(Path path) throws FileNotFoundException {
        return read(path, Locale.ROOT);
    }

    public Collection<RecordDto> read(Path path, Locale locale) throws FileNotFoundException {
        if (!Files.exists(path)) {
            throw new FileNotFoundException(String.format("File %s does not exist.", path));
        }
        List<RecordDto> recordDtoList = new ArrayList<>();
        try {
            String[] headers = getHeaders(path);
            log.debug("First Line: {}", Arrays.toString(headers));
            extractFromFileToList(path, recordDtoList, headers, locale);
        } catch (FieldsNotFoundException | IOException e) {
            log.error("Error reading file from path {}", path, e);
        }
        recordDtoList.forEach(recordDto -> log.info("Record {}", recordDto));
        return recordDtoList;
    }

    private String[] getHeaders(Path path) throws FieldsNotFoundException, IOException {
        Converter<String, ? extends List<String>> converter = converterFactory.createConverter(OutputType.ARRAY);
        try (Stream<String> lines = Files.lines(path)) {
            Optional<String> firstLine = lines.findFirst();
            return firstLine.map(s -> converter.convert(s.toLowerCase()))
                    .orElseThrow(() -> new FieldsNotFoundException("Field Not found")).toArray(new String[]{});
        }
    }

    private void extractFromFileToList(Path path, List<RecordDto> recordDtoList, String[] headers, Locale locale)
            throws IOException {
        try (Stream<String> fileContentStream = Files.lines(path)) {
            recordDtoList.addAll(fileContentStream
                .skip(1)
                .filter(this::filterLine)
                .map(line -> recordDtoService.create(headers, line, locale))
                .collect(Collectors.toList()));
        }
    }

    private boolean filterLine(String line) {
        return Objects.nonNull(line) && !line.isEmpty();
    }
}
