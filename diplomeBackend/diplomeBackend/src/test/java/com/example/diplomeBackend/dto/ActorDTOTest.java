package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActorDTOTest {

    @Test
    void testActorDTOConstructorAndGettersSetters() {
        // Arrange
        String name = "John Doe";
        String bio = "An experienced actor";
        Date dateOfBirth = new Date();
        Set<Long> movieIds = new HashSet<>();
        movieIds.add(1L);
        movieIds.add(2L);

        // Act
        ActorDTO actorDTO = new ActorDTO(name, bio, dateOfBirth, movieIds);

        // Assert
        assertEquals(name, actorDTO.getName());
        assertEquals(bio, actorDTO.getBio());
        assertEquals(dateOfBirth, actorDTO.getDateOfBirth());
        assertEquals(movieIds, actorDTO.getMovieIds());
    }

    @Test
    void testActorDTOSetters() {
        // Arrange
        ActorDTO actorDTO = new ActorDTO();

        // Act
        String name = "Jane Doe";
        String bio = "A talented actress";
        Date dateOfBirth = new Date();
        Set<Long> movieIds = new HashSet<>();
        movieIds.add(3L);
        movieIds.add(4L);

        actorDTO.setName(name);
        actorDTO.setBio(bio);
        actorDTO.setDateOfBirth(dateOfBirth);
        actorDTO.setMovieIds(movieIds);

        // Assert
        assertEquals(name, actorDTO.getName());
        assertEquals(bio, actorDTO.getBio());
        assertEquals(dateOfBirth, actorDTO.getDateOfBirth());
        assertEquals(movieIds, actorDTO.getMovieIds());
    }

    @Test
    void testToString() {
        // Arrange
        String name = "John Doe";
        String bio = "An experienced actor";
        Date dateOfBirth = new Date();
        Set<Long> movieIds = new HashSet<>();
        movieIds.add(1L);
        movieIds.add(2L);

        ActorDTO actorDTO = new ActorDTO(name, bio, dateOfBirth, movieIds);

        // Act
        String expectedToString = "ActorDTO{name='John Doe', bio='An experienced actor', dateOfBirth=" + dateOfBirth + ", movieIds=" + movieIds + "}";
        String actualToString = actorDTO.toString();

        // Assert
        assertEquals(expectedToString, actualToString);
    }
}
