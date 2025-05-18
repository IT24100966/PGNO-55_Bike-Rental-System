package com.bikerental.pgno55_bikerentalsystem.model;

import java.time.LocalDateTime;

public class ActivityLog {
    private int id;
    private String action;
    private String details;
    private String performedBy;
    private LocalDateTime createdAt;

    public ActivityLog() {}

    public ActivityLog(int id, String action, String details, String performedBy, LocalDateTime createdAt) {
        this.id = id;
        this.action = action;
        this.details = details;
        this.performedBy = performedBy;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}