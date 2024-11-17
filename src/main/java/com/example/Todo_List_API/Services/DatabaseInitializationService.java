package com.example.Todo_List_API.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

@Service
public class DatabaseInitializationService {
    private final JdbcTemplate jdbcTemplate;

    // Constructor to inject JdbcTemplate
    public DatabaseInitializationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // This method will run after the Spring Boot application context is initialized
    @PostConstruct
    public void runSqlFile() {
        // Path to the SQL file in resources
        String sqlFilePath = "src/main/resources/templates/insert_data.sql";

        try {
            // Read the contents of the SQL file
            String sql = new String(Files.readAllBytes(Paths.get(sqlFilePath)));

            // Execute the SQL script using JdbcTemplate
            jdbcTemplate.execute(sql);

            System.out.println("SQL file executed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
