package com.example.diplomeBackend.dto;

import java.util.Date;
import java.util.Set;

public class ActorDTO {

    private String name;
    private String bio;
    private Date dateOfBirth;
    private Set<Long> movieIds;

    public ActorDTO(){

    }

    public ActorDTO (String name, String bio, Date dateOfBirth, Set<Long> movieIds) {
        this.name = name;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
        this.movieIds = movieIds;
    }


    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Long> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(Set<Long> movieIds) {
        this.movieIds = movieIds;
    }
}
