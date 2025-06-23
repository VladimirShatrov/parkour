package market.analyses.parkour.unit.service;

import market.analyses.parkour.dto.SwitchAttribute;
import market.analyses.parkour.dto.SwitchDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwitchServiceTest {

    @Mock
    private SwitchRepository switchRepository;

    @InjectMocks
    private SwitchService switchService;

    @Test
    public void getAllSwitches_ReturnsValidList() {
        var switches = List.of(
                new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true),
                new Switch(2, new Company(2, "company2"), "switch2", 15, 4, 0, false, true, true)
        );

        Mockito.doReturn(switches).when(switchRepository).findAll();

        var response = switchService.getAllSwitches();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
        assertEquals(switches, response);
        verify(switchRepository, times(1)).findAll();
    }

    @Test
    public void getSwitchById_FindSwitch_ReturnsValidEntity() {
        var s = new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);
        Optional<Switch> optionalSwitch = Optional.of(s);
        Mockito.doReturn(optionalSwitch).when(switchRepository).findById(1L);
        var response = switchService.getSwitchById(1L);

        assertNotNull(response);
        assertEquals(s, response);
        verify(switchRepository, times(1)).findById(1L);
    }

    @Test
    public void getSwitchById_DontFindSwitch_ReturnsNull() {
        Mockito.doReturn(Optional.empty()).when(switchRepository).findById(1L);
        var response = switchService.getSwitchById(1L);

        assertNull(response);
        verify(switchRepository, times(1)).findById(1L);
    }

    @Test
    public void saveSwitch_ReturnsValidEntity() {
        var s = new Switch(null, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);
        var returnSwitch = new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);
        Mockito.doReturn(returnSwitch).when(switchRepository).save(s);

        var response = switchService.saveSwitch(s);

        assertNotNull(response);
        assertEquals(returnSwitch, response);
        verify(switchRepository, times(1)).save(s);
    }

    @Test
    public void deleteSwitch_ValidTimesCallRepository() {
        switchService.deleteSwitch(1L);
        verify(switchRepository, times(1)).deleteById(1L);
    }

    @Test
    public void existById_ReturnsValidBoolean() {
        doReturn(true).when(switchRepository).existsById(1L);

        var response = switchRepository.existsById(1L);
        assertTrue(response);
        verify(switchRepository, times(1)).existsById(1L);
    }

    @Test
    public void getSwitchesByCompany_CompanyHasSwitches_ReturnsValidList() {
        var company = new Company(1, "company1");
        var switches = List.of(
                new Switch(1, company, "switch1", 10, 2, 2, true, false, true),
                new Switch(2, company, "switch2", 15, 4, 0, false, true, true)
        );

        doReturn(switches).when(switchRepository).findByCompanyId(1L);
        var response = switchService.getSwitchesByCompany(1L);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
        assertEquals(switches, response);

        verify(switchRepository, times(1)).findByCompanyId(1L);
    }

    @Test
    public void getAllSwitchesDTO_ReturnsValidList() {
        var switchesDto = List.of(
                new SwitchDTO(1, "company1", 10, "switch1", new SwitchAttribute(2, 2, true, true, false)),
                new SwitchDTO(2, "company2", 15, "switch2", new SwitchAttribute(4, 4, false, true, true))
        );

        var switches = List.of(
                new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, true, false),
                new Switch(2, new Company(2, "company2"), "switch2", 15, 4, 4, false, true, true)
        );

        doReturn(switches).when(switchRepository).findAll();
        var response = switchService.getAllSwitchesDTO();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
        assertEquals(switchesDto, response);

        verify(switchRepository, times(1)).findAll();
    }
}
