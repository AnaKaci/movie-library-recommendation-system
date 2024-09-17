package com.example.diplomeBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
    @Entity
    public class Movie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long movieId;


        @Column(unique = true, nullable = false)
        private String title;

        @Column(length = 1000)
        private String description;

        @Temporal(TemporalType.DATE)
        private Date releaseDate;

        private int duration;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        @JoinColumn(name = "director_name")
        private Director director;

        private String poster;

        private String trailer;

        private Double averageRating;


        @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
        private Set<Feedback> feedbacks = new HashSet<>();


        @ManyToMany
        @JoinTable(
                name = "movie_genre",
                joinColumns = @JoinColumn(name = "movie_id"),
                inverseJoinColumns = @JoinColumn(name = "genre_name"))
        private Set<Genre> genres = new HashSet<>();

        @ManyToMany
        @JoinTable(
                name = "movie_actor",
                joinColumns = @JoinColumn(name = "movie_id"),
                inverseJoinColumns = @JoinColumn(name = "actor_name"))
        private Set<Actor> actors = new HashSet<>();

        public Movie(String title, String description, Date releaseDate, int duration, Director director,
                     String poster, String trailer, Double averageRating, Set<Genre> genres, Set<Actor> actors) {
            this.title = title;
            this.description = description;
            this.releaseDate = releaseDate;
            this.duration = duration;
            this.director = director;
            this.poster = poster;
            this.trailer = trailer;
            this.averageRating = averageRating;
            this.genres = genres;
            this.actors = actors;
        }

        public Movie() {

        }

        public Movie(Long movieId) {
            this.movieId = movieId;
        }

        // Getters and setters

        public Long getMovieId() {
            return movieId;
        }

        public void setMovieId(Long movieId) {
            this.movieId = movieId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Date getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public Director getDirector() {
            return director;
        }

        public void setDirector(Director director) {
            this.director = director;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getTrailer() {
            return trailer;
        }

        public void setTrailer(String trailer) {
            this.trailer = trailer;
        }

        public Double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(Double averageRating) {
            this.averageRating = averageRating;
        }

        public Set<Feedback> getFeedbacks() {
            return feedbacks;
        }

        public void setFeedbacks(Set<Feedback> feedbacks) {
            this.feedbacks = feedbacks;
        }


        public Set<Genre> getGenres() {
            return genres;
        }

        public void setGenres(Set<Genre> genres) {
            this.genres = genres;
        }

        public Set<Actor> getActors() {
            return actors;
        }

        public void setActors(Set<Actor> actors) {
            this.actors = actors;
        }
    }






