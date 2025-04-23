package market.analyses.parkour.service;

import market.analyses.parkour.entity.SwitchPriceHistory;
import market.analyses.parkour.repository.SwitchPriceHistoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SwitchPriceHistoryService {
    private final SwitchPriceHistoryRepository repository;

    public SwitchPriceHistoryService(SwitchPriceHistoryRepository repository) {
        this.repository = repository;
    }

    public List<SwitchPriceHistory> getAllPriceChanges() {
        return repository.findAll();
    }

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

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

