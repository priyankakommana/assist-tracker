package com.project.assist_tracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccessRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String requestedUsername;
    private String email;
    private String reason;
    private String status = "PENDING";
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getRequestedUsername() { return requestedUsername; }
    public void setRequestedUsername(String requestedUsername) { this.requestedUsername = requestedUsername; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}