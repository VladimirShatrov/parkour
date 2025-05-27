package market.analyses.parkour.repository;

import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.entity.SwitchPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwitchPriceHistoryRepository extends JpaRepository<SwitchPriceHistory, Long> {

    List<SwitchPriceHistory> getSwitchPriceHistoriesBySwitchEntityOrderByChangeDateAsc(Switch s);

    @Query("""
    SELECT h 
    FROM SwitchPriceHistory h
    JOIN FETCH h.switchEntity s
    ORDER BY s.id, h.changeDate
    """)
    List<SwitchPriceHistory> findAllWithSwitchOrdered();

}