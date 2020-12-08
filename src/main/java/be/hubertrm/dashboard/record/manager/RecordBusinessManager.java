package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Record;
import be.hubertrm.dashboard.record.model.Spending;
import be.hubertrm.dashboard.record.service.AccountService;
import be.hubertrm.dashboard.record.service.CategoryService;
import be.hubertrm.dashboard.record.service.RecordService;
import be.hubertrm.dashboard.record.service.SpendingService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Component
public class RecordBusinessManager {

    @Resource
    private RecordService recordService;
    @Resource
    private SpendingService spendingService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private AccountService accountService;

    public RecordBusinessManager(RecordService recordService, SpendingService spendingService, CategoryService categoryService, AccountService accountService) {
        this.recordService = recordService;
        this.spendingService = spendingService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    public List<Record> getAllRecords() {
        return recordService.getAllRecords();
    }

    public long createRecord(Record record) throws ResourceNotFoundException {
        Spending spending = new Spending();
        spending.setSpendingDate(new Timestamp(record.getSpendingDate().getTime()));
        spending.setAmount(record.getAmount());
        spending.setNote(record.getNote());
        spending.setCategoryId(categoryService.getCategoryByName(record.getCategory()).getId());
        spending.setAccountId(accountService.getAccountByName(record.getAccount()).getId());

        return spendingService.createSpending(spending).getId();
    }

    public long updateRecord(long recordId, Record recordDetails)
            throws ResourceNotFoundException {
        Spending spending = spendingService.findSpending(recordId);
        spending.setSpendingDate(new Timestamp(recordDetails.getSpendingDate().getTime()));
        spending.setAmount(recordDetails.getAmount());
        spending.setNote(recordDetails.getNote());
        spending.setCategoryId(categoryService.getCategoryByName(recordDetails.getCategory()).getId());
        spending.setAccountId(accountService.getAccountByName(recordDetails.getAccount()).getId());

        return spendingService.saveSpending(spending).getId();
    }
}
