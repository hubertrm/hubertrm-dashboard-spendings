package be.hubertrm.dashboard.spending.repository;

import be.hubertrm.dashboard.spending.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
}
