package com.example.demo.services;

import com.example.demo.model.Admin;
import com.example.demo.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin createAdmin(Admin admin) {
        // Basic validation
        if (admin.getName() == null || admin.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (admin.getEmail() == null || admin.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        // Check if email already exists
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        return adminRepository.save(admin);
    }
}