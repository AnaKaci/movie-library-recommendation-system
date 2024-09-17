package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieActorDTOTest {

    @Test
    void testMovieActorDTOConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        Long movieId = 101L;
        String actorName = "Leonardo DiCaprio";

        // Act
        MovieActorDTO movieActorDTO = new MovieActorDTO();
        movieActorDTO.setId(id);
        movieActorDTO.setMovieId(movieId);
        movieActorDTO.setActorName(actorName);

        // Assert
        assertEquals(id, movieActorDTO.getId());
        assertEquals(movieId, movieActorDTO.getMovieId());
        assertEquals(actorName, movieActorDTO.getActorName());
    }

    @Test
    void testMovieActorDTOSetters() {
        // Arrange
        MovieActorDTO movieActorDTO = new MovieActorDTO();
        Long id = 1L;
        Long movieId = 101L;
        String actorName = "Leonardo DiCaprio";

        // Act
        movieActorDTO.setId(id);
        movieActorDTO.setMovieId(movieId);
        movieActorDTO.setActorName(actorName);

        // Assert
        assertEquals(id, movieActorDTO.getId());
        assertEquals(movieId, movieActorDTO.getMovieId());
        assertEquals(actorName, movieActorDTO.getActorName());
    }
}
