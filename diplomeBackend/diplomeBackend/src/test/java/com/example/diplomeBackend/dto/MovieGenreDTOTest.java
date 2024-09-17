package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieGenreDTOTest {

    @Test
    void testMovieGenreDTOConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        Long movieId = 101L;
        String genreName = "Action";

        // Act
        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();
        movieGenreDTO.setId(id);
        movieGenreDTO.setMovieId(movieId);
        movieGenreDTO.setGenreName(genreName);

        // Assert
        assertEquals(id, movieGenreDTO.getId());
        assertEquals(movieId, movieGenreDTO.getMovieId());
        assertEquals(genreName, movieGenreDTO.getGenreName());
    }

    @Test
    void testMovieGenreDTOSetters() {
        // Arrange
        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();
        Long id = 1L;
        Long movieId = 101L;
        String genreName = "Action";

        // Act
        movieGenreDTO.setId(id);
        movieGenreDTO.setMovieId(movieId);
        movieGenreDTO.setGenreName(genreName);

        // Assert
        assertEquals(id, movieGenreDTO.getId());
        assertEquals(movieId, movieGenreDTO.getMovieId());
        assertEquals(genreName, movieGenreDTO.getGenreName());
    }
}
