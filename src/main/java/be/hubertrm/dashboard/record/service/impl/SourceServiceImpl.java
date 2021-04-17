package be.hubertrm.dashboard.record.service.impl;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Source;
import be.hubertrm.dashboard.record.repository.SourceRepository;
import be.hubertrm.dashboard.record.service.SourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class SourceServiceImpl implements SourceService {

    @Resource
    private SourceRepository sourceRepository;

    private static final String SOURCE_NOT_FOUND_MESSAGE = "Source not found for this id :: ";
    private static final String SOURCE_NOT_FOUND_BY_NAME_MESSAGE = "Source not found for this name :: ";

    public List<Source> getAllSources() {
        return sourceRepository.findAll();
    }

    public Source getSourceById(Long sourceId)
            throws ResourceNotFoundException {
        return sourceRepository.findById(sourceId).orElseThrow(
                () -> new ResourceNotFoundException(SOURCE_NOT_FOUND_MESSAGE + sourceId)
        );
    }
    public Source getSourceByName(String name)
            throws ResourceNotFoundException {
        return sourceRepository.findSourceByName(name).orElseThrow(
                () -> new ResourceNotFoundException(SOURCE_NOT_FOUND_BY_NAME_MESSAGE + name)
        );
    }

    public Source saveOrUpdate(Source source) {
        return sourceRepository.save(source);
    }

    public Source updateSource(Long sourceId, Source sourceDetails)
            throws ResourceNotFoundException {
        Source source = sourceRepository.findById(sourceId)
                .orElseThrow(() -> new ResourceNotFoundException(SOURCE_NOT_FOUND_MESSAGE + sourceId));

        source.setCreationDate(sourceDetails.getCreationDate());
        source.setName(sourceDetails.getName());
        source.setOrganisationId(sourceDetails.getOrganisationId());

        return sourceRepository.save(source);
    }

    public Map<String, Boolean> deleteSource(Long sourceId) throws ResourceNotFoundException {
        Source source = sourceRepository.findById(sourceId)
                .orElseThrow(() -> new ResourceNotFoundException(SOURCE_NOT_FOUND_MESSAGE + sourceId));

        sourceRepository.delete(source);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
