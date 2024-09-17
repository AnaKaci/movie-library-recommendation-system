package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MovieActorDTO;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieActor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MovieActorMapperTest {

    private MovieActorMapper movieActorMapper;

    @BeforeEach
    void setUp() {
        movieActorMapper = Mappers.getMapper(MovieActorMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        Actor actor = new Actor();
        actor.setActorName("John Doe");

        Movie movie = new Movie();
        movie.setMovieId(1L);

        MovieActor movieActor = new MovieActor();
        movieActor.setMovie(movie);
        movieActor.setActor(actor);

        // Act
        MovieActorDTO movieActorDTO = movieActorMapper.toDTO(movieActor);

        // Assert
        assertNotNull(movieActorDTO);
        assertEquals(1L, movieActorDTO.getMovieId());
        assertEquals("John Doe", movieActorDTO.getActorName());
    }

    @Test
    void testToEntity() {
        // Arrange
        MovieActorDTO movieActorDTO = new MovieActorDTO();
        movieActorDTO.setMovieId(1L);
        movieActorDTO.setActorName("John Doe");

        // Act
        MovieActor movieActor = movieActorMapper.toEntity(movieActorDTO);

        // Assert
        assertNotNull(movieActor);
        assertNotNull(movieActor.getMovie());
        assertNotNull(movieActor.getActor());
        assertEquals(1L, movieActor.getMovie().getMovieId());
        assertEquals("John Doe", movieActor.getActor().getActorName());
    }

    @Test
    void testMapActorName() {
        // Arrange
        String actorName = "John Doe";

        // Act
        Actor actor = movieActorMapper.mapActorName(actorName);

        // Assert
        assertNotNull(actor);
        assertEquals(actorName, actor.getActorName());
    }

    @Test
    void testMapMovieId() {
        // Arrange
        Long movieId = 1L;

        // Act
        Movie movie = movieActorMapper.mapMovieId(movieId);

        // Assert
        assertNotNull(movie);
        assertEquals(movieId, movie.getMovieId());
    }
}
