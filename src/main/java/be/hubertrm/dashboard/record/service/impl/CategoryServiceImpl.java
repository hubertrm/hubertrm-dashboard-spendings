package be.hubertrm.dashboard.record.service.impl;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Category;
import be.hubertrm.dashboard.record.repository.CategoryRepository;
import be.hubertrm.dashboard.record.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found for this id :: ";
    private static final String CATEGORY_NOT_FOUND_BY_NAME_MESSAGE = "Category not found for this name :: ";

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId)
            throws ResourceNotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE + categoryId));
    }

    public Category getCategoryByName(String name)
            throws ResourceNotFoundException {
        return categoryRepository.findCategoryByName(name).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_BY_NAME_MESSAGE + name));
    }

    public Category createOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, Category categoryDetails)
            throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE + categoryId));

        category.setName(categoryDetails.getName());
        category.setCreationDate(categoryDetails.getCreationDate());

        return categoryRepository.save(category);
    }

    @Override
    public Map<String, Boolean> deleteCategoryById(Long categoryId) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE + categoryId));

        categoryRepository.delete(category);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
