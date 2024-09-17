package com.example.diplomeBackend.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MovieDTO {
    private Long movieId;
    private String title;
    private String description;
    private Date releaseDate;
    private int duration;
    private String directorName;
    private String poster;
    private String trailer;
    private Double averageRating;
    private Set<String> genreNames = new HashSet<>();
    private Set<String> actorNames = new HashSet<>();

    // Getters and Setters
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

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
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

    public Set<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(Set<String> genreNames) {
        this.genreNames = genreNames;
    }

    public Set<String> getActorNames() {
        return actorNames;
    }

    public void setActorNames(Set<String> actorNames) {
        this.actorNames = actorNames;
    }
}
