package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ActorMapperTest {

    private ActorMapper actorMapper;

    @BeforeEach
    void setUp() {
        actorMapper = Mappers.getMapper(ActorMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        Set<Movie> movies = new HashSet<>();
        movies.add(new Movie(1L));
        movies.add(new Movie(2L));
        Actor actor = new Actor("John Doe", "An actor", new Date());
        actor.setActorName("1L");

        // Act
        ActorDTO actorDTO = actorMapper.toDTO(actor);

        // Assert
        assertNotNull(actorDTO);
        assertEquals("John Doe", actorDTO.getName());
        assertEquals("An actor", actorDTO.getBio());
        assertEquals(2, actorDTO.getMovieIds().size());
        assertTrue(actorDTO.getMovieIds().contains(1L));
        assertTrue(actorDTO.getMovieIds().contains(2L));
    }

    @Test
    void testToEntity() {
        // Arrange
        Set<Long> movieIds = new HashSet<>();
        movieIds.add(1L);
        movieIds.add(2L);

        ActorDTO actorDTO = new ActorDTO("John Doe", "An actor", new Date(), movieIds);
        actorDTO.setName("John Doe");

        // Act
        Actor actor = actorMapper.toEntity(actorDTO);

        // Assert
        assertNotNull(actor);
        assertEquals("John Doe", actor.getActorName());
        assertEquals("An actor", actor.getBio());
        assertTrue(actor.getMovies().isEmpty()); // Movies are ignored in this case
    }
}
