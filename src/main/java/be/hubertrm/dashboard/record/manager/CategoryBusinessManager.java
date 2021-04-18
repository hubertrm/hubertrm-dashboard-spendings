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

    public CategoryDto createOrUpdate(CategoryDto categoryDto, Long id) {
        categoryDto.setId(id);
        return createOrUpdate(categoryDto);
    }
    
    public Map<String, Boolean> deleteCategoryById(Long id) throws ResourceNotFoundException {
        return categoryService.deleteCategoryById(id);
    }
}
