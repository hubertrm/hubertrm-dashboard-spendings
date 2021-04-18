package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
@Service
public class CategoryGenericService extends GenericService<Category> {

    public CategoryGenericService(JpaRepository<Category, Long> jpa) {
        super(jpa);
    }
}
