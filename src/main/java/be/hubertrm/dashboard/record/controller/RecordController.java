package be.hubertrm.dashboard.record.controller;

import be.hubertrm.dashboard.record.dto.RecordDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.manager.RecordBusinessManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RecordController {

    @Resource
    private RecordBusinessManager recordBusinessManager;

    @GetMapping("/records")
    public List<RecordDto> getAllRecords() {
        return recordBusinessManager.getAllRecords();
    }

    @GetMapping("/record/{id}")
    public RecordDto getRecordById(@PathVariable(value = "id") Long recordId) throws ResourceNotFoundException {
        return recordBusinessManager.getRecordById(recordId);
    }

    @PostMapping("/record")
    public RecordDto createRecord(@RequestBody RecordDto recordDto) throws ResourceNotFoundException {
        return recordBusinessManager.createOrUpdate(recordDto);
    }

    @PutMapping("/record/{id}")
    public RecordDto updateRecord(@PathVariable(value = "id") Long recordId,
                                  @RequestBody RecordDto recordDto) throws ResourceNotFoundException {
        return recordBusinessManager.createOrUpdate(recordDto, recordId);
    }

    @DeleteMapping("/record/{id}")
    public Map<String, Boolean> deleteRecordById(@PathVariable(value = "id") Long recordId)
            throws ResourceNotFoundException {
        return recordBusinessManager.deleteRecordById(recordId);
    }
}
