package com.example.demo.controllers;

import com.example.demo.model.Hotelbooking;
import com.example.demo.model.Payment;
import com.example.demo.model.WedingBooking;
import com.example.demo.model.Wedingpackage;
import com.example.demo.services.HotelbookingService;
import com.example.demo.services.PaymentService;
import com.example.demo.services.WedingBookingService;
import com.example.demo.services.WedingpackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/wedingpackage")
public class WedingpackageController {

    @Autowired
    private WedingpackageService wedingpackageService;

    @Autowired
    private WedingBookingService wedingBookingService;

    @Autowired
    private HotelbookingService hotelbookingService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String showCreatePackagePage() {
        return "createpackage";
    }

    @GetMapping("/view")
    public String showViewPackagesPage() {
        return "ViewPackages";
    }

    @GetMapping("/editanddelete")
    public String showEditAndDeletePackagesPage() {
        return "editanddeletepackages";
    }

    @GetMapping("/booking")
    public String showBookingPage() {
        return "booking";
    }

    @GetMapping("/hotelbooking")
    public String showHotelBookingPage() {
        return "hotelbooking";
    }

    @GetMapping("/payweding")
    public String showPayWedingPage() {
        return "payweding";
    }

    @GetMapping
    @ResponseBody
    public List<Wedingpackage> getAllWedingpackages() {
        return (List<Wedingpackage>) wedingpackageService.getAllWedingpackages();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getWedingpackageById(@PathVariable String id) {
        try {
            Wedingpackage packageDetails = wedingpackageService.getWedingpackageById(Long.valueOf(id));
            if (packageDetails == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(packageDetails);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid ID format: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateWedingpackage(@PathVariable String id, @RequestBody Wedingpackage wedingpackage) {
        try {
            wedingpackage.setId(Long.valueOf(id));
            Wedingpackage updatedPackage = wedingpackageService.addWedingpackage(wedingpackage);
            return ResponseEntity.ok(updatedPackage);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid ID format: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteWedingpackage(@PathVariable String id) {
        try {
            wedingpackageService.deleteWedingpackage(Long.valueOf(id));
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid ID format: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addWedingpackage(@RequestBody Wedingpackage wedingpackage) {
        try {
            Wedingpackage savedPackage = wedingpackageService.addWedingpackage(wedingpackage);
            return ResponseEntity.ok(savedPackage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/book")
    public WedingBooking bookPackage(@RequestBody WedingBooking wedingBooking) {
        return wedingBookingService.addWedingBooking(wedingBooking);
    }

    @PostMapping("/hotelbook")
    @ResponseBody
    public ResponseEntity<?> bookHotel(@RequestBody Hotelbooking hotelbooking) {
        try {
            Hotelbooking savedBooking = hotelbookingService.addHotelBooking(hotelbooking);
            return ResponseEntity.ok(savedBooking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/payweding")
    @ResponseBody
    public ResponseEntity<?> processPayment(@RequestBody Payment payment) {
        try {
            payment.setTimestamp(LocalDateTime.now());
            Payment savedPayment = paymentService.addPayment(payment);
            return ResponseEntity.ok(savedPayment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}