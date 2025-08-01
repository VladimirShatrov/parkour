package market.analyses.parkour.service;

import market.analyses.parkour.dto.PriceDTO;
import market.analyses.parkour.dto.SwitchPriceDTO;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.entity.SwitchPriceHistory;
import market.analyses.parkour.repository.SwitchPriceHistoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SwitchPriceHistoryService {
    private final SwitchPriceHistoryRepository repository;

    public SwitchPriceHistoryService(SwitchPriceHistoryRepository repository) {
        this.repository = repository;
    }

    public List<SwitchPriceHistory> getAllPriceChanges() {
        return repository.findAll();
    }

    @CacheEvict(value = {"switchPricesHistories"}, allEntries = true)
    public SwitchPriceHistory savePriceChange(SwitchPriceHistory history) {
        return repository.save(history);
    }

    public SwitchPriceHistory getHistoryById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("History with id: " + id + " not found"));
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @CacheEvict(value = {"switchPricesHistories"}, allEntries = true)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Cacheable("switchPricesHistories")
    public List<SwitchPriceDTO> getHistoryBySwitch() {
        List<SwitchPriceHistory> histories = repository.findAllWithSwitchOrdered();

        Map<Switch, List<SwitchPriceHistory>> grouped = histories.stream()
                .collect(Collectors.groupingBy(SwitchPriceHistory::getSwitchEntity));

        return grouped.entrySet().stream()
                .map(entry -> {
                    Switch s = entry.getKey();
                    List<PriceDTO> prices = entry.getValue().stream()
                            .map(h -> new PriceDTO(h.getNewPrice(), h.getChangeDate()))
                            .toList();

                    return new SwitchPriceDTO(
                            s.getId().longValue(),
                            s.getTitle(),
                            prices
                    );
                }).toList();
    }
}

