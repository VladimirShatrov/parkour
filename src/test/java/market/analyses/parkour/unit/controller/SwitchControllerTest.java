package market.analyses.parkour.unit.controller;

import market.analyses.parkour.controller.SwitchController;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.service.SwitchService;
import market.analyses.parkour.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SwitchControllerTest {

    @Mock
    SwitchService switchService;

    @Mock
    CompanyService companyService;

    @InjectMocks
    SwitchController switchController;

    @Test
    void getAllSwitches_ReturnsValidResponseEntity() {
        var switches = List.of(
                new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true),
                new Switch(2, new Company(2, "company2"), "switch2", 15, 4, 0, false, true, true)
        );

        Mockito.doReturn(switches).when(switchService).getAllSwitches();

        var response = switchController.getAllSwitches();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(switches, response.getBody());
    }

    @Test
    void createSwitch_ReturnsCreatedResponse() {
        var switchEntity = new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);

        Mockito.doReturn(switchEntity).when(switchService).saveSwitch(switchEntity);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        var response = switchController.createSwitch(switchEntity, uriComponentsBuilder);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getSwitchById_ReturnsSwitch() {
        var switchEntity = new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);

        Mockito.doReturn(switchEntity).when(switchService).getSwitchById(1L);

        var response = switchController.getSwitchById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(switchEntity, response.getBody());
    }

    @Test
    void getSwitchById_ReturnsNotFound() {
        Mockito.doReturn(null).when(switchService).getSwitchById(999L);

        var response = switchController.getSwitchById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateSwitch_ReturnsUpdatedSwitch() {
        var company = new Company(1, "Company A");
        var switchEntity = new Switch(1, company, "Switch A", 10, 2, 2, true, false, true);
        var updatedSwitch = new Switch(1, company, "Switch B", 15, 4, 0, false, true, true);

        Mockito.doReturn(switchEntity).when(switchService).getSwitchById(1L);
        Mockito.doReturn(updatedSwitch).when(switchService).saveSwitch(Mockito.any(Switch.class));

        var response = switchController.updateSwitch(1L, updatedSwitch);

        Switch capturedSwitch = response.getBody();
        assertNotNull(capturedSwitch);
        assertEquals(updatedSwitch.getId(), capturedSwitch.getId());
        assertEquals(updatedSwitch.getTitle(), capturedSwitch.getTitle());
        assertEquals(updatedSwitch.getPrice(), capturedSwitch.getPrice());
        assertEquals(updatedSwitch.getPoePorts(), capturedSwitch.getPoePorts());
        assertEquals(updatedSwitch.getSfpPorts(), capturedSwitch.getSfpPorts());
        assertEquals(updatedSwitch.getUps(), capturedSwitch.getUps());
        assertEquals(updatedSwitch.getControllable(), capturedSwitch.getControllable());
        assertEquals(updatedSwitch.getAvailable(), capturedSwitch.getAvailable());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateSwitch_ReturnsNotFound() {
        var updatedSwitch = new Switch(999, new Company(1, "company1"), "switch1_updated", 15, 4, 0, true, true, false);

        Mockito.doReturn(null).when(switchService).getSwitchById(999L);

        var response = switchController.updateSwitch(999L, updatedSwitch);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteSwitch_ReturnsNoContent() {
        Mockito.doReturn(true).when(switchService).existsById(1L);

        var response = switchController.deleteSwitch(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteSwitch_ReturnsNotFound() {
        Mockito.doReturn(false).when(switchService).existsById(999L);

        var response = switchController.deleteSwitch(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getSwitchesByCompanyId_ReturnsSwitches() {
        var switches = List.of(
                new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true),
                new Switch(2, new Company(1, "company1"), "switch2", 15, 4, 0, false, true, true)
        );

        Mockito.doReturn(true).when(companyService).existsById(1L);
        Mockito.doReturn(switches).when(switchService).getSwitchesByCompany(1L);

        var response = switchController.getSwitchesByCompanyId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(switches, response.getBody());
    }

    @Test
    void getSwitchesByCompanyId_ReturnsBadRequest() {
        Mockito.doReturn(false).when(companyService).existsById(999L);

        var response = switchController.getSwitchesByCompanyId(999L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
