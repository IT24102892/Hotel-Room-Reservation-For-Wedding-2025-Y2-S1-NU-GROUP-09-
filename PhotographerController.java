package com.example.demo.controllers;

import com.example.demo.model.Photographer;
import com.example.demo.services.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class PhotographerController {
    @Autowired
    private PhotographerService photographerService;

    @GetMapping("/addPhotographer")
    public String showAddPhotographerForm(Model model) {
        model.addAttribute("photographer", new Photographer());
        return "addPhotographer";
    }

    @PostMapping("/addPhotographer")
    public String addPhotographer(@ModelAttribute Photographer photographer) {
        photographerService.addPhotographer(photographer);
        return "redirect:/viewPhotographers";
    }

    @GetMapping("/viewPhotographers")
    public String viewPhotographers(Model model) {
        model.addAttribute("photographers", photographerService.getAllPhotographers());
        return "viewPhotographers";
    }

    @GetMapping("/requestForm")
    public String showRequestForm(@RequestParam Long photographerId, Model model) {
        model.addAttribute("photographerId", photographerId);
        return "requestForm";
    }

    @PostMapping("/submitRequest")
    public String submitRequest(
            @RequestParam Long photographerId,
            @RequestParam String customerName,
            @RequestParam String customerEmail,
            @RequestParam String customerPhone, // Added for SMS
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate requestDate) {
        photographerService.sendRequest(photographerId, customerName, customerEmail, customerPhone, requestDate);
        return "redirect:/viewPhotographers";
    }

    @GetMapping("/photographerDashboard")
    public String showPhotographerDashboard(Model model) {
        model.addAttribute("requests", photographerService.getAllRequests());
        return "photographerDashboard";
    }

    @PostMapping("/updateRequestStatus")
    public String updateRequestStatus(@RequestParam Long requestId, @RequestParam String status) {
        photographerService.updateRequestStatus(requestId, status);
        return "redirect:/photographerDashboard";
    }
}