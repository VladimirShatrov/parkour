package market.analyses.parkour.service;

import market.analyses.parkour.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ParserCommandSender {

    private final RabbitTemplate rabbitTemplate;

    public ParserCommandSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRunCommand(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.RUN_QUEUE, message);
    }
}
