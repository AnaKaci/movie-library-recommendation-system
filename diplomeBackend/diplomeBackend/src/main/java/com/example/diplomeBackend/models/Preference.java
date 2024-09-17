package com.example.diplomeBackend.models;

import jakarta.persistence.*;

@Entity
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_name")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_name")
    private Actor actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_name")
    private Director director;

    private String preferenceType;

    // Constructors, getters, and setters

    public Preference() {
    }

    public Preference(User user, Genre genre, Actor actor, Director director, String preferenceType) {
        this.user = user;
        this.genre = genre;
        this.actor = actor;
        this.director = director;
        this.preferenceType = preferenceType;
    }

    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public String getPreferenceType() {
        return preferenceType;
    }

    public void setPreferenceType(String preferenceType) {
        if (!"FAVORITE".equals(preferenceType) && !"DISLIKED".equals(preferenceType)) {
            throw new IllegalArgumentException("Invalid preference type: " + preferenceType);
        }
        this.preferenceType = preferenceType;
    }
}

