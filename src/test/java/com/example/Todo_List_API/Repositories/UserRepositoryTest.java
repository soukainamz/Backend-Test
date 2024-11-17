package com.example.Todo_List_API.Repositories;

import com.example.Todo_List_API.Models.Company;
import com.example.Todo_List_API.Models.User;
import com.example.Todo_List_API.Dtos.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testFindByCompanyId() {
        // Create and save a company
        Company company = new Company();
        company.setName("Test Company");
        company = companyRepository.save(company);

        // Create and save a user associated with the company
        User user = new User();
        user.setUsername("JohnDoe");
        user.setRole("Admin");
        user.setCompany(company);
        user = userRepository.save(user);

        // Fetch users by companyId
        List<User> users = userRepository.findByCompanyId(company.getId());

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("JohnDoe");

        // Map the fetched user to UserDTO
        UserDTO userDTO = new UserDTO(users.get(0).getId(), users.get(0).getUsername(), users.get(0).getRole(), users.get(0).getCompany().getId());

        // Verify the UserDTO is mapped correctly
        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo("JohnDoe");
        assertThat(userDTO.getRole()).isEqualTo("Admin");
        assertThat(userDTO.getCompanyId()).isEqualTo(company.getId());
    }
}
