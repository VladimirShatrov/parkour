package market.analyses.parkour.integration.controller;

import market.analyses.parkour.config.BeansConfiguration;
import market.analyses.parkour.config.DataBaseTestHelper;
import market.analyses.parkour.entity.Company;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = BeansConfiguration.class)
@AutoConfigureMockMvc
public class CompanyControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DataBaseTestHelper dataBaseTestHelper;

    @BeforeEach
    void resetDataBase() {
        dataBaseTestHelper.resetDatabase();
    }

    @Test
    public void getAllCompanies_ReturnsValidResponseEntity() throws Exception {
        this.mockMvc.perform(get("/companies"))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                        [
                            {
                                "id": 1,
                                "nameCompany": "TFortis"
                            },
                            {
                                "id": 2,
                                "nameCompany": "MASTERMANN"
                            },
                            {
                                "id": 3,
                                "nameCompany": "OSNOVO"
                            },
                            {
                                "id": 4,
                                "nameCompany": "РЕЛИОН"
                            }
                        ]
                        """)
                );
    }

    @Test
    public void getCompanyById_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
        Long id = 1L;
        this.mockMvc.perform(get("/companies/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                            {
                                "id": 1,
                                "nameCompany": "TFortis"
                            }
                        """)
                );
    }

    @Test
    public void getCompaniesById_PayloadIsInvalid_ReturnsValidStatusCode() throws Exception {
        Long id = 5L;
        this.mockMvc.perform(get("/companies/{id}", id))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void createCompany_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
        Company newCompany = new Company(null, "newCompany");
        Company answerCompany = new Company(5, "newCompany");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(newCompany);
        String answerJson = objectMapper.writeValueAsString(answerCompany);

        this.mockMvc.perform(post("/companies")
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType("application/json"),
                        header().string("Location", "http://localhost/companies/5"),
                        content().json(answerJson)
                );
    }

    @Test
    public void createCompany_PayloadIsInValid_ReturnsValidStatusCode() throws Exception {
        Company company = new Company(1, "newCompany");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(company);

        this.mockMvc.perform(post("/companies")
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Компания с id: " + company.getId() + " уже существует.")
                );
    }

    @Test
    public void updateCompany_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
        Company payload = new Company(1, "updatedCompany");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(payload);

        this.mockMvc.perform(put("/companies/{id}", payload.getId())
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpectAll(
                        status().isOk(),
                        content().json(jsonPayload)
                );
    }

    @Test
    public void updateCompany_PayloadIsInValid_ReturnsValidStatusCode() throws Exception {
        Company payload = new Company(5, "updatedCompany");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(payload);

        this.mockMvc.perform(put("/companies/{id}", payload.getId())
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void deleteCompany_PayloadIsValid_ReturnsValidStatusCode() throws Exception {
        int id = 1;
        this.mockMvc.perform(delete("/companies/{id}", id))
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    public void deleteCompany_PayloadIsInValid_ReturnsValidStatusCode() throws Exception {
        int id = 5;
        this.mockMvc.perform(delete("/companies/{id}", id))
                .andExpectAll(
                        status().isNotFound()
                );
    }
}
