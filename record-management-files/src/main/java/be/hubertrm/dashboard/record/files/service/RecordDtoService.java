package be.hubertrm.dashboard.record.files.service;

import be.hubertrm.dashboard.record.core.dto.RecordDto;

import java.util.Locale;

public interface RecordDtoService {

    RecordDto create(String[] fields, String line);

    RecordDto create(String[] fields, String line, Locale locale);
}
