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

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        verify(companyService, times(1)).getAllCompanies();
    }

    @Test
    void getCompanyById_ReturnsCompany() {
        var company = new Company(1, "Company A");

        Mockito.doReturn(company).when(companyService).getCompanyById(1L);

        var response = companyController.getCompanyById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(company, response.getBody());
        verify(companyService, times(1)).getCompanyById(1L);
    }

    @Test
    void getCompanyById_ReturnsNotFound() {
        Mockito.doReturn(null).when(companyService).getCompanyById(999L);

        var response = companyController.getCompanyById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(companyService, times(1)).getCompanyById(999L);
    }

    @Test
    void createCompany_ReturnsCreatedResponse() {
        var company = new Company(1, "Company A");

        Mockito.doReturn(company).when(companyService).saveCompany(company);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(URI.create("http://localhost"));

        var response = companyController.createCompany(company, uriComponentsBuilder);
        URI location = response.getHeaders().getLocation();

        assertNotNull(response);
        assertNotNull(location);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("http://localhost/companies/1", location.toString());
        assertEquals(response.getBody(), company);
        verify(companyService, times(1)).saveCompany(company);
        verify(companyService, times(1)).existsById(1L);
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
        verify(companyService, times(1)).getCompanyById(1L);
        verify(companyService, times(1)).saveCompany(company);
    }

    @Test
    void updateCompany_ReturnsNotFound() {
        var updatedCompany = new Company(999, "Company B");

        Mockito.doReturn(null).when(companyService).getCompanyById(999L);

        var response = companyController.updateCompany(999L, updatedCompany);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(companyService, times(1)).getCompanyById(999L);
        verify(companyService, times(0)).saveCompany(Mockito.any(Company.class));
    }

    @Test
    void deleteCompany_ReturnsNoContent() {
        Mockito.doReturn(true).when(companyService).existsById(1L);

        var response = companyController.deleteCompany(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(companyService, times(1)).deleteCompany(1L);
    }

    @Test
    void deleteCompany_ReturnsNotFound() {
        Mockito.doReturn(false).when(companyService).existsById(999L);

        var response = companyController.deleteCompany(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(companyService, times(1)).existsById(999L);
        verify(companyService, times(0)).deleteCompany(Mockito.any(Long.class));
    }
}
