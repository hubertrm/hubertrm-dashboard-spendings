package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.dto.RecordDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.mapper.RecordMapper;
import be.hubertrm.dashboard.record.model.Record;
import be.hubertrm.dashboard.record.service.CategoryService;
import be.hubertrm.dashboard.record.service.SourceService;
import be.hubertrm.dashboard.record.service.RecordService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RecordBusinessManager {

    @Resource
    private final RecordService recordService;
    @Resource
    private final CategoryService categoryService;
    @Resource
    private final SourceService sourceService;

    private final RecordMapper recordMapper = Mappers.getMapper(RecordMapper.class);

    public RecordBusinessManager(RecordService recordService, CategoryService categoryService,
                                 SourceService sourceService) {
        this.recordService = recordService;
        this.categoryService = categoryService;
        this.sourceService = sourceService;
    }

    public List<RecordDto> getAllRecords() {
        return recordMapper.toDtoList(recordService.getAllRecords());
    }

    public RecordDto createOrUpdate(RecordDto recordDto) {
        return recordMapper.toDto(recordService.createOrUpdate(recordMapper.toEntity(recordDto)));
    }

    public void deleteRecordById(Long id) {
        recordService.deleteRecordById(id);
    }

//    public long updateRecord(long recordId, Record recordDetails)
//            throws ResourceNotFoundException {
//        Spending spending = spendingService.findSpending(recordId);
//        spending.setSpendingDate(new Timestamp(recordDetails.getPayDate().getTime()));
//        spending.setAmount(recordDetails.getAmount());
//        spending.setNote(recordDetails.getComments());
//        spending.setCategoryId(categoryService.getCategoryByName(recordDetails.getCategoryId()).getId());
//        spending.setAccountId(sourceService.getAccountByName(recordDetails.getSourceId()).getId());
//
//        return spendingService.saveSpending(spending).getId();
//    }
}
