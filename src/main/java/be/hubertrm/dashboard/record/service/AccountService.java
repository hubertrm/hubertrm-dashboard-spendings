package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Account;
import be.hubertrm.dashboard.record.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class AccountService {

    @Resource
    private AccountRepository accountRepository;

    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account not found for this id :: ";
    private static final String ACCOUNT_NOT_FOUND_BY_NAME_MESSAGE = "Account not found for this name :: ";

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId)
            throws ResourceNotFoundException {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE + accountId)
        );
    }
    public Account getAccountByName(String name)
            throws ResourceNotFoundException {
        return accountRepository.findAccountByName(name).orElseThrow(
                () -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND_BY_NAME_MESSAGE + name)
        );
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long accountId, Account accountDetails)
            throws ResourceNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE + accountId));

        account.setCreationDate(accountDetails.getCreationDate());
        account.setName(accountDetails.getName());
        account.setOrganisationId(accountDetails.getOrganisationId());

        return accountRepository.save(account);
    }

    public Map<String, Boolean> deleteAccount(Long accountId) throws ResourceNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE + accountId));

        accountRepository.delete(account);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
