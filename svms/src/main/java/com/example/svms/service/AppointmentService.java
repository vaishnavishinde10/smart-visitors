package com.example.svms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.svms.dto.AppointmentRequestDTO;
import com.example.svms.dto.AppointmentResponseDTO;
import com.example.svms.model.Appointment;
import com.example.svms.model.User;
import com.example.svms.model.Visitor;
import com.example.svms.repository.AppointmentRepository;
import com.example.svms.repository.UserRepository;
import com.example.svms.repository.VisitorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;


    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        // ✅ Use findById with orElseThrow here
        Optional<Visitor> optionalVisitor = visitorRepository.findById(dto.getVisitorId());
        Visitor visitor = optionalVisitor.orElseThrow(() -> new RuntimeException("Visitor not found"));

        Optional<User> optionalHost = userRepository.findById(dto.getHostId());
        User host = optionalHost.orElseThrow(() -> new RuntimeException("Host (user) not found"));

        Appointment appointment = new Appointment();
        appointment.setVisitor(visitor);
        appointment.setHost(host);
        appointment.setScheduledAt(LocalDateTime.parse(dto.getScheduledAt()));
        appointment.setStatus("PENDING");

        Appointment saved = appointmentRepository.save(appointment);
       
        emailService.sendEmail(
            host.getEmail(),
            "New Appointment Scheduled",
            "Visitor " + visitor.getName() + " has booked an appointment."
        );
        
        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setId(saved.getId());
        response.setVisitorName(visitor.getName());
        response.setHostName(host.getUsername());
        response.setStatus(saved.getStatus());
        response.setScheduledAt(saved.getScheduledAt().toString());

       
        
        
        return response;

    }
    
    public List<AppointmentResponseDTO> getAppointmentsForVisitor(String username) {
        Visitor visitor = visitorRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("Visitor not found"));

        List<Appointment> appointments = appointmentRepository.findByVisitor(visitor);

        return appointments.stream().map(appointment -> {
            AppointmentResponseDTO dto = new AppointmentResponseDTO();
            dto.setId(appointment.getId());
            dto.setVisitorName(visitor.getName());
            dto.setHostName(appointment.getHost().getUsername());
            dto.setStatus(appointment.getStatus());
            dto.setScheduledAt(appointment.getScheduledAt().toString());
            return dto;
        }).toList();
    }

}
