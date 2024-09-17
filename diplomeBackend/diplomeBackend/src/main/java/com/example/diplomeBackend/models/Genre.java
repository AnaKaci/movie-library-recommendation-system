package com.example.diplomeBackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "genre", uniqueConstraints = {@UniqueConstraint(columnNames = "genreName")})
public class Genre {

    @Id
    @Column(unique = true, nullable = false)
    private String genreName;

    // Constructors, getters, and setters

    public Genre() {
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
