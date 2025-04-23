package market.analyses.parkour.controller;

import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.repository.SwitchRepository;
import market.analyses.parkour.service.SwitchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/switches")
public class SwitchController {

    private final SwitchService switchService;

    public SwitchController(SwitchService switchService) {
        this.switchService = switchService;
    }

    @GetMapping
    public ResponseEntity<List<Switch>> getAllSwitches() {
        return ResponseEntity.ok(switchService.getAllSwitches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Switch> getSwitchById(@PathVariable Long id) {
        Switch s = switchService.getSwitchById(id);
        if (s == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(s);
        }
    }

    @PostMapping
    public ResponseEntity<Switch> createSwitch(@RequestBody Switch switchEntity) {
        return ResponseEntity.ok(switchService.saveSwitch(switchEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Switch> updateSwitch(@PathVariable Long id, @RequestBody Switch switchDetails) {
        Switch s = switchService.getSwitchById(id);

        s.setControllable(switchDetails.getControllable());
        s.setCompany(switchDetails.getCompany());
        s.setPrice(switchDetails.getPrice());
        s.setPoePorts(switchDetails.getPoePorts());
        s.setTitle(switchDetails.getTitle());
        s.setUps(switchDetails.getUps());
        s.setSfpPorts(switchDetails.getSfpPorts());

        switchService.saveSwitch(s);
        return ResponseEntity.ok(s);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSwitch(@PathVariable Long id) {
        if (!switchService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        switchService.deleteSwitch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Switch>> getSwitchesByCompanyId(@PathVariable Long companyId) {
        List<Switch> switches = switchService.getSwitchesByCompany(companyId);
        if (switches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(switches);
    }
}
