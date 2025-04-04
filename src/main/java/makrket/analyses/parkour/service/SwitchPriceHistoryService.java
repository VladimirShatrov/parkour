package makrket.analyses.parkour.service;

import makrket.analyses.parkour.entity.SwitchPriceHistory;
import makrket.analyses.parkour.repository.SwitchPriceHistoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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
}

