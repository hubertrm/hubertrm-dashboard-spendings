package be.hubertrm.dashboard.record.files.manager;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.ReadWriteException;
import be.hubertrm.dashboard.record.files.factory.FileWriterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
public class WriteManager {

    @Resource
    private FileWriterFactory fileWriterFactory;

    public Collection<RecordDto> writeFile(String filename, Collection<RecordDto> records, FileType type)
            throws IOException, ReadWriteException {
        var fileWriter = fileWriterFactory.create(type);
        Collection<RecordDto> writtenRecords = fileWriter.write(filename, records);
        if (writtenRecords.isEmpty()) {
            log.debug("All records written successfully !");
        } else {
            log.debug("{} records have been written out of {}: {}",
                    writtenRecords.size(),
                    records.size(),
                    writtenRecords);
        }
        return writtenRecords;
    }
}
