package com.example.diplomeBackend.dto;

public class FeedbackDTO {
    private Long userId;
    private String username;
    private Long movieId;
    private String feedbackText;
    private int feedbackRating;

    public FeedbackDTO(){

    }

    public FeedbackDTO(Long userId, Long movieId, String username, String text, int rating){
        this.userId = userId;
        this.movieId = movieId;
        this.username = username;
        this.feedbackText = text;
        this.feedbackRating = rating;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public int getFeedbackRating(){ return feedbackRating; }

    public void setFeedbackRating(int rating){ this.feedbackRating = rating; }

}
