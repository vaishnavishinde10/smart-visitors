package com.example.svms.controller;

import com.example.svms.dto.VisitorDTO;
import com.example.svms.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@CrossOrigin(origins = "*")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    // Allow RECEPTIONIST and ADMIN to add visitor
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PostMapping("/all")
    public ResponseEntity<VisitorDTO> createVisitor(@RequestBody VisitorDTO visitorDto) {
        VisitorDTO createdVisitor = visitorService.createVisitor(visitorDto);
        return ResponseEntity.ok(createdVisitor);
    }
}