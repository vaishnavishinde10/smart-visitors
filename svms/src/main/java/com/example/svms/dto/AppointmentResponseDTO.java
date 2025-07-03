package com.example.svms.dto;



public class AppointmentResponseDTO {
    private Long id;
    private String visitorName;
    private String hostName;
    private String status;
    private String scheduledAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getScheduledAt() {
		return scheduledAt;
	}
	public void setScheduledAt(String scheduledAt) {
		this.scheduledAt = scheduledAt;
	}
	@Override
	public String toString() {
		return "AppointmentResponseDTO [id=" + id + ", visitorName=" + visitorName + ", hostName=" + hostName
				+ ", status=" + status + ", scheduledAt=" + scheduledAt + "]";
	}

    
}
