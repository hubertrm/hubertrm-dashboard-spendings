package be.hubertrm.dashboard.record.controller;

import be.hubertrm.dashboard.record.dto.CategoryDto;
import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.manager.CategoryBusinessManager;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "id") Long categoryId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(categoryBusinessManager.getCategoryById(categoryId));
    }

    @PostMapping("/categories")
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryBusinessManager.createOrUpdate(categoryDto);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(value = "id") Long categoryId,
        @RequestBody CategoryDto categoryDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(categoryBusinessManager.createOrUpdate(categoryDto));
    }

    @DeleteMapping("/categories/{id}")
    public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") Long categoryId)
        throws ResourceNotFoundException {
        return categoryBusinessManager.deleteCategoryById(categoryId);
    }
}
