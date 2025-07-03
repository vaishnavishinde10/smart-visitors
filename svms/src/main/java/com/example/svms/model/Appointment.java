package com.example.svms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Visitor visitor;

    @ManyToOne
    private User host;

    private LocalDateTime scheduledAt;
    private String status; // PENDING, APPROVED, COMPLETED
    
    public Appointment(){}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Visitor getVisitor() {
		return visitor;
	}
	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	public User getHost() {
		return host;
	}
	public void setHost(User host) {
		this.host = host;
	}
	public LocalDateTime getScheduledAt() {
		return scheduledAt;
	}
	public void setScheduledAt(LocalDateTime scheduledAt) {
		this.scheduledAt = scheduledAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Appointment [id=" + id + ", visitor=" + visitor + ", host=" + host + ", scheduledAt=" + scheduledAt
				+ ", status=" + status + "]";
	}
	public Appointment(Long id, Visitor visitor, User host, LocalDateTime scheduledAt, String status) {
		super();
		this.id = id;
		this.visitor = visitor;
		this.host = host;
		this.scheduledAt = scheduledAt;
		this.status = status;
	}
    
    
}
