// PayHallsRepository.java (new)
package com.example.demo.repositories;

import com.example.demo.model.PayHalls;
import org.springframework.data.repository.CrudRepository;

public interface PayHallsRepository extends CrudRepository<PayHalls, Long> {
}