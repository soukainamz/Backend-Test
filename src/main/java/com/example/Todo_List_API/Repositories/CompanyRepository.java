package com.example.Todo_List_API.Repositories;

import com.example.Todo_List_API.Models.Company;
import com.example.Todo_List_API.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
   // List<Company> findByCompanyId(Long id);
}
