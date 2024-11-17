package com.example.Todo_List_API.Repositories;

import com.example.Todo_List_API.Models.Task;
import com.example.Todo_List_API.Models.User;
import com.example.Todo_List_API.Dtos.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserId() {
        // Create and save a user
        User user = new User();
        user.setUsername("JaneDoe");
        user.setRole("Employee");
        user = userRepository.save(user);

        // Create and save tasks for the user
        Task task1 = new Task();
        task1.setTitle("Complete Task 1");
        task1.setUser(user); // Associate task with user

        Task task2 = new Task();
        task2.setTitle("Complete Task 2");
        task2.setUser(user); // Associate task with user

        taskRepository.save(task1);
        taskRepository.save(task2);

        // Fetch tasks by userId
        List<Task> tasks = taskRepository.findByUserId(user.getId());

        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Complete Task 1");
        assertThat(tasks.get(1).getTitle()).isEqualTo("Complete Task 2");

        // Optionally, if you want to convert Task to DTO, you can do that as well
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getRole(), user.getCompany() != null ? user.getCompany().getId() : null);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo("JaneDoe");
        assertThat(userDTO.getRole()).isEqualTo("Employee");
    }

    @Test
    public void testFindByUserIdIn() {
        // Create and save multiple users
        User user1 = new User();
        user1.setUsername("User1");
        user1.setRole("Manager");
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("User2");
        user2.setRole("Developer");
        user2 = userRepository.save(user2);

        // Create and save tasks for the users
        Task task1 = new Task();
        task1.setTitle("Task for User 1");
        task1.setUser(user1);

        Task task2 = new Task();
        task2.setTitle("Task for User 2");
        task2.setUser(user2);

        taskRepository.save(task1);
        taskRepository.save(task2);

        // Fetch tasks by multiple user IDs
        List<Task> tasks = taskRepository.findByUserIdIn(List.of(user1.getId(), user2.getId()));

        assertThat(tasks).hasSize(2); // Two tasks should be returned
        assertThat(tasks.get(0).getTitle()).isIn("Task for User 1", "Task for User 2");
        assertThat(tasks.get(1).getTitle()).isIn("Task for User 1", "Task for User 2");
    }
}
