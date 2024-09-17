package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.GenreDTO;
import com.example.diplomeBackend.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GenreMapperTest {

    private GenreMapper genreMapper;

    @BeforeEach
    void setUp() {
        genreMapper = Mappers.getMapper(GenreMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        Genre genre = new Genre();
        genre.setGenreName("Action");

        // Act
        GenreDTO genreDTO = genreMapper.toDTO(genre);

        // Assert
        assertNotNull(genreDTO);
        assertEquals("Action", genreDTO.getGenreName());
    }

    @Test
    void testToEntity() {
        // Arrange
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setGenreName("Action");

        // Act
        Genre genre = genreMapper.toEntity(genreDTO);

        // Assert
        assertNotNull(genre);
        assertEquals("Action", genre.getGenreName());
    }
}
