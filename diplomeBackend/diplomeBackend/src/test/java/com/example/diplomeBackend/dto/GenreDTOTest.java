package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenreDTOTest {

    @Test
    void testGenreDTOConstructorAndGettersSetters() {
        // Arrange
        String genreName = "Action";

        // Act
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setGenreName(genreName);

        // Assert
        assertEquals(genreName, genreDTO.getGenreName());
    }

    @Test
    void testGenreDTOSetters() {
        // Arrange
        GenreDTO genreDTO = new GenreDTO();

        String genreName = "Comedy";

        // Act
        genreDTO.setGenreName(genreName);

        // Assert
        assertEquals(genreName, genreDTO.getGenreName());
    }
}
