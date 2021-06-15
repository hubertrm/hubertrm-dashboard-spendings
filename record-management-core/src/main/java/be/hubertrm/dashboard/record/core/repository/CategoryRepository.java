package be.hubertrm.dashboard.record.core.repository;

import be.hubertrm.dashboard.record.core.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select ca from Category ca where ca.name = ?1")
    Optional<Category> findCategoryByName(String name);
}
