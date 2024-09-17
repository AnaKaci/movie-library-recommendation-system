package com.example.diplomeBackend.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Director {


    @Id
    @Column(unique = true, nullable = false)
    private String directorName;

    @Column(length = 5000)
    private String bio;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    // Constructors, getters, and setters

    public Director() {
    }

    public Director(String directorName, String bio, Date dateOfBirth) {
        this.directorName = directorName;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
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
}

