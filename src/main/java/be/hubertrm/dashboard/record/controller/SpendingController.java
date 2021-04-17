package be.hubertrm.dashboard.record.controller;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Spending;
import be.hubertrm.dashboard.record.service.impl.SpendingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class SpendingController {

    @Resource
    private SpendingService spendingService;

    @GetMapping("/spendings")
    public List<Spending> getAllSpendings() {
        return spendingService.getAllSpendings();
    }

    @GetMapping("/spendings/{id}")
    public ResponseEntity<Spending> getSpendingById(@PathVariable(value = "id") Long spendingId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(spendingService.getSpendingById(spendingId));
    }

    @PostMapping("/spendings")
    public Spending createSpending(@RequestBody Spending spending) {
        return spendingService.createSpending(spending);
    }

    @PutMapping("/spendings/{id}")
    public ResponseEntity<Spending> updateSpending(@PathVariable(value = "id") Long spendingId,
        @RequestBody Spending spendingDetails) throws ResourceNotFoundException {
        return ResponseEntity.ok(spendingService.updateSpending(spendingId, spendingDetails));
    }

    @DeleteMapping("/spendings/{id}")
    public Map<String, Boolean> deleteSpending(@PathVariable(value = "id") Long spendingId)
        throws ResourceNotFoundException {
        return spendingService.deleteSpending(spendingId);
    }
}
