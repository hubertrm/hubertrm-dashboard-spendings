package be.hubertrm.dashboard.record.service.impl;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Spending;
import be.hubertrm.dashboard.record.repository.SpendingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class SpendingService {

    @Resource
    private SpendingRepository spendingRepository;

    private static final String SPENDING_NOT_FOUND_MESSAGE = "Spending not found for this id :: ";

    public List<Spending> getAllSpendings() {
        return spendingRepository.findAll();
    }

    public Spending getSpendingById(Long spendingId)
            throws ResourceNotFoundException {
        return spendingRepository.findById(spendingId).orElseThrow(
                () -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + spendingId)
        );
    }

    public Spending createSpending(Spending spending) {
        return spendingRepository.save(spending);
    }

    public Spending findSpending(Long spendingId)
            throws ResourceNotFoundException {
        return spendingRepository.findById(spendingId)
                .orElseThrow(() -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + spendingId));
    }

    public Spending saveSpending(Spending spending) {
        return spendingRepository.save(spending);
    }

    public Spending updateSpending(Long spendingId, Spending spendingDetails)
            throws ResourceNotFoundException {
        Spending spending = spendingRepository.findById(spendingId)
                .orElseThrow(() -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + spendingId));

        spending.setAccountId(spendingDetails.getAccountId());
        spending.setAmount(spendingDetails.getAmount());
        spending.setCategoryId(spendingDetails.getCategoryId());
        spending.setNote(spendingDetails.getNote());
        spending.setSpendingDate(spendingDetails.getSpendingDate());

        return spendingRepository.save(spending);
    }

    public Map<String, Boolean> deleteSpending(Long spendingId) throws ResourceNotFoundException {
        Spending spending = spendingRepository.findById(spendingId)
                .orElseThrow(() -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + spendingId));

        spendingRepository.delete(spending);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Map<String, Boolean> deleteSpendings(List<Long> spendingIds) {
        List<Spending> spendingList = spendingRepository.findAllById(spendingIds);

        spendingRepository.deleteAll(spendingList);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
