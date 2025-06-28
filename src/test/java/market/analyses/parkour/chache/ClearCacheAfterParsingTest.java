package market.analyses.parkour.chache;


import market.analyses.parkour.config.BeansConfiguration;
import market.analyses.parkour.dto.ParserResultDTO;
import market.analyses.parkour.repository.SwitchPriceHistoryRepository;
import market.analyses.parkour.repository.SwitchRepository;
import market.analyses.parkour.service.ParserResultListener;
import market.analyses.parkour.service.SwitchPriceHistoryService;
import market.analyses.parkour.service.SwitchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = BeansConfiguration.class)
@AutoConfigureMockMvc
public class ClearCacheAfterParsingTest {

    @Autowired
    private ParserResultListener listener;

    @Autowired
    private SwitchService switchService;

    @Autowired
    private SwitchPriceHistoryService switchPriceHistoryService;

    @MockitoSpyBean
    private SwitchRepository switchRepository;

    @MockitoSpyBean
    private SwitchPriceHistoryRepository switchPriceHistoryRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void clear() {
        cacheManager.getCache("switches").clear();
        cacheManager.getCache("switchesData").clear();
        cacheManager.getCache("switchPricesHistories").clear();
        Mockito.reset(switchPriceHistoryRepository);
        Mockito.reset(switchRepository);
    }

    @Test
    public void ReceiveParserResult_AllCachesClear() throws Exception {
        //первые вызовы заполняют кеш
        switchService.getAllSwitches();
        switchService.getAllSwitchesDTO();
        switchPriceHistoryService.getHistoryBySwitch();
        verify(switchRepository, times(2)).findAll();
        verify(switchPriceHistoryRepository, times(1)).findAllWithSwitchOrdered();

        //повторные вызовы берут данные из кеша
        switchService.getAllSwitches();
        switchService.getAllSwitchesDTO();
        switchPriceHistoryService.getHistoryBySwitch();
        verify(switchRepository, times(2)).findAll();
        verify(switchPriceHistoryRepository, times(1)).findAllWithSwitchOrdered();

        ParserResultDTO result = new ParserResultDTO();
        String message = objectMapper.writeValueAsString(result);

        listener.receiveResult(message);

        switchService.getAllSwitches();
        switchService.getAllSwitchesDTO();
        switchPriceHistoryService.getHistoryBySwitch();
        verify(switchRepository, times(4)).findAll();
        verify(switchPriceHistoryRepository, times(2)).findAllWithSwitchOrdered();
    }
}
