package market.analyses.parkour.integration.controller;


import market.analyses.parkour.config.BeansConfiguration;
import market.analyses.parkour.config.DataBaseTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


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
}
