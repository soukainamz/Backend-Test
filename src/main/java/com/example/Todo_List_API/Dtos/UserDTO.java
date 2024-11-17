package com.example.Todo_List_API.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String role;
    private Long companyId;

    // Constructor
    public UserDTO(Long id, String username, String role, Long companyId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.companyId = companyId;
    }

    // Default constructor (for frameworks like Spring)
    public UserDTO() {
    }
}
