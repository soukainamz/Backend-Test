package com.example.Todo_List_API.Repositories;

import com.example.Todo_List_API.Models.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testSaveCompany() {
        Company company = new Company();
        company.setName("Test Company");

        Company savedCompany = companyRepository.save(company);

        assertThat(savedCompany).isNotNull();
        assertThat(savedCompany.getName()).isEqualTo("Test Company");
    }

    @Test
    public void testFindAllCompanies() {
        Company company1 = new Company();
        company1.setName("Company 1");

        Company company2 = new Company();
        company2.setName("Company 2");

        companyRepository.save(company1);
        companyRepository.save(company2);

        assertThat(companyRepository.findAll()).hasSize(2);
    }
}
