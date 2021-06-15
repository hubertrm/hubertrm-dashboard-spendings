package be.hubertrm.dashboard.record.files.writer;

import be.hubertrm.dashboard.record.core.dto.RecordDto;

import java.io.IOException;
import java.util.Collection;

/**
 * {@code FileWriter} class represents properties and behaviors of FileWriter objects
 * in the record Management System.
 * <br>
 *    The FileWriter can write a collection of {@link RecordDto} objects to a file.
 * <br>
 * <p>ON : may 22, 2021
 *
 * @version 1.0
 * @author Hubert Romain - hubertrm
 */
public interface FileWriter extends Writer {

    /**
     * Write a collection of {@link RecordDto}s to a given file identified by its filename.
     * If the file does not exists, it is created. Otherwise it is replaced.
     *
     * @param filename: the name of the file in which the records need to be written
     * @param recordDtoCollection: the collection of {@link RecordDto} objects to be written out
     * @return the collection of {@link RecordDto} objects that have not been written to the file
     */
    Collection<RecordDto> write(String filename, Collection<RecordDto> recordDtoCollection) throws IOException;
}
