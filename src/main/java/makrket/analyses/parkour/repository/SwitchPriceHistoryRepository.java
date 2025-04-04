package makrket.analyses.parkour.repository;

import makrket.analyses.parkour.entity.SwitchPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SwitchPriceHistoryRepository extends JpaRepository<SwitchPriceHistory, Long> {
}