package be.hubertrm.dashboard.record.controller;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.manager.RecordBusinessManager;
import be.hubertrm.dashboard.record.model.Record;
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
    public List<Record> getRecords() {
        return recordBusinessManager.getAllRecords();
    }

    @PostMapping("/records")
    public long createRecord(@RequestBody Record record) throws ResourceNotFoundException {
        return recordBusinessManager.createRecord(record);
    }

    @PutMapping("/records/{id}")
    public ResponseEntity<Long> updateRecord(@PathVariable(value = "id") Long recordId,
                                             @RequestBody Record recordDetails) throws ResourceNotFoundException {
        return ResponseEntity.ok(recordBusinessManager.updateRecord(recordId, recordDetails));
    }
}
