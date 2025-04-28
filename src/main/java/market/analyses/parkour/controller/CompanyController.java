package market.analyses.parkour.controller;

import market.analyses.parkour.entity.Company;
import market.analyses.parkour.repository.CompanyRepository;
import market.analyses.parkour.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);

        if (company != null) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody Company company,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        if (company.getId() != null && companyService.existsById(company.getId().longValue())) {
            return ResponseEntity.badRequest().body("Компания с id: " + company.getId() + " уже существует.");
        }
        Company newCompany = companyService.saveCompany(company);
        int id = newCompany.getId();
        return ResponseEntity.created(uriComponentsBuilder
                    .path("companies/{id}")
                    .build(Map.of("id", id)))
                .body(newCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        Company company = companyService.getCompanyById(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }

        company.setNameCompany(companyDetails.getNameCompany());
        return ResponseEntity.ok(companyService.saveCompany(company));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (!companyService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}