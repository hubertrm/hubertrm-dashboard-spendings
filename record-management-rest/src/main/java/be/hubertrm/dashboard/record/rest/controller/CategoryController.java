package be.hubertrm.dashboard.record.rest.controller;

import be.hubertrm.dashboard.record.rest.dto.CategoryDto;
import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.manager.CategoryBusinessManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class CategoryController {

    @Resource
    private CategoryBusinessManager categoryBusinessManager;

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryBusinessManager.getAllCategories();
    }

    @GetMapping("/category/{id}")
    public CategoryDto getCategoryById(@PathVariable(value = "id") Long categoryId)
            throws ResourceNotFoundException {
        return categoryBusinessManager.getCategoryById(categoryId);
    }

    @PostMapping("/category")
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) throws ResourceNotFoundException {
        return categoryBusinessManager.createOrUpdate(categoryDto);
    }

    @PutMapping("/category/{id}")
    public CategoryDto updateCategory(@PathVariable(value = "id") Long categoryId,
        @RequestBody CategoryDto categoryDto) throws ResourceNotFoundException {
        return categoryBusinessManager.createOrUpdate(categoryDto, categoryId);
    }

    @DeleteMapping("/category/{id}")
    public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") Long categoryId)
        throws ResourceNotFoundException {
        return categoryBusinessManager.deleteCategoryById(categoryId);
    }
}
