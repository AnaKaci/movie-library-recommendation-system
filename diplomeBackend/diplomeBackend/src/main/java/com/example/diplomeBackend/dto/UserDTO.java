package com.example.diplomeBackend.dto;

import java.util.Date;
import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private List<Long> watchlistIds;

    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public UserDTO(){

    }

    public UserDTO(Long id, String username, String email, List<Long> watchlistIds, String firstName, String lastName, Date dateOfBirth) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.watchlistIds = watchlistIds;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public UserDTO(Long id, String username, String email, String firstName, String lastName, Date dateOfBirth) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getWatchlistIds() {
        return watchlistIds;
    }

    public void setWatchlistIds(List<Long> watchlistIds) {
        this.watchlistIds = watchlistIds;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
