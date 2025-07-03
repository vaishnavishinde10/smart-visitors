package com.example.svms.controller;

import com.example.svms.dto.AppointmentRequestDTO;
import com.example.svms.dto.AppointmentResponseDTO;
import com.example.svms.service.AppointmentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Allow only ADMIN, RECEPTIONIST, HOST to create appointments
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'HOST')")
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO response = appointmentService.createAppointment(dto);
        return ResponseEntity.ok(response);
    }
    
    @PreAuthorize("hasRole('VISITOR')")
    @GetMapping("/my")
    public ResponseEntity<List<AppointmentResponseDTO>> getMyAppointments(Authentication authentication) {
        String username = authentication.getName(); // Extract username from token
        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsForVisitor(username);
        return ResponseEntity.ok(appointments);
    }
}
