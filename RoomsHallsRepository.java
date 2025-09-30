package com.example.demo.repositories;

import com.example.demo.model.RoomsHalls;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomsHallsRepository extends CrudRepository<RoomsHalls, Long> {
    List<RoomsHalls> findByTypes(String types);
    List<RoomsHalls> findByAvailable(boolean available); // Updated from findByAvailability
    List<RoomsHalls> findByTypesAndAvailable(String types, boolean available); // Updated from findByTypesAndAvailability
    List<RoomsHalls> findAllByOrderByPriceDesc();
    List<RoomsHalls> findByTypesOrderByPriceDesc(String types);
    List<RoomsHalls> findByAvailableOrderByPriceDesc(boolean available); // Updated from findByAvailabilityOrderByPriceDesc
    List<RoomsHalls> findByTypesAndAvailableOrderByPriceDesc(String types, boolean available); // Updated from findByTypesAndAvailabilityOrderByPriceDesc
}