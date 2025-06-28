package market.analyses.parkour.service;

import market.analyses.parkour.dto.SwitchAttribute;
import market.analyses.parkour.dto.SwitchDTO;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.repository.SwitchRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SwitchService {
    private final SwitchRepository switchRepository;

    public SwitchService(SwitchRepository switchRepository) {
        this.switchRepository = switchRepository;
    }

    @Cacheable("switches")
    public List<Switch> getAllSwitches() {
        return switchRepository.findAll();
    }

    public Switch getSwitchById(Long id) {
        return switchRepository.findById(id).orElse(null);
    }

    @Transactional
    @CacheEvict(value = {"switches", "switchesData"}, allEntries = true)
    public Switch saveSwitch(Switch networkSwitch) {
        return switchRepository.save(networkSwitch);
    }

    @Transactional
    @CacheEvict(value = {"switches", "switchesData"}, allEntries = true)
    public void deleteSwitch(Long id) {
        switchRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return switchRepository.existsById(id);
    }

    public List<Switch> getSwitchesByCompany(Long id) {
        return switchRepository.findByCompanyId(id);
    }

    @Cacheable("switchesData")
    public List<SwitchDTO> getAllSwitchesDTO() {
        var switches = switchRepository.findAll();

        List<SwitchDTO> result = new ArrayList<>();
        for (Switch s : switches) {
            SwitchAttribute attributes = new SwitchAttribute(
                    s.getPoePorts(),
                    s.getSfpPorts(),
                    s.getUps(),
                    s.getControllable(),
                    s.getAvailable()
            );

            result.add(new SwitchDTO(
                    s.getId(),
                    s.getCompany().getNameCompany(),
                    s.getPrice(),
                    s.getTitle(),
                    attributes
            ));
        }
        return result;
    }
}