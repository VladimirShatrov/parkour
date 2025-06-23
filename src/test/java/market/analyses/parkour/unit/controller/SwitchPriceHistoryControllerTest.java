package market.analyses.parkour.unit.controller;

import market.analyses.parkour.controller.SwitchPriceHistoryController;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.entity.SwitchPriceHistory;
import market.analyses.parkour.service.SwitchPriceHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SwitchPriceHistoryControllerTest {

    @Mock
    private SwitchPriceHistoryService service;

    @InjectMocks
    private SwitchPriceHistoryController controller;

    @Test
    void getAllHistory_ReturnsValidResponseEntity() {
        List<SwitchPriceHistory> priceHistoryList = List.of(
                new SwitchPriceHistory(1, new Switch(), 100, LocalDate.parse("2025-05-10")),
                new SwitchPriceHistory(2, new Switch(), 120, LocalDate.parse("2025-05-11"))
        );
        Mockito.when(service.getAllPriceChanges()).thenReturn(priceHistoryList);

        ResponseEntity<List<SwitchPriceHistory>> response = controller.getAllHistory();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(priceHistoryList, response.getBody());
        verify(service, times(1)).getAllPriceChanges();
    }

    @Test
    void getHistoryById_ReturnsValidResponseEntity() {
        SwitchPriceHistory priceHistory = new SwitchPriceHistory(1, new Switch(), 100, LocalDate.parse("2025-05-10"));
        Mockito.when(service.getHistoryById(1L)).thenReturn(priceHistory);

        ResponseEntity<SwitchPriceHistory> response = controller.getHistoryById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(priceHistory, response.getBody());
        verify(service, times(1)).getHistoryById(1L);
    }

    @Test
    void getHistoryById_ReturnsNotFoundResponseEntity_WhenNotFound() {
        Mockito.when(service.getHistoryById(1L)).thenThrow(new NoSuchElementException("History with id: 1 not found"));

        ResponseEntity<SwitchPriceHistory> response = controller.getHistoryById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(service, times(1)).getHistoryById(1L);
    }

    @Test
    void createHistory_ReturnsCreatedResponseEntity() {
        SwitchPriceHistory newHistory = new SwitchPriceHistory(null, new Switch(), 100, LocalDate.parse("2025-05-10"));
        SwitchPriceHistory createdHistory = new SwitchPriceHistory(1, new Switch(), 100, LocalDate.parse("2025-05-10"));

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(URI.create("http://localhost"));
        Mockito.when(service.savePriceChange(Mockito.any(SwitchPriceHistory.class))).thenReturn(createdHistory);

        ResponseEntity<?> response = controller.createHistory(newHistory, uriBuilder);
        URI location = response.getHeaders().getLocation();

        assertNotNull(response);
        assertNotNull(location);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("http://localhost/switch-price-history/1", location.toString());
        assertEquals(createdHistory, response.getBody());

        verify(service, times(0)).existsById(1L);
        verify(service, times(1)).savePriceChange(newHistory);
    }

    @Test
    void createHistory_ReturnsBadRequestResponseEntity_WhenIdExists() {
        SwitchPriceHistory newHistory = new SwitchPriceHistory(1, new Switch(), 100, LocalDate.parse("2025-05-10"));

        Mockito.when(service.existsById(1L)).thenReturn(true);

        ResponseEntity<?> response = controller.createHistory(newHistory, UriComponentsBuilder.newInstance());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Запись об изменении цены с id: 1 уже существует", response.getBody());

        verify(service, times(1)).existsById(1L);
        verify(service, times(0)).savePriceChange(newHistory);
    }

    @Test
    void updateHistory_ReturnsUpdatedResponseEntity() {
        SwitchPriceHistory existingHistory = new SwitchPriceHistory(1, new Switch(), 100, LocalDate.parse("2025-05-10"));
        SwitchPriceHistory updatedHistory = new SwitchPriceHistory(1, new Switch(), 120, LocalDate.parse("2025-05-11"));

        Mockito.when(service.getHistoryById(1L)).thenReturn(existingHistory);
        Mockito.when(service.savePriceChange(Mockito.any(SwitchPriceHistory.class))).thenReturn(updatedHistory);

        ResponseEntity<SwitchPriceHistory> response = controller.updateHistory(1L, updatedHistory);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedHistory, response.getBody());

        verify(service, times(1)).getHistoryById(1L);
        verify(service, times(1)).savePriceChange(Mockito.any(SwitchPriceHistory.class));
    }

    @Test
    void updateHistory_ReturnsNotFoundResponseEntity_WhenNotFound() {
        SwitchPriceHistory updatedHistory = new SwitchPriceHistory(1, new Switch(), 120, LocalDate.parse("2025-05-11"));

        Mockito.when(service.getHistoryById(1L)).thenThrow(new NoSuchElementException("History with id: 1 not found"));

        ResponseEntity<SwitchPriceHistory> response = controller.updateHistory(1L, updatedHistory);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(service, times(1)).getHistoryById(1L);
        verify(service, times(0)).savePriceChange(Mockito.any(SwitchPriceHistory.class));
    }

    @Test
    void deleteHistory_ReturnsNoContentResponseEntity() {
        Mockito.when(service.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteHistory(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service, times(1)).delete(1L);
        verify(service, times(1)).existsById(1L);
    }

    @Test
    void deleteHistory_ReturnsNotFoundResponseEntity_WhenNotFound() {
        Mockito.when(service.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = controller.deleteHistory(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(service, times(0)).delete(1L);
        verify(service, times(1)).existsById(1L);
    }
}
