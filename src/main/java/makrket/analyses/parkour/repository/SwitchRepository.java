package makrket.analyses.parkour.repository;

import makrket.analyses.parkour.entity.Switch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwitchRepository extends JpaRepository<Switch, Integer> {
    List<Switch> findByCompanyId(Integer companyId);
}