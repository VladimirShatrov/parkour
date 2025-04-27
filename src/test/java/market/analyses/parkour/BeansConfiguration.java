package market.analyses.parkour;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class BeansConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer(DynamicPropertyRegistry registry) {
        var container = new PostgreSQLContainer<>("postgres:15")
                .withInitScript("init.sql");
        return container;
    }
}
