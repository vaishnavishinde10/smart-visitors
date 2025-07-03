package com.example.svms.service;

import com.example.svms.dto.VisitorDTO;
import com.example.svms.model.Visitor;
import com.example.svms.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    // Create Visitor
    public VisitorDTO createVisitor(VisitorDTO dto) {
        Visitor visitor = new Visitor();
        visitor.setName(dto.getName());
        visitor.setEmail(dto.getEmail());
        visitor.setPhone(dto.getPhone());
        visitor.setCompany(dto.getCompany()); // ✅ include company

        Visitor saved = visitorRepository.save(visitor);

        VisitorDTO response = new VisitorDTO();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setPhone(saved.getPhone());
        response.setCompany(saved.getCompany());
        return response;
    }

    // ✅ Get all Visitors
    @PreAuthorize("hasRole('ADMIN')")

    public List<VisitorDTO> getAllVisitors() {
        List<Visitor> visitors = visitorRepository.findAll();
        return visitors.stream().map(v -> {
            VisitorDTO dto = new VisitorDTO();
            dto.setId(v.getId());
            dto.setName(v.getName());
            dto.setEmail(v.getEmail());
            dto.setPhone(v.getPhone());
            dto.setCompany(v.getCompany());
            return dto;
        }).collect(Collectors.toList());
    }
}
