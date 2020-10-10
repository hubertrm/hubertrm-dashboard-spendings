package be.hubertrm.dashboard.spending.controller;

import be.hubertrm.dashboard.spending.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.spending.model.Account;
import be.hubertrm.dashboard.spending.service.AccountService;
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
    private AccountService accountService;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") Long accountId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable(value = "id") Long accountId,
        @RequestBody Account accountDetails) throws ResourceNotFoundException {
        return ResponseEntity.ok(accountService.updateAccount(accountId, accountDetails));
    }

    @DeleteMapping("/accounts/{id}")
    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountId)
        throws ResourceNotFoundException {
        return accountService.deleteAccount(accountId);
    }
}
