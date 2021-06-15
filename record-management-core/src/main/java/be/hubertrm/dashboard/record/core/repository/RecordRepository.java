package be.hubertrm.dashboard.record.core.repository;

import be.hubertrm.dashboard.record.core.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}
