package be.hubertrm.dashboard.record.core.repository;

import be.hubertrm.dashboard.record.core.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    @Query("select org from Organisation org where org.name = ?1")
    Optional<Organisation> findOrganisationByName(String name);
}
