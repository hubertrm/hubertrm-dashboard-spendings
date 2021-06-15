package be.hubertrm.dashboard.record.files.manager;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.FileNotFoundException;
import be.hubertrm.dashboard.record.files.exception.ReadWriteException;
import be.hubertrm.dashboard.record.files.factory.FileReaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Slf4j
@Component
public class ReadManager {

    @Resource
    private FileReaderFactory fileReaderFactory;

    public Collection<RecordDto> readFile(String filename, FileType type) throws ReadWriteException, FileNotFoundException {
        var fileReader = fileReaderFactory.create(type);
        Collection<RecordDto> readerRecords = fileReader.read(filename);
        if (readerRecords.isEmpty()) {
            log.debug("No records read ...");
        } else {
            log.debug("{} records have been read: {}",
                    readerRecords.size(),
                    readerRecords);
        }
        return readerRecords;
    }
}
