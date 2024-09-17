package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PreferenceDTOTest {

    @Test
    void testPreferenceDTOConstructorAndGettersSetters() {
        // Arrange
        Long preferenceId = 1L;
        String genreName = "Action";
        String actorName = "Tom Hanks";
        String directorName = "Steven Spielberg";
        String preferenceType = "Favorite";

        // Act
        PreferenceDTO preferenceDTO = new PreferenceDTO(preferenceId, genreName, actorName, directorName, preferenceType);

        // Assert
        assertEquals(preferenceId, preferenceDTO.getPreferenceId());
        assertEquals(genreName, preferenceDTO.getGenreName());
        assertEquals(actorName, preferenceDTO.getActorName());
        assertEquals(directorName, preferenceDTO.getDirectorName());
        assertEquals(preferenceType, preferenceDTO.getPreferenceType());
    }

    @Test
    void testPreferenceDTOSetters() {
        // Arrange
        PreferenceDTO preferenceDTO = new PreferenceDTO();
        Long preferenceId = 1L;
        String genreName = "Action";
        String actorName = "Tom Hanks";
        String directorName = "Steven Spielberg";
        String preferenceType = "Favorite";

        // Act
        preferenceDTO.setPreferenceId(preferenceId);
        preferenceDTO.setGenreName(genreName);
        preferenceDTO.setActorName(actorName);
        preferenceDTO.setDirectorName(directorName);
        preferenceDTO.setPreferenceType(preferenceType);

        // Assert
        assertEquals(preferenceId, preferenceDTO.getPreferenceId());
        assertEquals(genreName, preferenceDTO.getGenreName());
        assertEquals(actorName, preferenceDTO.getActorName());
        assertEquals(directorName, preferenceDTO.getDirectorName());
        assertEquals(preferenceType, preferenceDTO.getPreferenceType());
    }
}
