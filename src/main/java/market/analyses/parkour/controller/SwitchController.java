package market.analyses.parkour.controller;

import market.analyses.parkour.dto.SwitchDTO;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.service.CompanyService;
import market.analyses.parkour.service.SwitchService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/switches")
public class SwitchController {

    private final SwitchService switchService;

    private final CompanyService companyService;

    public SwitchController(SwitchService switchService, CompanyService companyService) {
        this.switchService = switchService;
        this.companyService = companyService;
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
            return ResponseEntity.ok(s);
        }
    }

    @PostMapping
    public ResponseEntity<?> createSwitch(@RequestBody Switch switchEntity,
                                               UriComponentsBuilder uriComponentsBuilder) {
        if (switchEntity.getId() != null && switchService.existsById(switchEntity.getId().longValue())) {
            return ResponseEntity.badRequest().body("Коммутатор с id: " + switchEntity.getId() + " уже существует.");
        }
        Switch newSwitch = switchService.saveSwitch(switchEntity);
        int id = newSwitch.getId();
        return ResponseEntity.created(uriComponentsBuilder
                    .path("switches/{id}")
                    .build(Map.of("id", id)))
                .body(newSwitch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Switch> updateSwitch(@PathVariable Long id, @RequestBody Switch switchDetails) {
        Switch s = switchService.getSwitchById(id);
        if (s == null) {
            return ResponseEntity.notFound().build();
        }
        s.setControllable(switchDetails.getControllable());
        s.setCompany(switchDetails.getCompany());
        s.setPrice(switchDetails.getPrice());
        s.setPoePorts(switchDetails.getPoePorts());
        s.setTitle(switchDetails.getTitle());
        s.setUps(switchDetails.getUps());
        s.setSfpPorts(switchDetails.getSfpPorts());
        s.setAvailable(switchDetails.getAvailable());

        return ResponseEntity.ok(switchService.saveSwitch(s));
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
    public ResponseEntity<?> getSwitchesByCompanyId(@PathVariable Long companyId) {
        if (!companyService.existsById(companyId)) {
            return ResponseEntity.badRequest().body("Компания с id: " + companyId + " не найдена.");
        }

        List<Switch> switches = switchService.getSwitchesByCompany(companyId);
        if (switches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(switches);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SwitchDTO>> getAllSwitchesDTO() {
        var switches = switchService.getAllSwitchesDTO();
        if (switches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(switches);
    }
}
