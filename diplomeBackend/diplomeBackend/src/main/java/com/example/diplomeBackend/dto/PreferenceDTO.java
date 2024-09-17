package com.example.diplomeBackend.dto;

public class PreferenceDTO {
    private Long preferenceId;
    private String genreName;
    private String actorName;
    private String directorName;
    private String preferenceType;

    // Constructors, getters, and setters

    public PreferenceDTO() {
    }

    public PreferenceDTO(Long preferenceId, String genreName, String actorName, String directorName, String preferenceType) {
        this.preferenceId = preferenceId;
        this.genreName = genreName;
        this.actorName = actorName;
        this.directorName = directorName;
        this.preferenceType = preferenceType;
    }

    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getPreferenceType() {
        return preferenceType;
    }

    public void setPreferenceType(String preferenceType) {
        this.preferenceType = preferenceType;
    }
}
