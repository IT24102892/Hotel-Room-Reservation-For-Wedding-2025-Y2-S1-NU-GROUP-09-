package com.example.demo.controllers;

import com.example.demo.model.HallsPayment;
import com.example.demo.model.RoomsHalls;
import com.example.demo.services.BookingService;
import com.example.demo.services.RoomsHallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/api/rooms-halls")
public class RoomsHallsController {

    private static final Logger LOGGER = Logger.getLogger(RoomsHallsController.class.getName());

    @Autowired
    private RoomsHallsService roomsHallsService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/add")
    public String showAddProductPage() {
        return "add-product";
    }

    @GetMapping("/view")
    public String showViewRoomsHallsPage() {
        return "viewroomsandhalls";
    }

    @GetMapping("/user-view")
    public String showUserViewRoomsHallsPage() {
        return "userviewroomsandhalls";
    }

    @GetMapping("/edit/{id}")
    public String showEditRoomOrHallPage(@PathVariable Long id) {
        return "editroomsandhalls";
    }

    @GetMapping
    @ResponseBody
    public Iterable<RoomsHalls> getAllRoomsHalls(@RequestParam(required = false) String type,
                                                 @RequestParam(required = false) Boolean aircondition) {
        return roomsHallsService.getAllRoomsHalls(type, aircondition);
    }

    @GetMapping("/user")
    @ResponseBody
    public Iterable<RoomsHalls> getAllRoomsHallsForUsers() {
        return roomsHallsService.getAllRoomsHallsForUsers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public RoomsHalls getRoomOrHallById(@PathVariable Long id) {
        return roomsHallsService.getRoomOrHallById(id);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addRoomOrHall(@RequestBody RoomsHalls roomsHalls) {
        try {
            RoomsHalls savedRoomOrHall = roomsHallsService.addRoomOrHall(roomsHalls);
            return ResponseEntity.ok(savedRoomOrHall);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateRoomOrHall(@PathVariable Long id, @RequestBody RoomsHalls roomsHalls) {
        try {
            roomsHalls.setId(id);
            RoomsHalls updatedRoomOrHall = roomsHallsService.updateRoomOrHall(roomsHalls);
            return ResponseEntity.ok(updatedRoomOrHall);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRoomOrHall(@PathVariable Long id) {
        try {
            roomsHallsService.deleteRoomOrHall(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/book/{id}")
    public String showBookingPage(@PathVariable Long id, Model model) {
        LOGGER.info("Received ID: " + id);
        if (id == null) {
            model.addAttribute("errorMessage", "No hall ID provided. Please select a valid room or hall to book.");
            return "error";
        }
        try {
            RoomsHalls roomOrHall = roomsHallsService.getRoomOrHallById(id);
            if (!roomOrHall.isAvailable()) {
                model.addAttribute("errorMessage", "The selected hall or room is not available for booking.");
                return "error";
            }
            return "hallsbooking";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "No hall or room found with ID: " + id + ". Please select a valid room or hall.");
            return "error";
        }
    }

    @GetMapping("/payment")
    public String showPaymentPage(@RequestParam(required = false) Long roomId, Model model) {
        LOGGER.info("Received roomId for payment: " + roomId);
        if (roomId == null) {
            model.addAttribute("errorMessage", "No hall ID provided. Please select a valid room or hall to book.");
            return "error";
        }
        try {
            RoomsHalls roomOrHall = roomsHallsService.getRoomOrHallById(roomId);
            if (!roomOrHall.isAvailable()) {
                model.addAttribute("errorMessage", "The selected hall or room is not available for booking.");
                return "error";
            }
            model.addAttribute("room", roomOrHall);
            return "payment";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "No hall or room found with ID: " + roomId + ". Please select a valid room or hall.");
            return "error";
        }
    }

    @PostMapping("/book")
    @ResponseBody
    public ResponseEntity<?> bookAndPay(@RequestBody HallsPayment hallsPayment) {
        try {
            HallsPayment saved = bookingService.bookAndPay(hallsPayment);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    // Error response class
    static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}