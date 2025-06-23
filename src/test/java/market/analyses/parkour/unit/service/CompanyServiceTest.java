package market.analyses.parkour.unit.service;

import market.analyses.parkour.entity.Company;
import market.analyses.parkour.repository.CompanyRepository;
import market.analyses.parkour.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    public void getAllCompanies_ReturnsValidList() {
        var companies = List.of(
                new Company(1, "company1"),
                new Company(2, "company2")
        );

        doReturn(companies).when(companyRepository).findAll();
        var response = companyService.getAllCompanies();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
        assertEquals(companies, response);
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    public void getCompanyById_ReturnsValidEntity() {
        var company = new Company(1, "company1");

        doReturn(Optional.of(company)).when(companyRepository).findById(1L);
        var response = companyService.getCompanyById(1L);

        assertNotNull(response);
        assertEquals(company, response);
        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    public void getCompanyById_ReturnsNull() {
        doReturn(Optional.empty()).when(companyRepository).findById(1L);
        var response = companyService.getCompanyById(1L);

        assertNull(response);
        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    public void saveCompany_ReturnsValidEntity() {
        var company = new Company(null, "company1");
        var savedCompany = new Company(1, "company1");

        doReturn(savedCompany).when(companyRepository).save(company);
        var response = companyService.saveCompany(company);

        assertNotNull(response);
        assertEquals(savedCompany, response);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    public void deleteCompany_ValidTimesCallRepository() {
        companyService.deleteCompany(1L);
        verify(companyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void existById_ReturnsValidBoolean() {
        doReturn(true).when(companyRepository).existsById(1L);
        var response = companyService.existsById(1L);

        assertTrue(response);
        verify(companyRepository, times(1)).existsById(1L);
    }
}
