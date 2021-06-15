package be.hubertrm.dashboard.record.files.writer;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.files.enums.FileType;

/**
 * {@code Writer} class represents properties and behaviors of Writer objects
 * in the record Management System.
 * <br>
 *    The Writer can write a collection of {@link RecordDto} objects to a destination (File, Database).
 * <br>
 * <p>ON : may 22, 2021
 *
 * @version 1.0
 * @author Hubert Romain - hubertrm
 */
public interface Writer {

    /**
     * @return the class unique supported {@link FileType}
     */
    FileType getSupportedFileType();
}
