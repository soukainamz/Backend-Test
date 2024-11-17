package com.example.Todo_List_API.Services;

import com.example.Todo_List_API.Models.Task;
import com.example.Todo_List_API.Models.TaskStatus;
import com.example.Todo_List_API.Models.User;
import com.example.Todo_List_API.Repositories.TaskRepository;
import com.example.Todo_List_API.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setRole("STANDARD");

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.START); // Correct usage of TaskStatus Enum
        task.setUser(user);
    }

    @Test
    public void testGetTasksForUser_standardUser() {
        when(taskRepository.findByUserId(user.getId())).thenReturn(Collections.singletonList(task));

        List<Task> tasks = taskService.getTasksForUser(user);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
        verify(taskRepository, times(1)).findByUserId(user.getId());
    }


    @Test
    public void testGetTasksForUser_superUser() {
        user.setRole("SUPER_USER");
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));

        List<Task> tasks = taskService.getTasksForUser(user);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task, user);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Map<String, Object> result = taskService.updateTask(1L, 1L);

        assertNotNull(result);
        assertEquals(task, result.get("task"));
        assertEquals(user, result.get("user"));
    }

    @Test
    public void testSaveTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        task.setTitle("Updated Task");
        Task updatedTask = taskService.saveTask(task);

        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.saveTask(task);
        });

        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    public void testUserNotFoundForUpdate() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.updateTask(1L, 1L);
        });

        assertEquals("User not found", exception.getMessage());
    }
}
