package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Category;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long categoryId) throws ResourceNotFoundException;

    Category getCategoryByName(String name) throws ResourceNotFoundException;

    Category createOrUpdate(Category category);

    Category updateCategory(Long categoryId, Category categoryDetails) throws ResourceNotFoundException;

    Map<String, Boolean> deleteCategoryById(Long categoryId) throws ResourceNotFoundException;
}
