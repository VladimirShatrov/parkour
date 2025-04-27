package market.analyses.parkour.unit.controller;

import market.analyses.parkour.controller.SwitchController;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.service.SwitchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SwitchControllerTest {

    @Mock
    SwitchService service;

    @InjectMocks
    SwitchController controller;


    @Test
    void getAllSwitches_ReturnsValidResponseEntity() {
        var switches = List.of(
                new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true),
                new Switch(2, new Company(2, "company2"), "switch2", 15, 4, 0, false, true, true)
        );

        Mockito.doReturn(switches).when(service).getAllSwitches();

        var returnedSwitches = controller.getAllSwitches();

        assertNotNull(returnedSwitches);
        assertEquals(HttpStatus.OK, returnedSwitches.getStatusCode());
        assertEquals(switches, returnedSwitches.getBody());
    }
}
