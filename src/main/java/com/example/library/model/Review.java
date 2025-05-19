package com.example.library.model;

import java.time.LocalDateTime;

public class Review {
    private Long id;
    private Long userId;
    private Long bikeId;
    private int rating;
    private String feedbackText;
    private LocalDateTime timestamp;

    public Review() {}

    public Review(Long id, Long userId, Long bikeId, int rating, String feedbackText, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.bikeId = bikeId;
        this.rating = rating;
        this.feedbackText = feedbackText;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getBikeId() { return bikeId; }
    public void setBikeId(Long bikeId) { this.bikeId = bikeId; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getFeedbackText() { return feedbackText; }
    public void setFeedbackText(String feedbackText) { this.feedbackText = feedbackText; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}