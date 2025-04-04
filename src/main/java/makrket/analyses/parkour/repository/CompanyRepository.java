package makrket.analyses.parkour.repository;

import makrket.analyses.parkour.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
