package com.example.demo.repositories;

import com.example.demo.model.Admin;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}