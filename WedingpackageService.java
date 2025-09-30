package com.example.demo.services;

import                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      com.example.demo.model.Wedingpackage;
import com.example.demo.repositories.WedingpackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class                                                                                                                                                                                                                                                                                                                                                                                                                                                        WedingpackageService {

    @Autowired
    private WedingpackageRepository wedingpackageRepository;

    public Wedingpackage addWedingpackage(Wedingpackage wedingpackage) {
        // Validation
        if (wedingpackage.getPackageName() == null || wedingpackage.getPackageName().isEmpty()) {
            throw new IllegalArgumentException("Package name is required");
        }
        if (wedingpackage.getHallprice() == null || !wedingpackage.getHallprice().matches("\\$\\d+")) {
            throw new IllegalArgumentException("Hall price must be in format '$ followed by digits'");
        }
        if (wedingpackage.getFoodtype() == null || !wedingpackage.getFoodtype().matches("veg|nonveg")) {
            throw new IllegalArgumentException("Food type must be 'veg' or 'nonveg'");
        }
        if (wedingpackage.getPersons() <= 0) {
            throw new IllegalArgumentException("Number of persons must be positive");
        }
        return wedingpackageRepository.save(wedingpackage);
    }

    public List<Wedingpackage> getAllWedingpackages() {
        return (List<Wedingpackage>) wedingpackageRepository.findAll();
    }

    public Wedingpackage getWedingpackageById(Long id) {
        Optional<Wedingpackage> packageOptional = wedingpackageRepository.findById(id);
        return packageOptional.orElse(null);
    }

    public void deleteWedingpackage(Long id) {
        wedingpackageRepository.deleteById(id);
    }
}