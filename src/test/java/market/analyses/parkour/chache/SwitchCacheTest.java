package market.analyses.parkour.chache;

import market.analyses.parkour.config.BeansConfiguration;
import market.analyses.parkour.config.DataBaseTestHelper;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.repository.SwitchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(classes = BeansConfiguration.class)
@AutoConfigureMockMvc
public class SwitchCacheTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private SwitchRepository repository;

    @Autowired
    private DataBaseTestHelper dataBaseTestHelper;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        dataBaseTestHelper.resetDatabase();
        cacheManager.getCache("switches").clear();
        cacheManager.getCache("switchesData").clear();
    }

    @Test
    public void EndpointCallTwoTimes_CachingWorks() throws Exception {
        mockMvc.perform(get("/switches"))
                .andExpectAll(status().isOk());
        mockMvc.perform(get("/switches"))
                .andExpectAll(status().isOk());

        verify(repository, times(1)).findAll();
    }

    @Test
    public void EndpointCallTwoTimes_CachingWorksWithDto() throws Exception {
        mockMvc.perform(get("/switches/all"))
                .andExpectAll(status().isOk());
        mockMvc.perform(get("/switches/all"))
                .andExpectAll(status().isOk());

        verify(repository, times(1)).findAll();
    }

    @Test
    public void SaveSwitch_ClearCache() throws Exception {
        mockMvc.perform(get("/switches"))
                .andExpectAll(status().isOk());

        Switch s = new Switch(null, new Company(1, "company1"), "newSwitch", 2, 2, 2, true, true, true);
        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = mapper.writeValueAsString(s);

        mockMvc.perform(post("/switches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpectAll(status().isCreated());

        mockMvc.perform(get("/switches"))
                .andExpectAll(status().isOk());

        verify(repository, times(2)).findAll();
    }

    @Test
    public void SaveSwitch_ClearCacheDto() throws Exception {
        mockMvc.perform(get("/switches/all"))
                .andExpectAll(status().isOk());

        Switch s = new Switch(null, new Company(1, "company1"), "newSwitch", 2, 2, 2, true, true, true);
        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = mapper.writeValueAsString(s);

        mockMvc.perform(post("/switches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpectAll(status().isCreated());

        mockMvc.perform(get("/switches/all"))
                .andExpectAll(status().isOk());

        verify(repository, times(2)).findAll();
    }

    @Test
    public void DeleteSwitch_ClearCache() throws Exception {
        mockMvc.perform(get("/switches"))
                .andExpectAll(status().isOk());

        mockMvc.perform(delete("/switches/{id}", 1))
                .andExpectAll(status().isNoContent());

        mockMvc.perform(get("/switches"))
                .andExpectAll(status().isOk());

        verify(repository, times(2)).findAll();
    }

    @Test
    public void DeleteSwitch_ClearCacheDto() throws Exception {
        mockMvc.perform(get("/switches/all"))
                .andExpectAll(status().isOk());

        mockMvc.perform(delete("/switches/{id}", 1))
                .andExpectAll(status().isNoContent());

        mockMvc.perform(get("/switches/all"))
                .andExpectAll(status().isOk());

        verify(repository, times(2)).findAll();
    }
}
