package com.example.demo.controllers;

import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.services.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/manage")
public class ManageController {

    @Autowired
    private ManageService manageService;

    @GetMapping
    public String showManagePage() {
        return "manage"; // Maps to manage.html in src/main/resources/static
    }

    @GetMapping("/users")
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return manageService.getAllUsers();
    }

    @GetMapping("/admins")
    @ResponseBody
    public Iterable<Admin> getAllAdmins() {
        return manageService.getAllAdmins();
    }

    @PutMapping("/users")
    @ResponseBody
    public User updateUser(@RequestBody User user) {
        return manageService.updateUser(user);
    }

    @PutMapping("/admins")
    @ResponseBody
    public Admin updateAdmin(@RequestBody Admin admin) {
        return manageService.updateAdmin(admin);
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable Long id) {
        manageService.deleteUser(id);
    }

    @DeleteMapping("/admins/{id}")
    @ResponseBody
    public void deleteAdmin(@PathVariable Long id) {
        manageService.deleteAdmin(id);
    }
}