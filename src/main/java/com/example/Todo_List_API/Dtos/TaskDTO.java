package com.example.Todo_List_API.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private String username;
    private String userRole;
    private String status;  // New field to store the status

    // Constructors, getters, and setters

    public TaskDTO(Long id, String title, String description, Long userId, String username, String userRole, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.username = username;
        this.userRole = userRole;
        this.status = status;  // Assign status to the DTO constructor
    }
}
