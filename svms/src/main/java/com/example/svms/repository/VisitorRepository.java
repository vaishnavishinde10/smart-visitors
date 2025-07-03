package com.example.svms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.svms.model.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
	
	Optional<Visitor> findByEmail(String email);
}
