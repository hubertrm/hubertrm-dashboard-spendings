package be.hubertrm.dashboard.record.repository;

import be.hubertrm.dashboard.record.model.Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long> {

}
