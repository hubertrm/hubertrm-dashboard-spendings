package be.hubertrm.dashboard.record.controller;

import be.hubertrm.dashboard.record.dto.SourceDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Source;
import be.hubertrm.dashboard.record.service.SourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class AccountController {

    @Resource
    private SourceService sourceService;

    @GetMapping("/accounts")
    public List<Source> getAllAccounts() {
        return sourceService.getAllSources();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Source> getAccountById(@PathVariable(value = "id") Long accountId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(sourceService.getSourceById(accountId));
    }

    @PostMapping("/accounts")
    public Source createAccount(@RequestBody Source source) {
        return sourceService.saveOrUpdate(source);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Source> updateAccount(@PathVariable(value = "id") Long sourceId,
                                                @RequestBody SourceDto sourceDto) throws ResourceNotFoundException {
        return null;
//        return ResponseEntity.ok(sourceService.saveOrUpdate(sourceDto, sourceId));
    }

    @DeleteMapping("/accounts/{id}")
    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long sourceId)
        throws ResourceNotFoundException {
        return sourceService.deleteSource(sourceId);
    }
}
