package market.analyses.parkour.unit.controller;

import market.analyses.parkour.controller.CompanyController;
import market.analyses.parkour.entity.Company;
import market.analyses.parkour.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {

    @Mock
    CompanyService companyService;

    @InjectMocks
    CompanyController companyController;

    @Test
    void getAllCompanies_ReturnsValidResponseEntity() {
        var companies = List.of(
                new Company(1, "Company A"),
                new Company(2, "Company B")
        );

        Mockito.doReturn(companies).when(companyService).getAllCompanies();

        var response = companyController.getAllCompanies();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(companies, response.getBody());
    }

    @Test
    void getCompanyById_ReturnsCompany() {
        var company = new Company(1, "Company A");

        Mockito.doReturn(company).when(companyService).getCompanyById(1L);

        var response = companyController.getCompanyById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(company, response.getBody());
    }

    @Test
    void getCompanyById_ReturnsNotFound() {
        Mockito.doReturn(null).when(companyService).getCompanyById(999L);

        var response = companyController.getCompanyById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createCompany_ReturnsCreatedResponse() {
        var company = new Company(1, "Company A");

        Mockito.doReturn(company).when(companyService).saveCompany(company);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        var response = companyController.createCompany(company, uriComponentsBuilder);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateCompany_ReturnsUpdatedCompany() {
        var company = new Company(1, "Company A");
        var updatedCompany = new Company(1, "Company B");

        Mockito.doReturn(company).when(companyService).getCompanyById(1L);
        Mockito.doReturn(updatedCompany).when(companyService).saveCompany(Mockito.any(Company.class));

        var response = companyController.updateCompany(1L, updatedCompany);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCompany, response.getBody());
    }

    @Test
    void updateCompany_ReturnsNotFound() {
        var updatedCompany = new Company(999, "Company B");

        Mockito.doReturn(null).when(companyService).getCompanyById(999L);

        var response = companyController.updateCompany(999L, updatedCompany);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteCompany_ReturnsNoContent() {
        Mockito.doReturn(true).when(companyService).existsById(1L);

        var response = companyController.deleteCompany(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteCompany_ReturnsNotFound() {
        Mockito.doReturn(false).when(companyService).existsById(999L);

        var response = companyController.deleteCompany(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
