package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectorDTOTest {

    @Test
    void testDirectorDTOConstructorAndGettersSetters() {
        // Arrange
        String name = "Steven Spielberg";
        String bio = "Famous American director";
        Date dateOfBirth = new Date();

        // Act
        DirectorDTO directorDTO = new DirectorDTO(name, bio, dateOfBirth);

        // Assert
        assertEquals(name, directorDTO.getName());
        assertEquals(bio, directorDTO.getBio());
        assertEquals(dateOfBirth, directorDTO.getDateOfBirth());
    }

    @Test
    void testDirectorDTOSetters() {
        // Arrange
        DirectorDTO directorDTO = new DirectorDTO();

        // Act
        String name = "Quentin Tarantino";
        String bio = "Renowned film director and writer";
        Date dateOfBirth = new Date();

        directorDTO.setName(name);
        directorDTO.setBio(bio);
        directorDTO.setDateOfBirth(dateOfBirth);

        // Assert
        assertEquals(name, directorDTO.getName());
        assertEquals(bio, directorDTO.getBio());
        assertEquals(dateOfBirth, directorDTO.getDateOfBirth());
    }
}
