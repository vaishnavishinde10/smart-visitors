package com.example.svms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.svms.model.Appointment;
import com.example.svms.model.Visitor;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	List<Appointment> findByVisitor(Visitor visitor);
}
