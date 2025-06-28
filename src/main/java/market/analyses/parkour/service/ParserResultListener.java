package market.analyses.parkour.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import market.analyses.parkour.config.RabbitMQConfig;
import market.analyses.parkour.dto.ParserResultDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.CacheManager;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ParserResultListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;
    private final CacheManager cacheManager;

    public ParserResultListener(SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper, CacheManager cacheManager) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
        this.cacheManager = cacheManager;
    }

    @RabbitListener(queues = RabbitMQConfig.RESULT_QUEUE)
    public void receiveResult(String message) {
        try {
            ParserResultDTO dto = objectMapper.readValue(message, ParserResultDTO.class);

            cacheManager.getCache("switches").clear();
            cacheManager.getCache("switchesData").clear();
            cacheManager.getCache("switchPricesHistories").clear();

            messagingTemplate.convertAndSend("/topic/parser/result", dto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
