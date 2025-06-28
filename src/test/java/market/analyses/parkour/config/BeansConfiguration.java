package market.analyses.parkour.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class BeansConfiguration {

    @Bean
    @ServiceConnection(name = "postgres")
    public PostgreSQLContainer<?> postgreSQLContainer() {
        var container = new PostgreSQLContainer<>("postgres:15")
                .withInitScript("init.sql");
        return container;
    }

    @Bean
    @ServiceConnection(name = "redis")
    public GenericContainer<?> redis() {
        return new GenericContainer<>(
                DockerImageName.parse("redis:7.2")
        ).withExposedPorts(6379);
    }

    @Bean
    public DataBaseTestHelper dataBaseTestHelper(JdbcTemplate jdbcTemplate) {
        return new DataBaseTestHelper(jdbcTemplate);
    }
}
