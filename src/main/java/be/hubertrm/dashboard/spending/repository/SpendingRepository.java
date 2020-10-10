package be.hubertrm.dashboard.spending.repository;

import be.hubertrm.dashboard.spending.model.Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long> {
}
