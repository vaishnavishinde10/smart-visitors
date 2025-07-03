package com.example.svms.dto;



public class AppointmentRequestDTO {
    private Long visitorId;
    private Long hostId;
    private String scheduledAt; // ISO format (e.g. "2025-07-01T10:30")
	public Long getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(Long visitorId) {
		this.visitorId = visitorId;
	}
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	public String getScheduledAt() {
		return scheduledAt;
	}
	public void setScheduledAt(String scheduledAt) {
		this.scheduledAt = scheduledAt;
	}
	@Override
	public String toString() {
		return "AppointmentRequestDTO [visitorId=" + visitorId + ", hostId=" + hostId + ", scheduledAt=" + scheduledAt
				+ "]";
	}

  
}

