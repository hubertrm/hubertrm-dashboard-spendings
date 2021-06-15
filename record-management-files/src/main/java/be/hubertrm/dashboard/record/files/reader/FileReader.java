package be.hubertrm.dashboard.record.files.reader;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.FileNotFoundException;

import java.util.Collection;
import java.util.Locale;

/**
 * {@code FileReader} class represents properties and behaviors of FileReader objects
 * in the record Management System.
 * <br>
 *    The FileReader can read a collection of {@link RecordDto} objects from a file.
 * <br>
 * <p>ON : may 22, 2021
 *
 * @version 1.0
 * @author Hubert Romain - hubertrm
 */
public interface FileReader {

    /**
     * @return the class unique supported {@link FileType}
     */
    FileType getSupportedFileType();

    /**
     * read a collection of {@link RecordDto}s from a given file identified by its filename.
     * If the file does not exists, an {@link FileNotFoundException} exception is thrown.
     *
     * @param filename: the name of the file in which the records need to be written
     * @return the collection of {@link RecordDto} objects that have been read from the file
     */
    Collection<RecordDto> read(String filename) throws FileNotFoundException;

    /**
     * read a collection of {@link RecordDto}s from a given file identified by its filename.
     * If the file does not exists, an {@link FileNotFoundException} exception is thrown.
     *
     * @param filename: the name of the file in which the records need to be written
     * @param locale: the locale of the file
     * @return the collection of {@link RecordDto} objects that have been read from the file
     */
    Collection<RecordDto> read(String filename, Locale locale) throws FileNotFoundException;
}
