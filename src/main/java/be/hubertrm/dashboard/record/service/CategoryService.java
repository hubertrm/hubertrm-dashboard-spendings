package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.model.Category;
import be.hubertrm.dashboard.record.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    private final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found for this id :: ";
    private final String CATEGORY_NOT_FOUND_BY_NAME_MESSAGE = "Category not found for this name :: ";

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId)
            throws ResourceNotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE + categoryId)
        );
    }

    public Category getCategoryByName(String name)
            throws ResourceNotFoundException {
        return categoryRepository.findCategoryByName(name).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_BY_NAME_MESSAGE + name)
        );
    }

    public Category createCategory(Category organisation) {
        return categoryRepository.save(organisation);
    }

    public Category updateCategory(Long categoryId, Category organisationDetails)
            throws ResourceNotFoundException {
        Category organisation = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE + categoryId));

        organisation.setName(organisationDetails.getName());
        organisation.setCreationDate(organisationDetails.getCreationDate());

        return categoryRepository.save(organisation);
    }

    public Map<String, Boolean> deleteCategory(Long categoryId) throws ResourceNotFoundException {
        Category organisation = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE + categoryId));

        categoryRepository.delete(organisation);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
