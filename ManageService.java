package com.example.demo.services;

import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Iterable<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        // Check for duplicate email
        if (userRepository.findByEmail(user.getEmail()).isPresent() &&
                !userRepository.findByEmail(user.getEmail()).get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Preserve existing password if not provided
        if (user.getPassword() == null) {
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.setPassword(existingUser.getPassword());
        }
        return userRepository.save(user);
    }

    public Admin updateAdmin(Admin admin) {
        if (admin.getId() == null) {
            throw new IllegalArgumentException("Admin ID is required");
        }
        if (admin.getName() == null || admin.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (admin.getEmail() == null || admin.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        // Check for duplicate email
        if (adminRepository.findByEmail(admin.getEmail()).isPresent() &&
                !adminRepository.findByEmail(admin.getEmail()).get().getId().equals(admin.getId())) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Preserve existing password if not provided
        if (admin.getPassword() == null) {
            Admin existingAdmin = adminRepository.findById(admin.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Admin not found"));
            admin.setPassword(existingAdmin.getPassword());
        }
        return adminRepository.save(admin);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }

    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new IllegalArgumentException("Admin not found");
        }
        adminRepository.deleteById(id);
    }
}