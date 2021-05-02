package be.hubertrm.dashboard.record.rest.service.impl;

import be.hubertrm.dashboard.record.rest.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.rest.model.Category;
import be.hubertrm.dashboard.record.rest.repository.CategoryRepository;
import be.hubertrm.dashboard.record.rest.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
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

    @Override
    public Category getCategoryByName(String name)
            throws ResourceNotFoundException {
        return categoryRepository.findCategoryByName(name).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_BY_NAME_MESSAGE + name));
    }

    @Override
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
