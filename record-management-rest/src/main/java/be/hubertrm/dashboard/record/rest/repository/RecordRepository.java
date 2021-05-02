package be.hubertrm.dashboard.record.rest.repository;

import be.hubertrm.dashboard.record.rest.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}
