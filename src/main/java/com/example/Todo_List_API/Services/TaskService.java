package com.example.Todo_List_API.Services;

import com.example.Todo_List_API.Models.Task;
import com.example.Todo_List_API.Models.TaskStatus;
import com.example.Todo_List_API.Models.User;
import com.example.Todo_List_API.Repositories.TaskRepository;
import com.example.Todo_List_API.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getTasksForUser(User user) {
        if ("SUPER_USER".equals(user.getRole())) {
            return taskRepository.findAll();
        } else if ("COMPANY_ADMIN".equals(user.getRole())) {
            List<User> companyUsers = userRepository.findByCompanyId(user.getCompany().getId());
            List<Long> userIds = companyUsers.stream().map(User::getId).toList();
            return taskRepository.findByUserIdIn(userIds);
        } else {
            return taskRepository.findByUserId(user.getId());
        }
    }

    public Task createTask(Task task, User user) {
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Map<String, Object> updateTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));


        Map<String, Object> result = new HashMap<>();
        result.put("task", task);
        result.put("user", user);

        return result;
    }

    public Task saveTask(Task task_modify) {
        Task task = taskRepository.findById(task_modify.getId()).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(task_modify.getTitle());
        task.setDescription(task_modify.getDescription());
        task.setStatus(task_modify.getStatus());

            return taskRepository.save(task);

    }




    public void deleteTask(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.setUser(null);
        taskRepository.save(task);
        taskRepository.delete(task);
    }




    public Task addTask(String title, String description, TaskStatus status, Long userId) {
        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setDescription(description);
        newTask.setStatus(status);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        newTask.setUser(user);
        return taskRepository.save(newTask);
    }






}
