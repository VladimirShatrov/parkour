package makrket.analyses.parkour.service;

import makrket.analyses.parkour.entity.Switch;
import makrket.analyses.parkour.repository.SwitchRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SwitchService {
    private final SwitchRepository switchRepository;

    public SwitchService(SwitchRepository switchRepository) {
        this.switchRepository = switchRepository;
    }

    public List<Switch> getAllSwitches() {
        return switchRepository.findAll();
    }

    public Switch getSwitchById(Long id) {
        return switchRepository.findById(id).orElse(null);
    }

    public Switch saveSwitch(Switch networkSwitch) {
        return switchRepository.save(networkSwitch);
    }

    public void deleteSwitch(Long id) {
        switchRepository.deleteById(id);
    }
}

