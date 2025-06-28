package market.analyses.parkour.unit.service;

import market.analyses.parkour.dto.PriceDTO;
import market.analyses.parkour.dto.SwitchPriceDTO;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.entity.SwitchPriceHistory;
import market.analyses.parkour.repository.SwitchPriceHistoryRepository;
import market.analyses.parkour.service.SwitchPriceHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwitchPriceHistoryServiceTest {

    @Mock
    private SwitchPriceHistoryRepository repository;

    @InjectMocks
    private SwitchPriceHistoryService service;

    @Test
    public void getAllPriceChanges_ReturnsValidList() {
        var s = new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);
        var history = List.of(
                new SwitchPriceHistory(1, s, 45, LocalDate.of(2025, 6, 20)),
                new SwitchPriceHistory(2, s, 50, LocalDate.of(2025, 6, 23))
        );

        doReturn(history).when(repository).findAll();
        var response = service.getAllPriceChanges();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
        assertEquals(history, response);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void savePriceChanges_ReturnsValidEntity() {
        var s = new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);
        var history = new SwitchPriceHistory(null, s, 45, LocalDate.of(2025, 6, 20));
        var savedHistory = new SwitchPriceHistory(1, s, 45, LocalDate.of(2025, 6, 20));

        doReturn(savedHistory).when(repository).save(history);
        var response = service.savePriceChange(history);

        assertNotNull(response);
        assertEquals(savedHistory, response);
        verify(repository, times(1)).save(history);
    }

    @Test
    public void getHistoryById_HistoryFound_ReturnsValidEntity() {
        var s = new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);
        var history = new SwitchPriceHistory(1, s, 45, LocalDate.of(2025, 6, 20));

        doReturn(Optional.of(history)).when(repository).findById(1L);
        var response = service.getHistoryById(1L);

        assertNotNull(response);
        assertEquals(history, response);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void getHistoryById_HistoryNotFound_ThrowValidException() {
        doReturn(Optional.empty()).when(repository).findById(1L);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,() -> {
            service.getHistoryById(1L);
        });

        assertEquals("History with id: 1 not found", exception.getMessage());
    }

    @Test
    public void existById_ReturnsValidBoolean() {
        doReturn(true).when(repository).existsById(1L);
        var response = service.existsById(1L);

        assertTrue(response);
        verify(repository, times(1)).existsById(1L);
    }

    @Test
    public void delete_ValidTimesCallRepository() {
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void getHistoryBySwitch_ReturnValidList() {
        var s = new Switch(1, new Company(1, "company1"), "switch1", 10, 2, 2, true, false, true);
        var s2 = new Switch(2, new Company(1, "company1"), "switch2", 10, 2, 2, true, false, true);
        var history = List.of(
            new SwitchPriceHistory(1, s, 25, LocalDate.of(2025, 5, 23)),
            new SwitchPriceHistory(2, s, 35, LocalDate.of(2025, 6, 23)),
            new SwitchPriceHistory(3, s2, 30, LocalDate.of(2025, 5, 23)),
            new SwitchPriceHistory(3, s2, 25, LocalDate.of(2025, 6, 23))
        );

        var switchPricesDto = List.of(
            new SwitchPriceDTO(2L, "switch2", List.of(
                    new PriceDTO(30, LocalDate.of(2025, 5, 23)),
                    new PriceDTO(25, LocalDate.of(2025, 6, 23))
            )),
            new SwitchPriceDTO(1L, "switch1", List.of(
                    new PriceDTO(25, LocalDate.of(2025, 5, 23)),
                    new PriceDTO(35, LocalDate.of(2025, 6, 23))
            ))
        );

        doReturn(history).when(repository).findAllWithSwitchOrdered();
        var response = service.getHistoryBySwitch();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
        assertThat(response).containsExactlyInAnyOrderElementsOf(switchPricesDto);
        verify(repository, times(1)).findAllWithSwitchOrdered();
    }
}
