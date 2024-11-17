package com.example.Todo_List_API.Repositories;

import com.example.Todo_List_API.Models.Company;
import com.example.Todo_List_API.Models.Task;
import com.example.Todo_List_API.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdIn(List<Long> userIds);
    List<Task> findByUser(User user);

}
