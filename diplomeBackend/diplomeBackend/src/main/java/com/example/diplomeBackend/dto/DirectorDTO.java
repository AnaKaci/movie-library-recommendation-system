package com.example.diplomeBackend.dto;

import java.util.Date;

public class DirectorDTO {
    private String name;
    private String bio;
    private Date dateOfBirth;

    public DirectorDTO(){

    }

    public DirectorDTO(String name, String bio, Date dateOfBirth){
        this.name = name;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
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
}
