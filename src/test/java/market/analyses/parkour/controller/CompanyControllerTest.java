package market.analyses.parkour.controller;

import market.analyses.parkour.entity.Company;
import market.analyses.parkour.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {

    @Mock
    CompanyService service;

    @InjectMocks
    CompanyController controller;

    @Test
    void getAllCompanies_ReturnsValidResponseEntity() {
        var companies = List.of(
                new Company(1, "Company A"),
                new Company(2, "Company B")
        );

        Mockito.doReturn(companies).when(service).getAllCompanies();

        var response = controller.getAllCompanies();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(companies, response.getBody());
    }
}
