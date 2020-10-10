package be.hubertrm.dashboard.spending.controller;

import be.hubertrm.dashboard.spending.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.spending.model.Category;
import be.hubertrm.dashboard.spending.service.CategoryService;
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
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long categoryId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId,
        @RequestBody Category categoryDetails) throws ResourceNotFoundException {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDetails));
    }

    @DeleteMapping("/categories/{id}")
    public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") Long categoryId)
        throws ResourceNotFoundException {
        return categoryService.deleteCategory(categoryId);
    }
}
