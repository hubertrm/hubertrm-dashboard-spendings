package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.dto.CategoryDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.mapper.CategoryMapper;
import be.hubertrm.dashboard.record.service.CategoryService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class CategoryBusinessManager {

    @Resource
    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    public CategoryBusinessManager(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toDtoList(categoryService.getAllCategories());
    }

    public CategoryDto getCategoryById(Long id) throws ResourceNotFoundException {
        return categoryMapper.toDto(categoryService.getCategoryById(id));
    }

    public CategoryDto createOrUpdate(CategoryDto categoryDto) {
        return categoryMapper.toDto(categoryService.createOrUpdate(categoryMapper.toEntity(categoryDto)));
    }
    
    public Map<String, Boolean> deleteCategoryById(Long id) throws ResourceNotFoundException {
        return categoryService.deleteCategoryById(id);
    }

//    public long updateCategory(long categoryId, Category categoryDetails)
//            throws ResourceNotFoundException {
//        Spending spending = spendingService.findSpending(categoryId);
//        spending.setSpendingDate(new Timestamp(categoryDetails.getPayDate().getTime()));
//        spending.setAmount(categoryDetails.getAmount());
//        spending.setNote(categoryDetails.getComments());
//        spending.setCategoryId(categoryService.getCategoryByName(categoryDetails.getCategoryId()).getId());
//        spending.setAccountId(sourceService.getAccountByName(categoryDetails.getSourceId()).getId());
//
//        return spendingService.saveSpending(spending).getId();
//    }
}
