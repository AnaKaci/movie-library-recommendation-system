package com.example.diplomeBackend.dto;

import java.util.Date;

public class WatchlistDTO {
    private Long id;
    private Long userId;
    private Long movieId;

    private Date dateAdded;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public Date getDateAdded(){ return dateAdded; }
    public void setDateAdded(Date dateAdded){ this.dateAdded = dateAdded; }

}
