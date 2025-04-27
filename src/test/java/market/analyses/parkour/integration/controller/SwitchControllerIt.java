package market.analyses.parkour.integration.controller;

import market.analyses.parkour.BeansConfiguration;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.entity.Switch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = BeansConfiguration.class)
@AutoConfigureMockMvc
public class SwitchControllerIt {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllSwitches_ReturnsValidResponseEntity() throws Exception {
        this.mockMvc.perform(get("/switches"))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                        [
                            {
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
                            {
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
                            {
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
                            {
                                "id": 4,
                                "company": {
                                    "id": 4,
                                    "nameCompany": "РЕЛИОН"
                                },
                                "title": "DGS-1210-28P",
                                "price": 300,
                                "poePorts": 24,
                                "sfpPorts": 2,
                                "ups": false,
                                "controllable": true,
                                "available": true
                            }
                        ]
                        """)
                );
    }

    @Test
    public void getSwitchById_IdIsValid_ReturnsValidResponseEntity() throws Exception {
        this.mockMvc.perform(get("/switches/{id}", 1))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                        {
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
                        }                        
                        """)
                );
    }

    @Test
    public void getSwitchById_IdIsInvalid_ReturnsValidResponseEntity() throws Exception {
        this.mockMvc.perform(get("/switches/{id}", 5))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void createSwitch_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
        Switch payload = new Switch(null, new Company(1, "TFortis"),
                "NewSwitch", 1000, 12, 0, false, false, false);

        Switch answerPayload = new Switch(5, new Company(1, "TFortis"),
                "NewSwitch", 1000, 12, 0, false, false, false);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(payload);
        String answerJsonPayload = objectMapper.writeValueAsString(answerPayload);

        this.mockMvc.perform(post("/switches")
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType("application/json"),
                        header().string("Location", "http://localhost/switches/5"),
                        content().json(answerJsonPayload)
                );
    }
}
