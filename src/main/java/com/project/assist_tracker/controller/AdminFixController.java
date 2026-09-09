package com.project.assist_tracker.controller;

import com.project.assist_tracker.model.User;
import com.project.assist_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminFixController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/create-admin")
    public String createAdmin() {
        try {
            userRepository.deleteAll();
            
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRole("ADMIN");

            userRepository.save(admin);
            
            return "SUCCESS: admin/admin created! Total users: " + userRepository.count();
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED: " + e.getMessage();
        }
    }
}