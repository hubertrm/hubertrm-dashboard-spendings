package be.hubertrm.dashboard.record.core.controller;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.manager.RecordBusinessManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @ResponseStatus(HttpStatus.CREATED)
    public RecordDto createRecord(@RequestBody RecordDto recordDto) throws ResourceNotFoundException {
        return recordBusinessManager.createOrUpdate(recordDto);
    }

    @PostMapping("/records")
    public List<RecordDto> saveRecords(@RequestBody List<RecordDto> recordDtoCollection) {
        return recordBusinessManager.createOrUpdate(recordDtoCollection);
    }

    @PutMapping("/record/{id}")
    public RecordDto updateRecord(@PathVariable(value = "id") Long recordId,
                                  @RequestBody RecordDto recordDto) throws ResourceNotFoundException {
        return recordBusinessManager.createOrUpdate(recordDto, recordId);
    }

    @DeleteMapping("/record/{id}")
    public void deleteRecordById(@PathVariable(value = "id") Long recordId)
            throws ResourceNotFoundException {
        recordBusinessManager.deleteRecordById(recordId);
    }
}
