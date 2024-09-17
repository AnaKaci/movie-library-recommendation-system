package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.DirectorDTO;
import com.example.diplomeBackend.models.Director;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DirectorMapperTest {

    private DirectorMapper directorMapper;

    @BeforeEach
    void setUp() {
        directorMapper = Mappers.getMapper(DirectorMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        Director director = new Director();
        director.setDirectorName("Steven Spielberg");
        director.setBio("Famous director");
        director.setDateOfBirth(new Date());

        // Act
        DirectorDTO directorDTO = directorMapper.toDTO(director);

        // Assert
        assertNotNull(directorDTO);
        assertEquals("Steven Spielberg", directorDTO.getName());
        assertEquals("Famous director", director.getBio()); // Assuming bio is handled separately
        assertNotNull(directorDTO.getDateOfBirth()); // Assuming dateOfBirth is handled separately
    }

    @Test
    void testToEntity() {
        // Arrange
        DirectorDTO directorDTO = new DirectorDTO("Steven Spielberg", "Famous director", new Date());
        directorDTO.setName("Steven Spielberg");

        // Act
        Director director = directorMapper.toEntity(directorDTO);

        // Assert
        assertNotNull(director);
        assertEquals("Steven Spielberg", director.getDirectorName());
        assertEquals("Famous director", directorDTO.getBio()); // Assuming bio is handled separately
        assertNotNull(directorDTO.getDateOfBirth()); // Assuming dateOfBirth is handled separately
    }
}
