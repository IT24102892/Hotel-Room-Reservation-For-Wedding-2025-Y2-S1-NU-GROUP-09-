package com.example.demo.repositories;

import com.example.demo.model.Calendarview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CalendarviewRepository extends CrudRepository<Calendarview, Long> {
    List<Calendarview> findByWedingtype(String wedingtype);
    List<Calendarview> findByDate(String date);
    List<Calendarview> findByDateContaining(String month);
}