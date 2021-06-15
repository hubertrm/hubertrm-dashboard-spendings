package be.hubertrm.dashboard.record.core.service;

import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.model.Category;

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
