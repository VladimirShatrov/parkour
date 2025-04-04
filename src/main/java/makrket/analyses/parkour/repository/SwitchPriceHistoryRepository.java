package makrket.analyses.parkour.repository;

import makrket.analyses.parkour.entity.SwitchPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwitchPriceHistoryRepository extends JpaRepository<SwitchPriceHistory, Long> {
}