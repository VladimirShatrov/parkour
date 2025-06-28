package market.analyses.parkour.jpa;

import market.analyses.parkour.config.BeansConfiguration;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.entity.SwitchPriceHistory;
import market.analyses.parkour.repository.SwitchPriceHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BeansConfiguration.class)
@Testcontainers
public class SwitchPriceHistoryRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    SwitchPriceHistoryRepository repository;

    @Test
    public void findAllWithSwitchOrdered_ReturnsAllHistoriesSorted() {
        Company c = new Company(null, "company1");
        entityManager.persist(c);

        Switch s1 = new Switch(null, c, "switch1", 25, 2, 2, true, true, true);
        Switch s2 = new Switch(null, c, "switch2", 25, 2, 2, true, true, true);
        entityManager.persist(s1);
        entityManager.persist(s2);

        entityManager.persist(new SwitchPriceHistory(null, s1, 30, LocalDate.of(2025, 6, 24)));
        entityManager.persist(new SwitchPriceHistory(null, s2, 30, LocalDate.of(2025, 6, 24)));
        entityManager.persist(new SwitchPriceHistory(null, s1, 25, LocalDate.of(2025, 6, 23)));
        entityManager.persist(new SwitchPriceHistory(null, s2, 25, LocalDate.of(2025, 6, 23)));

        entityManager.flush();

        List<SwitchPriceHistory> result = repository.findAllWithSwitchOrdered();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertEquals("switch1", result.get(0).getSwitchEntity().getTitle());
        assertEquals("switch2", result.get(2).getSwitchEntity().getTitle());
        assertEquals(LocalDate.of(2025, 6, 23), result.get(0).getChangeDate());
        assertEquals(LocalDate.of(2025, 6, 24), result.get(1).getChangeDate());
    }
}
