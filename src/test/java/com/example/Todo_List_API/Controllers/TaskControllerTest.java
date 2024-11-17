package com.example.Todo_List_API.Controllers;

import com.example.Todo_List_API.Models.Task;
import com.example.Todo_List_API.Models.User;
import com.example.Todo_List_API.Repositories.UserRepository;
import com.example.Todo_List_API.Services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserRepository userRepository;

    private User user;
    private Task task;

    @BeforeEach
    public void setup() {
        // Create a mock user
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setRole("Standard");

        // Create a mock task
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("This is a test task");
        task.setUser(user);
    }

    @Test
    public void testGetTasksByUserId() throws Exception {
        // Mock userRepository and taskService
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskService.getTasksForUser(user)).thenReturn(List.of(task));

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tasks_item"))
                .andExpect(model().attributeExists("user_info"))
                .andExpect(view().name("tasks"));
    }

    @Test
    public void testShowEditTaskForm() throws Exception {
        // Mock taskService
        when(taskService.updateTask(1L, 1L)).thenReturn(
                Map.of("task", task, "user", user)
        );

        // Perform GET request for edit form
        mockMvc.perform(get("/api/tasks/1/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("userRole"))
                .andExpect(model().attributeExists("accountID"))
                .andExpect(model().attribute("taskstatut", task.getStatus()))
                .andExpect(view().name("edit-task"));
    }

    @Test
    public void testSaveEditedTask() throws Exception {
        // Perform POST request to save task
        mockMvc.perform(post("/api/tasks/1/edit/1")
                        .flashAttr("task", task))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/tasks/1"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        // Perform POST request to delete task
        mockMvc.perform(post("/api/tasks/1/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/tasks/1"));
    }
}
