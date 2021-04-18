package be.hubertrm.dashboard.record.controller;

import be.hubertrm.dashboard.record.dto.CategoryDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.manager.BusinessManager;
import be.hubertrm.dashboard.record.model.Category;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class GenericCategoryController {

    @Resource
    private BusinessManager<CategoryDto, Category> genericCategoryBusinessManager;

    @GetMapping("/genericCategories")
    public List<CategoryDto> getAllCategories() {
        return genericCategoryBusinessManager.getAll();
    }

    @GetMapping("/genericCategory/{id}")
    public CategoryDto getCategoryById(@PathVariable(value = "id") Long categoryId)
            throws ResourceNotFoundException {
        return genericCategoryBusinessManager.getById(categoryId);
    }

    @PostMapping("/genericCategory")
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return genericCategoryBusinessManager.createOrUpdate(categoryDto);
    }

    @PutMapping("/genericCategory/{id}")
    public CategoryDto updateCategory(@PathVariable(value = "id") Long categoryId,
        @RequestBody CategoryDto categoryDto) throws ResourceNotFoundException {
        return genericCategoryBusinessManager.createOrUpdate(categoryDto, categoryId);
    }

    @DeleteMapping("/genericCategory/{id}")
    public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") Long categoryId)
        throws ResourceNotFoundException {
        return genericCategoryBusinessManager.deleteById(categoryId);
    }
}
