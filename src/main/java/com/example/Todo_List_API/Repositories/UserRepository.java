package com.example.Todo_List_API.Repositories;

import com.example.Todo_List_API.Models.Task;
import com.example.Todo_List_API.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
  //  List<User> findByCompany(String company);

    List<User> findByCompanyId(Long id);
}
