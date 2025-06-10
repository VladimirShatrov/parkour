package market.analyses.parkour.unit.service;

import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.repository.SwitchRepository;
import market.analyses.parkour.service.SwitchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SwitchServiceTest {

    @Mock
    private SwitchRepository switchRepository;

    @InjectMocks
    private SwitchService switchService;

    @Test
    public void getAllSwitches_ReturnsValidCollection() {
        var switches = List.of(
                new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true),
                new Switch(2, new Company(2, "company2"), "switch2", 15, 4, 0, false, true, true)
        );

        Mockito.doReturn(switches).when(switchRepository).findAll();

        var response = switchService.getAllSwitches();

        assertNotNull(response);
        assertEquals(switches, response);
        verify(switchRepository, times(1)).findAll();
    }

    @Test
    public void getSwitchById_ReturnValidEntity() {
        var s = new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);
        Optional<Switch> optionalSwitch = Optional.of(s);
        Mockito.doReturn(optionalSwitch).when(switchRepository).findById(1L);
        var response = switchService.getSwitchById(1L);

        assertNotNull(response);
        assertEquals(s, response);
        verify(switchRepository, times(1)).findById(1L);
    }
}
