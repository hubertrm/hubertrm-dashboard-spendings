package be.hubertrm.dashboard.record.controller;

import be.hubertrm.dashboard.record.dto.RecordDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.manager.RecordBusinessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RecordController {

    @Autowired
    RecordBusinessManager recordBusinessManager;

    @GetMapping("/records")
    public List<RecordDto> getRecords() {
        return recordBusinessManager.getAllRecords();
    }

    @PostMapping("/records")
    public RecordDto createRecord(@RequestBody RecordDto recordDto) throws ResourceNotFoundException {
        return recordBusinessManager.createOrUpdate(recordDto);
    }

    @PutMapping("/records/{id}")
    public ResponseEntity<RecordDto> updateRecord(@PathVariable(value = "id") Long recordId,
                                             @RequestBody RecordDto recordDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(recordBusinessManager.createOrUpdate(recordDto));
    }
}
