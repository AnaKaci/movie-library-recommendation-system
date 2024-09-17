package com.example.diplomeBackend.models;

import jakarta.persistence.*;

import java.util.Date;

    @Entity
    public class Feedback {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long feedbackId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "movie_id")
        private Movie movie;

        @Column(length = 1000)
        private String feedbackText;

        private int feedbackRating;

        @Temporal(TemporalType.TIMESTAMP)
        private Date feedbackDate;

        // Getters and setters

        public Long getFeedbackId() {
            return feedbackId;
        }

        public void setFeedbackId(Long feedbackId) {
            this.feedbackId = feedbackId;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Movie getMovie() {
            return movie;
        }

        public void setMovie(Movie movie) {
            this.movie = movie;
        }

        public String getFeedbackText() {
            return feedbackText;
        }

        public void setFeedbackText(String feedbackText) {
            this.feedbackText = feedbackText;
        }

        public int getFeedbackRating(){ return feedbackRating; }

        public void setFeedbackRating(int rating){ this.feedbackRating = rating; }

        public Date getFeedbackDate() {
            return feedbackDate;
        }

        public void setFeedbackDate(Date feedbackDate) {
            this.feedbackDate = feedbackDate;
        }
    }









