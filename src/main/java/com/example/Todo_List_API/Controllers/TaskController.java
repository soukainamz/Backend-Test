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
        return modelAndView;

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



    // delete task
    @PostMapping("/{userId}/delete/{id}")
    public String deleteTask(@PathVariable Long userId, @PathVariable Long id) {
        taskService.deleteTask(id); // Call the delete service method
        return "redirect:/api/tasks/" + userId; // Redirect back to the task list
    }


    // create task
    @GetMapping("/{userId}/new")
    public ModelAndView showCreateTaskForm(@PathVariable Long userId) {

        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch all users for the dropdown
        List<User> allUsers = userRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("create-task"); // The name of the HTML view
        modelAndView.addObject("user_info", currentUser); // Current user
        modelAndView.addObject("users", allUsers); // List of all users
        modelAndView.addObject("task", new Task()); // New task object for the form

        return modelAndView;
    }


    // save created task
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
