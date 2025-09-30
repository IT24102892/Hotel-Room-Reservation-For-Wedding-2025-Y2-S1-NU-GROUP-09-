package com.example.demo.services;

import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    public String authenticate(String email, String password) {
        // Check admin table
        var adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword().equals(password)) {
                return "/dashboard"; // Redirect to admin dashboard
            }
        }

        // Check user table
        var userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return "/"; // Redirect to user home page
            }
        }

        throw new IllegalArgumentException("Invalid email or password");
    }
}