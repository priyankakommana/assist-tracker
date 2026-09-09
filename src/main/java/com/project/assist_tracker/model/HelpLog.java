package com.project.assist_tracker.model;

import jakarta.persistence.*;

@Entity
public class HelpLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String helperName;
    private String createdBy; // KOTHA FIELD
    private String helpedTo;
    private String issueType;
    private String description;
    private int timeSpentMinutes;

    // Default Constructor
    public HelpLog() {}

    // Getters and Setters - IVI MOTHAM UNDALI, OKATI POINA ERROR VASTUNDI
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHelperName() { return helperName; }
    public void setHelperName(String helperName) { this.helperName = helperName; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getHelpedTo() { return helpedTo; }
    public void setHelpedTo(String helpedTo) { this.helpedTo = helpedTo; }

    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getTimeSpentMinutes() { return timeSpentMinutes; }
    public void setTimeSpentMinutes(int timeSpentMinutes) { this.timeSpentMinutes = timeSpentMinutes; }
}