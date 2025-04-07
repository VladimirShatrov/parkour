package makrket.analyses.parkour.controller;

import makrket.analyses.parkour.entity.Switch;
import makrket.analyses.parkour.repository.SwitchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/switches")
public class SwitchController {

    private final SwitchRepository switchRepository;

    public SwitchController(SwitchRepository switchRepository) {
        this.switchRepository = switchRepository;
    }

    @GetMapping
    public ResponseEntity<List<Switch>> getAllSwitches() {
        return ResponseEntity.ok(switchRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Switch> getSwitchById(@PathVariable Long id) {
        return switchRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Switch> createSwitch(@RequestBody Switch switchEntity) {
        return ResponseEntity.ok(switchRepository.save(switchEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Switch> updateSwitch(@PathVariable Long id, @RequestBody Switch switchDetails) {
        return switchRepository.findById(id).map(switchEntity -> {
            switchEntity.setTitle(switchDetails.getTitle());
            switchEntity.setPrice(switchDetails.getPrice());
            switchEntity.setPoePorts(switchDetails.getPoePorts());
            switchEntity.setSfpPorts(switchDetails.getSfpPorts());
            switchEntity.setUps(switchDetails.getUps());
            switchEntity.setControllable(switchDetails.getControllable());
            switchEntity.setCompany(switchDetails.getCompany());
            return ResponseEntity.ok(switchRepository.save(switchEntity));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSwitch(@PathVariable Long id) {
        if (!switchRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        switchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Switch>> getSwitchesByCompanyId(@PathVariable Long companyId) {
        List<Switch> switches = switchRepository.findByCompanyId(companyId);
        if (switches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(switches);
    }
}
