package be.hubertrm.dashboard.record.rest.manager;

import be.hubertrm.dashboard.record.rest.dto.CategoryDto;
import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.mapper.CategoryMapper;
import be.hubertrm.dashboard.record.rest.service.CategoryService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class CategoryBusinessManager {

    @Resource
    private CategoryService categoryService;

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toDtoList(categoryService.getAllCategories());
    }

    public CategoryDto getCategoryById(Long id) throws ResourceNotFoundException {
        return categoryMapper.toDto(categoryService.getCategoryById(id));
    }

    public CategoryDto getCategoryByName(String name) throws ResourceNotFoundException {
        return categoryMapper.toDto(categoryService.getCategoryByName(name));
    }

    public CategoryDto createOrUpdate(CategoryDto categoryDto) throws ResourceNotFoundException {
        return categoryMapper.toDto(categoryService.createOrUpdate(categoryMapper.toEntity(categoryDto)));
    }

    public CategoryDto createOrUpdate(CategoryDto categoryDto, Long id) throws ResourceNotFoundException {
        return categoryMapper.toDto(categoryService.updateCategory(id, categoryMapper.toEntity(categoryDto)));
    }
    
    public Map<String, Boolean> deleteCategoryById(Long id) throws ResourceNotFoundException {
        return categoryService.deleteCategoryById(id);
    }
}
