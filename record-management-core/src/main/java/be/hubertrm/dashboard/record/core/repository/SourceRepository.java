package be.hubertrm.dashboard.record.core.repository;

import be.hubertrm.dashboard.record.core.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {

    @Query("select ac from Source ac where ac.name = ?1")
    Optional<Source> findSourceByName(String name);
}
