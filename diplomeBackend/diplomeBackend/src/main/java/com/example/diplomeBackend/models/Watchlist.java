package com.example.diplomeBackend.models;
import jakarta.persistence.*;
import java.util.Date;

    @Entity
    public class Watchlist {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long watchlistId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "movie_id")
        private Movie movie;

        @Temporal(TemporalType.TIMESTAMP)
        private Date dateAdded;

        // Constructors, getters, and setters

        public Watchlist() {
        }

        public Watchlist(User user, Movie movie, Date dateAdded) {
            this.user = user;
            this.movie = movie;
            this.dateAdded = dateAdded;
        }

        public Long getWatchlistId() {
            return watchlistId;
        }

        public void setWatchlistId(Long watchlistId) {
            this.watchlistId = watchlistId;
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

        public Date getDateAdded() {
            return dateAdded;
        }

        public void setDateAdded(Date dateAdded) {
            this.dateAdded = dateAdded;
        }
    }

