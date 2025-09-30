package com.example.demo.services;

import com.example.demo.model.Photographer;
import com.example.demo.model.Request;
import com.example.demo.repositories.PhotographerRepository;
import com.example.demo.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PhotographerService {
    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TwilioSmsService twilioSmsService; // Added for SMS

    public Photographer addPhotographer(Photographer photographer) {
        return photographerRepository.save(photographer);
    }

    public List<Photographer> getAllPhotographers() {
        return photographerRepository.findAll();
    }

    public Request sendRequest(Long photographerId, String customerName, String customerEmail, String customerPhone, LocalDate requestDate) {
        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new RuntimeException("Photographer not found"));
        Request request = new Request(photographer, customerName, customerEmail, customerPhone, requestDate, "PENDING");
        request = requestRepository.save(request);

        // Send SMS to photographer
        String smsMessage = "New photography request from " + customerName + " on " + requestDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".";
        twilioSmsService.sendSms(photographer.getPhone(), smsMessage);

        // Send email to photographer (existing)
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(photographer.getEmail());
        message.setSubject("New Photography Request");
        message.setText("Dear " + photographer.getName() + ",\n\nYou have a new request from " + customerName + " on " + requestDate + ".\n\nCheck the dashboard for details!");
        mailSender.send(message);

        return request;
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public void updateRequestStatus(Long requestId, String status) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        requestRepository.save(request);

        // Send SMS to customer
        String smsMessage = "Your request with " + request.getPhotographer().getName() + " on " + request.getRequestDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " is now: " + status + ".";
        twilioSmsService.sendSms(request.getCustomerPhone(), smsMessage);

        // Send email to customer (existing)
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getCustomerEmail());
        message.setSubject("Photography Request Status Update");
        message.setText("Dear " + request.getCustomerName() + ",\n\nYour request for photographer " + request.getPhotographer().getName() +
                " on " + request.getRequestDate() + " has been marked as: " + status + ".\n\nThank you for your request!");
        mailSender.send(message);
    }
}