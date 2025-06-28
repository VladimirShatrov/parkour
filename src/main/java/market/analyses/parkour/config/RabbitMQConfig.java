package market.analyses.parkour.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String RUN_QUEUE = "parser.run";
    public static final String RESULT_QUEUE = "parser.result";

    @Bean
    public Queue runQueue() {
        return new Queue(RUN_QUEUE, false);
    }

    @Bean
    public Queue resultQueue() {
        return new Queue(RESULT_QUEUE, false);
    }
}