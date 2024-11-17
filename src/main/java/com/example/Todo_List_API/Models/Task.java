package com.example.Todo_List_API.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)  // Store the enum as a string in the database
    private TaskStatus status;

    @ManyToOne(cascade = CascadeType.ALL)  // Cascade all operations (persist, merge) to User
    @JoinColumn(name = "user_id")
    private User user;
}
