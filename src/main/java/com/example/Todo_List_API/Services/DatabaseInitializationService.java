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


    public DatabaseInitializationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostConstruct
    public void runSqlFile() {
        String sqlFilePath = "src/main/resources/templates/insert_data.sql";

        try {

            String sql = new String(Files.readAllBytes(Paths.get(sqlFilePath)));

            jdbcTemplate.execute(sql);

            System.out.println("SQL file executed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
