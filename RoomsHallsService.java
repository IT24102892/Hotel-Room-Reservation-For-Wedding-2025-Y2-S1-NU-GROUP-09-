package com.example.demo.services;

import com.example.demo.model.RoomsHalls;
import com.example.demo.repositories.RoomsHallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomsHallsService {

    @Autowired
    private RoomsHallsRepository roomsHallsRepository;

    public RoomsHalls addRoomOrHall(RoomsHalls roomsHalls) {
        if (roomsHalls.getTypes() == null || !roomsHalls.getTypes().matches("rooms|halls")) {
            throw new IllegalArgumentException("Type must be 'rooms' or 'halls'");
        }
        if (roomsHalls.getImage() == null || roomsHalls.getImage().trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL is required");
        }
        if (roomsHalls.getTypes().equals("rooms") && (roomsHalls.getNumber() != 1 && roomsHalls.getNumber() != 2)) {
            throw new IllegalArgumentException("Room capacity must be 1 or 2");
        }
        if (roomsHalls.getTypes().equals("halls") && (roomsHalls.getNumber() < 100 || roomsHalls.getNumber() > 1000)) {
            throw new IllegalArgumentException("Hall capacity must be between 100 and 1000");
        }
        if (roomsHalls.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        return roomsHallsRepository.save(roomsHalls);
    }

    public List<RoomsHalls> getAllRoomsHalls(String type, Boolean availability) {
        if (type != null && availability != null) {
            return roomsHallsRepository.findByTypesAndAvailableOrderByPriceDesc(type, availability); // Updated
        } else if (type != null) {
            return roomsHallsRepository.findByTypesOrderByPriceDesc(type);
        } else if (availability != null) {
            return roomsHallsRepository.findByAvailableOrderByPriceDesc(availability); // Updated
        }
        return roomsHallsRepository.findAllByOrderByPriceDesc();
    }

    public List<RoomsHalls> getAllRoomsHallsForUsers() {
        return roomsHallsRepository.findAllByOrderByPriceDesc().stream()
                .filter(RoomsHalls::isAvailable) // Use isAvailable() for consistency
                .collect(Collectors.toList());
    }

    public RoomsHalls getRoomOrHallById(Long id) {
        return roomsHallsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room or Hall not found with ID: " + id));
    }

    public RoomsHalls updateRoomOrHall(RoomsHalls roomsHalls) {
        if (roomsHalls.getId() == null) {
            throw new IllegalArgumentException("ID is required");
        }
        if (roomsHalls.getTypes() == null || !roomsHalls.getTypes().matches("rooms|halls")) {
            throw new IllegalArgumentException("Type must be 'rooms' or 'halls'");
        }
        if (roomsHalls.getImage() == null || roomsHalls.getImage().trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL is required");
        }
        if (roomsHalls.getTypes().equals("rooms") && (roomsHalls.getNumber() != 1 && roomsHalls.getNumber() != 2)) {
            throw new IllegalArgumentException("Room capacity must be 1 or 2");
        }
        if (roomsHalls.getTypes().equals("halls") && (roomsHalls.getNumber() < 100 || roomsHalls.getNumber() > 1000)) {
            throw new IllegalArgumentException("Hall capacity must be between 100 and 1000");
        }
        if (roomsHalls.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        return roomsHallsRepository.save(roomsHalls);
    }

    public void deleteRoomOrHall(Long id) {
        if (!roomsHallsRepository.existsById(id)) {
            throw new IllegalArgumentException("Room or Hall not found with ID: " + id);
        }
        roomsHallsRepository.deleteById(id);
    }
}