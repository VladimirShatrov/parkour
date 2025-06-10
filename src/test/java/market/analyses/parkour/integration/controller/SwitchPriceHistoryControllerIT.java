package market.analyses.parkour.integration.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import market.analyses.parkour.config.BeansConfiguration;
import market.analyses.parkour.config.DataBaseTestHelper;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.Switch;
import market.analyses.parkour.entity.SwitchPriceHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = BeansConfiguration.class)
@AutoConfigureMockMvc
public class SwitchPriceHistoryControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DataBaseTestHelper dataBaseTestHelper;

    @BeforeEach
    void resetDataBase() {
        dataBaseTestHelper.resetDatabase();
    }

    @Test
    public void getAllHistory_ReturnsValidResponseEntity() throws Exception {
        this.mockMvc.perform(get("/switch-price-history"))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                        [
                            {
                                "id": 1,
                                "switchEntity": {
                                    "id": 1,
                                    "company": {
                                        "id": 1,
                                        "nameCompany": "TFortis"
                                    },
                                    "title": "Catalyst 9200",
                                    "price": 1200,
                                    "poePorts": 24,
                                    "sfpPorts": 4,
                                    "ups": true,
                                    "controllable": true,
                                    "available": true
                                },
                                "newPrice": 1100,
                                "changeDate": "2025-04-01"
                            },
                            {
                                "id": 2,
                                "switchEntity": {
                                    "id": 1,
                                    "company": {
                                        "id": 1,
                                        "nameCompany": "TFortis"
                                    },
                                    "title": "Catalyst 9200",
                                    "price": 1200,
                                    "poePorts": 24,
                                    "sfpPorts": 4,
                                    "ups": true,
                                    "controllable": true,
                                    "available": true
                                },
                                "newPrice": 1150,
                                "changeDate": "2025-04-15"
                            },
                            {
                                "id": 3,
                                "switchEntity": {
                                    "id": 2,
                                    "company": {
                                        "id": 2,
                                        "nameCompany": "MASTERMANN"
                                    },
                                    "title": "TL-SG3428X",
                                    "price": 400,
                                    "poePorts": 24,
                                    "sfpPorts": 4,
                                    "ups": false,
                                    "controllable": true,
                                    "available": true
                                },
                                "newPrice": 390,
                                "changeDate": "2025-03-20"
                            },
                            {
                                "id": 4,
                                "switchEntity": {
                                    "id": 3,
                                    "company": {
                                        "id": 3,
                                        "nameCompany": "OSNOVO"
                                    },
                                    "title": "CRS328-24P-4S+",
                                    "price": 600,
                                    "poePorts": 24,
                                    "sfpPorts": 4,
                                    "ups": true,
                                    "controllable": false,
                                    "available": true
                                },
                                "newPrice": 580,
                                "changeDate": "2025-03-25"
                            }
                        ]
                        """)
                );
    }

    @Test
    public void getHistoryById_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
        int id = 1;
        this.mockMvc.perform(get("/switch-price-history/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                        {
                            "id": 1,
                            "switchEntity": {
                                "id": 1,
                                "company": {
                                    "id": 1,
                                    "nameCompany": "TFortis"
                                },
                                "title": "Catalyst 9200",
                                "price": 1200,
                                "poePorts": 24,
                                "sfpPorts": 4,
                                "ups": true,
                                "controllable": true,
                                "available": true
                            },
                            "newPrice": 1100,
                            "changeDate": "2025-04-01"
                        }
                        """)
                );
    }

    @Test
    public void getHistoryById_PayloadIsInValid_ReturnsValidStatusCode() throws Exception {
        int id = 5;
        this.mockMvc.perform(get("/switch-price-history/{id}", id))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void createHistory_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
        SwitchPriceHistory payload = new SwitchPriceHistory(null, new Switch(1, new Company(1, "TFortis"),
                "NewSwitch", 1000, 12, 0, false, false, false),
                1000, LocalDate.of(2025, 4, 29));

        SwitchPriceHistory answerPayload = new SwitchPriceHistory(5, new Switch(1, new Company(1, "TFortis"),
                "NewSwitch", 1000, 12, 0, false, false, false),
                1000, LocalDate.of(2025, 4, 29));


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonPayload = objectMapper.writeValueAsString(payload);
        String jsonAnswer = objectMapper.writeValueAsString(answerPayload);

        this.mockMvc.perform(post("/switch-price-history")
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType("application/json"),
                        header().string("Location", "http://localhost/switch-price-history/5"),
                        content().json(jsonAnswer)
                );
    }

    @Test
    public void createHistory_PayloadIsInValid_ReturnsValidStatusCode() throws Exception {
        SwitchPriceHistory payload = new SwitchPriceHistory(1, new Switch(1, new Company(1, "TFortis"),
                "NewSwitch", 1000, 12, 0, false, false, false),
                1000, LocalDate.of(2025, 4, 29));


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonPayload = objectMapper.writeValueAsString(payload);

        this.mockMvc.perform(post("/switch-price-history")
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Запись об изменении цены с id: " + payload.getId() + " уже существует")
                );
    }

    @Test
    public void updateHistory_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
        SwitchPriceHistory payload = new SwitchPriceHistory(1, new Switch(1, new Company(1, "TFortis"),
                "NewSwitch", 1000, 12, 0, false, false, false),
                1000, LocalDate.of(2025, 4, 29));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonPayload = objectMapper.writeValueAsString(payload);

        this.mockMvc.perform(put("/switch-price-history/{id}", 1)
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpectAll(
                        status().isOk(),
                        content().json(jsonPayload)
                );
    }

    @Test
    public void updateHistory_PayloadIsInValid_ReturnsValidStatusCode() throws Exception {
        SwitchPriceHistory payload = new SwitchPriceHistory(5, new Switch(1, new Company(1, "TFortis"),
                "NewSwitch", 1000, 12, 0, false, false, false),
                1000, LocalDate.of(2025, 4, 29));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonPayload = objectMapper.writeValueAsString(payload);

        this.mockMvc.perform(put("/switch-price-history/{id}", 5)
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void deleteSwitch_PayloadIsValid_ReturnsValidStatusCode() throws Exception {
        int id = 1;
        this.mockMvc.perform(delete("/switch-price-history/{id}", id))
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    public void deleteSwitch_PayloadIsInValid_ReturnsValidStatusCode() throws Exception {
        int id = 5;
        this.mockMvc.perform(delete("/switch-price-history/{id}", id))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void getHistoryByAllSwitches_ReturnsValidResponseEntity() throws Exception {
        this.mockMvc.perform(get("/switch-price-history/sortedBySwitch"))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                            [
                                {
                                    "switchId": 1,
                                    "switchName": "Catalyst 9200",
                                    "prices": [
                                        {
                                            "price": 1100,
                                            "date": "2025-04-01"
                                        },
                                        {
                                            "price": 1150,
                                            "date": "2025-04-15"
                                        }
                                    ]
                                },
                                {
                                    "switchId": 2,
                                    "switchName": "TL-SG3428X",
                                    "prices": [
                                        {
                                            "price": 390,
                                            "date": "2025-03-20"
                                        }
                                    ]
                                },
                                {
                                    "switchId": 3,
                                    "switchName": "CRS328-24P-4S+",
                                    "prices": [
                                        {
                                            "price": 580,
                                            "date": "2025-03-25"
                                        }
                                    ]
                                }
                            ]
                        """)
                );
    }
}
