package com.example.Todo_List_API.Controllers;

import ch.qos.logback.core.model.Model;
import com.example.Todo_List_API.Dtos.TaskDTO;
import com.example.Todo_List_API.Dtos.UserDTO;
import com.example.Todo_List_API.Models.Task;
import com.example.Todo_List_API.Models.TaskStatus;
import com.example.Todo_List_API.Models.User;
import com.example.Todo_List_API.Repositories.UserRepository;
import com.example.Todo_List_API.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@RestController
@Controller
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    // For simplicity, using a dummy user as current user.
 /*   private User getCurrentUser() {
        return userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getTasksForUser(getCurrentUser());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task, getCurrentUser());
    }

  */
    private Long id_user_task;
    private String user_role;
    private User user_info;
    @GetMapping("/{userId}")
    public ModelAndView getTasksByUserId(@PathVariable Long userId) {
        // Fetch the user based on userId in the URL
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Return tasks for that specific user
        //return
        List<Task> tasks =    taskService.getTasksForUser(user);


        // Map tasks to DTOs
        List<TaskDTO> taskDTOs = tasks.stream().map(task -> new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getUser().getId(),
                task.getUser().getUsername(),
                task.getUser().getRole(),
                task.getStatus().toString()
        )).collect(Collectors.toList());



        // Map the User to a UserDTO, including companyId
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getRole(), user.getCompany().getId());

        List<User> allUsers = userRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("tasks"); // the name of the HTML view
        modelAndView.addObject("tasks_item", taskDTOs);  // add model attributes

        modelAndView.addObject("user_info", userDTO);
        modelAndView.addObject("users", allUsers);
      //  model.addAttribute("users", userService.getAllUsers());
        // modelAndView.addObject("account", user.getId());
        //modelAndView.addObject("account_role", user.getRole());
        user_info =user;
        id_user_task=userId;
        user_role= user.getRole();
        return modelAndView;
       // return "tasks";
    }


    // Edit task (GET request to show the edit form)
    @GetMapping("/{userId}/edit/{id}")
    public ModelAndView showEditTaskForm(@PathVariable Long id, @PathVariable Long userId) {

        Map<String, Object> result = taskService.updateTask(id, userId);
        Task task = (Task) result.get("task");
        User user = (User) result.get("user");

        ModelAndView modelAndView = new ModelAndView("edit-task");
        modelAndView.addObject("task", task);
        modelAndView.addObject("userRole", user.getRole());
        modelAndView.addObject("accountID", user.getId());
        modelAndView.addObject("taskstatut", task.getStatus());


        System.out.println("Task Status: " + task.getStatus());
        return modelAndView;
    }

    // Save edited task (POST request to save changes)
    @PostMapping("/{userId}/edit/{id}")
    public String saveEditedTask(@ModelAttribute Task task, @PathVariable Long userId) {

        taskService.saveTask(task); // Save the updated task

        return "redirect:/api/tasks/"+userId;
    }



    @PostMapping("/{userId}/delete/{id}")
    public String deleteTask(@PathVariable Long userId, @PathVariable Long id) {
        taskService.deleteTask(id); // Call the delete service method
        return "redirect:/api/tasks/" + userId; // Redirect back to the task list
    }








    @GetMapping("/{userId}/new")
    public ModelAndView showCreateTaskForm(@PathVariable Long userId) {
        // Fetch the current user
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch all users for the dropdown
        List<User> allUsers = userRepository.findAll();

        // Create and configure the ModelAndView
        ModelAndView modelAndView = new ModelAndView("create-task"); // The name of the HTML view
        modelAndView.addObject("user_info", currentUser); // Current user
        modelAndView.addObject("users", allUsers); // List of all users
        modelAndView.addObject("task", new Task()); // New task object for the form

        return modelAndView;
    }



    /*
    @PostMapping("/{userId}/new")
    public String createTask(@PathVariable Long userId,
                             @RequestParam Long username, // This should be the user ID
                             @RequestParam String title,
                             @RequestParam String description,
                             @RequestParam TaskStatus status) { // Use TaskStatus enum

        // Log the received username for debugging
        System.out.println("Received username: " + username);
        System.out.println("Received username: " + title);
        System.out.println("Received username: " + description);
        System.out.println("Received username: " + status);

        // Fetch the user by ID
        User assignedUser = userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + username));


        // Log the fetched user ID
        if (assignedUser != null && assignedUser.getId() != null) {
            System.out.println("Assigned User ID: " + assignedUser.getId());
        } else {
            throw new IllegalArgumentException("Assigned user ID is null or user not found");
        }


        // Create a new task and associate it with the user
        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setDescription(description);
        newTask.setStatus(status);
        newTask.setUser(assignedUser); // Set the user

        // Save the task
        taskService.saveTask(newTask);

        return "redirect:/api/tasks/" + userId; // Redirect to a relevant page
    }

     */

    @PostMapping("/{userId}/new")
    public String createTask(@PathVariable Long userId,
                           @RequestParam Long username, // This should be the user ID
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam TaskStatus status) {
         taskService.addTask(title, description, status, username);
        return "redirect:/api/tasks/" + userId;
    }







}