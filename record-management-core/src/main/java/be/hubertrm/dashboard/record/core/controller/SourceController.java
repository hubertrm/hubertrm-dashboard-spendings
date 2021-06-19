package be.hubertrm.dashboard.record.core.controller;

import be.hubertrm.dashboard.record.core.dto.SourceDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.manager.SourceBusinessManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class SourceController {

    @Resource
    private SourceBusinessManager sourceBusinessManager;

    @GetMapping("/sources")
    public List<SourceDto> getAllSources() {
        return sourceBusinessManager.getAllSources();
    }

    @GetMapping("/source/{id}")
    public SourceDto getSourceById(@PathVariable(value = "id") Long sourceId)
            throws ResourceNotFoundException {
        return sourceBusinessManager.getSourceById(sourceId);
    }

    @PostMapping("/source")
    public SourceDto createSource(@RequestBody SourceDto sourceDto) throws ResourceNotFoundException {
        return sourceBusinessManager.createOrUpdate(sourceDto);
    }

    @PutMapping("/source/{id}")
    public SourceDto updateSource(@PathVariable(value = "id") Long sourceId,
                                  @RequestBody SourceDto sourceDto) throws ResourceNotFoundException {
        return sourceBusinessManager.createOrUpdate(sourceDto, sourceId);
    }

    @DeleteMapping("/source/{id}")
    public Map<String, Boolean> deleteSourceById(@PathVariable(value = "id") Long sourceId)
            throws ResourceNotFoundException {
        return sourceBusinessManager.deleteSourceById(sourceId);
    }
}