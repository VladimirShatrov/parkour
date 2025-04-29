package market.analyses.parkour.controller;

import market.analyses.parkour.entity.SwitchPriceHistory;
import market.analyses.parkour.repository.SwitchPriceHistoryRepository;
import market.analyses.parkour.service.SwitchPriceHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/switch-price-history")
public class SwitchPriceHistoryController {

    private final SwitchPriceHistoryService historyService;

    public SwitchPriceHistoryController(SwitchPriceHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public ResponseEntity<List<SwitchPriceHistory>> getAllHistory() {
        return ResponseEntity.ok(historyService.getAllPriceChanges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SwitchPriceHistory> getHistoryById(@PathVariable Long id) {
        try {
            SwitchPriceHistory history = historyService.getHistoryById(id);
            return ResponseEntity.ok(history);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createHistory(@RequestBody SwitchPriceHistory history,
                                                            UriComponentsBuilder uriComponentsBuilder) {
        if (history.getId() != null && historyService.existsById(history.getId().longValue())) {
            return ResponseEntity.badRequest().body("Запись об изменении цены с id: " + history.getId() + " уже существует");
        }
        SwitchPriceHistory newHistory = historyService.savePriceChange(history);
        return ResponseEntity.created(uriComponentsBuilder
                    .path("/switch-price-history/{id}")
                    .build(Map.of("id", newHistory.getId())))
                .body(newHistory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SwitchPriceHistory> updateHistory(@PathVariable Long id, @RequestBody SwitchPriceHistory historyDetails) {
        try {
            SwitchPriceHistory history = historyService.getHistoryById(id);

            history.setSwitchEntity(historyDetails.getSwitchEntity());
            history.setNewPrice(historyDetails.getNewPrice());
            history.setChangeDate(historyDetails.getChangeDate());

            return ResponseEntity.ok(historyService.savePriceChange(history));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        if (!historyService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        historyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}