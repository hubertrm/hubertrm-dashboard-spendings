package be.hubertrm.dashboard.record.core.service;

import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.model.Source;

import java.util.List;
import java.util.Map;

public interface SourceService {

    List<Source> getAllSources();

    Source getSourceById(Long sourceId) throws ResourceNotFoundException;

    Source getSourceByName(String name) throws ResourceNotFoundException;

    Source createOrUpdate(Source source);

    Source updateSource(Long sourceId, Source sourceDetails) throws ResourceNotFoundException;

    Map<String, Boolean> deleteSourceById(Long sourceId) throws ResourceNotFoundException;
}
