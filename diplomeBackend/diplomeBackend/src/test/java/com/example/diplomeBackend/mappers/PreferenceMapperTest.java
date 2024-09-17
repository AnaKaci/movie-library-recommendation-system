package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.PreferenceDTO;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Director;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.models.Preference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PreferenceMapperTest {

    private PreferenceMapper preferenceMapper;

    @BeforeEach
    void setUp() {
        preferenceMapper = Mappers.getMapper(PreferenceMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        Genre genre = new Genre();
        genre.setGenreName("Action");

        Actor actor = new Actor();
        actor.setActorName("Tom Hanks");

        Director director = new Director();
        director.setDirectorName("Steven Spielberg");

        Preference preference = new Preference();
        preference.setGenre(genre);
        preference.setActor(actor);
        preference.setDirector(director);
        preference.setPreferenceType("Movie");

        // Act
        PreferenceDTO preferenceDTO = preferenceMapper.toDTO(preference);

        // Assert
        assertNotNull(preferenceDTO);
        assertEquals("Action", preferenceDTO.getGenreName());
        assertEquals("Tom Hanks", preferenceDTO.getActorName());
        assertEquals("Steven Spielberg", preferenceDTO.getDirectorName());
        assertEquals("Movie", preferenceDTO.getPreferenceType());
    }

    @Test
    void testToEntity() {
        // Arrange
        PreferenceDTO preferenceDTO = new PreferenceDTO();
        preferenceDTO.setGenreName("Action");
        preferenceDTO.setActorName("Tom Hanks");
        preferenceDTO.setDirectorName("Steven Spielberg");
        preferenceDTO.setPreferenceType("Movie");

        // Act
        Preference preference = preferenceMapper.toEntity(preferenceDTO);

        // Assert
        assertNotNull(preference);
        assertNotNull(preference.getGenre());
        assertNotNull(preference.getActor());
        assertNotNull(preference.getDirector());
        assertEquals("Action", preference.getGenre().getGenreName());
        assertEquals("Tom Hanks", preference.getActor().getActorName());
        assertEquals("Steven Spielberg", preference.getDirector().getDirectorName());
        assertEquals("Movie", preference.getPreferenceType());
    }
}
