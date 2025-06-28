package market.analyses.parkour.chache;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import market.analyses.parkour.config.BeansConfiguration;
import market.analyses.parkour.config.DataBaseTestHelper;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.entity.SwitchPriceHistory;
import market.analyses.parkour.repository.SwitchPriceHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BeansConfiguration.class)
@AutoConfigureMockMvc
public class PriceHistoryCacheTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private SwitchPriceHistoryRepository repository;

    @Autowired
    private DataBaseTestHelper dataBaseTestHelper;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        dataBaseTestHelper.resetDatabase();
        cacheManager.getCache("switchPricesHistories").clear();
    }

    @Test
    public void EndpointCallTwoTimes_CachingWorks() throws Exception {
        mockMvc.perform(get("/switch-price-history/sortedBySwitch"))
                .andExpectAll(status().isOk());
        mockMvc.perform(get("/switch-price-history/sortedBySwitch"))
                .andExpectAll(status().isOk());

        verify(repository, times(1)).findAllWithSwitchOrdered();
    }

    @Test
    public void SaveSwitch_ClearCache() throws Exception {
        mockMvc.perform(get("/switch-price-history/sortedBySwitch"))
                .andExpectAll(status().isOk());

        Switch s = new Switch(1, new Company(1, "TFortis"), "newSwitch", 2, 2, 2, true, true, true);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        SwitchPriceHistory history = new SwitchPriceHistory(null, s, 500, LocalDate.of(2025, 6, 27));
        String jsonPayloadHistory = mapper.writeValueAsString(history);

        mockMvc.perform(post("/switch-price-history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayloadHistory))
                .andExpectAll(status().isCreated());

        mockMvc.perform(get("/switch-price-history/sortedBySwitch"))
                .andExpectAll(status().isOk());

        verify(repository, times(2)).findAllWithSwitchOrdered();
    }

    @Test
    public void DeleteSwitch_ClearCache() throws Exception {
        mockMvc.perform(get("/switch-price-history/sortedBySwitch"))
                .andExpectAll(status().isOk());

        mockMvc.perform(delete("/switch-price-history/{id}", 1))
                .andExpectAll(status().isNoContent());

        mockMvc.perform(get("/switch-price-history/sortedBySwitch"))
                .andExpectAll(status().isOk());

        verify(repository, times(2)).findAllWithSwitchOrdered();
    }
}
