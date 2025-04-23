package market.analyses.parkour.controller;

import market.analyses.parkour.entity.SwitchPriceHistory;
import market.analyses.parkour.repository.SwitchPriceHistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/switch-price-history")
public class SwitchPriceHistoryController {

    private final SwitchPriceHistoryRepository historyRepository;

    public SwitchPriceHistoryController(SwitchPriceHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping
    public ResponseEntity<List<SwitchPriceHistory>> getAllHistory() {
        return ResponseEntity.ok(historyRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SwitchPriceHistory> getHistoryById(@PathVariable Long id) {
        Optional<SwitchPriceHistory> history = historyRepository.findById(id);
        return history.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SwitchPriceHistory> createHistory(@RequestBody SwitchPriceHistory history) {
        SwitchPriceHistory savedHistory = historyRepository.save(history);
        return ResponseEntity.ok(savedHistory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SwitchPriceHistory> updateHistory(@PathVariable Long id, @RequestBody SwitchPriceHistory historyDetails) {
        return historyRepository.findById(id).map(history -> {
            history.setNewPrice(historyDetails.getNewPrice());
            history.setChangeDate(historyDetails.getChangeDate());
            history.setSwitchEntity(historyDetails.getSwitchEntity());
            return ResponseEntity.ok(historyRepository.save(history));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        if (!historyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        historyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}