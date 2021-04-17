package be.hubertrm.dashboard.record.repository;

import be.hubertrm.dashboard.record.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}