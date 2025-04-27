package market.analyses.parkour.unit.controller;

import market.analyses.parkour.controller.SwitchPriceHistoryController;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.SwitchPriceHistory;
import market.analyses.parkour.service.SwitchPriceHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SwitchPriceHistoryControllerTest {

    @Mock
    SwitchPriceHistoryService service;

    @InjectMocks
    SwitchPriceHistoryController controller;

    @Test
    void getAllPriceChanges_ReturnsValidResponseEntity() {
        var historyList = List.of(
                new SwitchPriceHistory(1, new Switch(1, new Company(1, "Company A"), "Switch 1", 100, 4, 2, true, false, true), 110, LocalDate.now()),
                new SwitchPriceHistory(2, new Switch(2, new Company(2, "Company B"), "Switch 2", 200, 8, 4, false, true, true), 190, LocalDate.now())
        );

        Mockito.doReturn(historyList).when(service).getAllPriceChanges();

        var response = controller.getAllHistory();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(historyList, response.getBody());
    }
}
