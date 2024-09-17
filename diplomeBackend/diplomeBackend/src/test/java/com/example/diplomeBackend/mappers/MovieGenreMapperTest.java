package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MovieGenreDTO;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieGenre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MovieGenreMapperTest {

    private MovieGenreMapper movieGenreMapper;

    @BeforeEach
    void setUp() {
        movieGenreMapper = Mappers.getMapper(MovieGenreMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        Genre genre = new Genre();
        genre.setGenreName("Action");

        Movie movie = new Movie();
        movie.setMovieId(1L);

        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setMovie(movie);
        movieGenre.setGenre(genre);

        // Act
        MovieGenreDTO movieGenreDTO = movieGenreMapper.toDTO(movieGenre);

        // Assert
        assertNotNull(movieGenreDTO);
        assertEquals(1L, movieGenreDTO.getMovieId());
        assertEquals("Action", movieGenreDTO.getGenreName());
    }

    @Test
    void testToEntity() {
        // Arrange
        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();
        movieGenreDTO.setMovieId(1L);
        movieGenreDTO.setGenreName("Action");

        // Act
        MovieGenre movieGenre = movieGenreMapper.toEntity(movieGenreDTO);

        // Assert
        assertNotNull(movieGenre);
        assertNotNull(movieGenre.getMovie());
        assertNotNull(movieGenre.getGenre());
        assertEquals(1L, movieGenre.getMovie().getMovieId());
        assertEquals("Action", movieGenre.getGenre().getGenreName());
    }

    @Test
    void testMapGenreName() {
        // Arrange
        String genreName = "Action";

        // Act
        Genre genre = movieGenreMapper.mapGenreName(genreName);

        // Assert
        assertNotNull(genre);
        assertEquals(genreName, genre.getGenreName());
    }

    @Test
    void testMapMovieId() {
        // Arrange
        Long movieId = 1L;

        // Act
        Movie movie = movieGenreMapper.mapMovieId(movieId);

        // Assert
        assertNotNull(movie);
        assertEquals(movieId, movie.getMovieId());
    }
}
