package com.example.diplomeBackend.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Actor {

    @Id
    @Column(unique = true, nullable = false)
    private String actorName;

    @Column(length = 5000)
    private String bio;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;


    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    // Constructors, getters, and setters

    public Actor() {
    }

    public Actor(String actorName){
        this.actorName = actorName;
    }

    public Actor(String actorName, String bio, Date dateOfBirth) {
        this.actorName = actorName;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
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

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
