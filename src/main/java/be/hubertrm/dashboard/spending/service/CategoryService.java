package be.hubertrm.dashboard.spending.service;

import be.hubertrm.dashboard.spending.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.spending.model.Category;
import be.hubertrm.dashboard.spending.repository.CategoryRepository;
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

    private final String SPENDING_NOT_FOUND_MESSAGE = "Category not found for this id :: ";

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long organisationId)
            throws ResourceNotFoundException {
        return categoryRepository.findById(organisationId).orElseThrow(
                () -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + organisationId)
        );
    }

    public Category createCategory(Category organisation) {
        return categoryRepository.save(organisation);
    }

    public Category updateCategory(Long organisationId, Category organisationDetails)
            throws ResourceNotFoundException {
        Category organisation = categoryRepository.findById(organisationId)
                .orElseThrow(() -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + organisationId));

        organisation.setName(organisationDetails.getName());
        organisation.setCreationDate(organisationDetails.getCreationDate());

        return categoryRepository.save(organisation);
    }

    public Map<String, Boolean> deleteCategory(Long organisationId) throws ResourceNotFoundException {
        Category organisation = categoryRepository.findById(organisationId)
                .orElseThrow(() -> new ResourceNotFoundException(SPENDING_NOT_FOUND_MESSAGE + organisationId));

        categoryRepository.delete(organisation);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
