package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Director;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        movieMapper = Mappers.getMapper(MovieMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        Genre genre1 = new Genre();
        genre1.setGenreName("Action");
        Genre genre2 = new Genre();
        genre2.setGenreName("Adventure");
        Set<Genre> genres = new HashSet<>();
        genres.add(genre1);
        genres.add(genre2);

        Actor actor1 = new Actor();
        actor1.setActorName("John Doe");
        Actor actor2 = new Actor();
        actor2.setActorName("Jane Smith");
        Set<Actor> actors = new HashSet<>();
        actors.add(actor1);
        actors.add(actor2);

        Movie movie = new Movie();
        Director director = new Director();
        director.setDirectorName("Steven Spielberg");
        movie.setDirector(director);
        movie.setGenres(genres);
        movie.setActors(actors);
        movie.setTitle("Jurassic Park");
        movie.setDescription("A movie about dinosaurs.");
        movie.setReleaseDate(new java.util.Date());
        movie.setDuration(127);
        movie.setPoster("poster-url");
        movie.setTrailer("trailer-url");
        movie.setAverageRating(8.5);

        // Act
        MovieDTO movieDTO = movieMapper.toDTO(movie);

        // Assert
        assertNotNull(movieDTO);
        assertEquals("Steven Spielberg", movieDTO.getDirectorName());
        assertTrue(movieDTO.getGenreNames().contains("Action"));
        assertTrue(movieDTO.getGenreNames().contains("Adventure"));
        assertTrue(movieDTO.getActorNames().contains("John Doe"));
        assertTrue(movieDTO.getActorNames().contains("Jane Smith"));
        assertEquals("Jurassic Park", movieDTO.getTitle());
        assertEquals("A movie about dinosaurs.", movieDTO.getDescription());
        assertEquals(movie.getReleaseDate(), movieDTO.getReleaseDate());
        assertEquals(127, movieDTO.getDuration());
        assertEquals("poster-url", movieDTO.getPoster());
        assertEquals("trailer-url", movieDTO.getTrailer());
        assertEquals(8.5, movieDTO.getAverageRating());
    }

    @Test
    void testToEntity() {
        // Arrange
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setDirectorName("Steven Spielberg");
        movieDTO.setGenreNames(Set.of("Action", "Adventure"));
        movieDTO.setActorNames(Set.of("John Doe", "Jane Smith"));
        movieDTO.setTitle("Jurassic Park");
        movieDTO.setDescription("A movie about dinosaurs.");
        movieDTO.setReleaseDate(new java.util.Date());
        movieDTO.setDuration(127);
        movieDTO.setPoster("poster-url");
        movieDTO.setTrailer("trailer-url");
        movieDTO.setAverageRating(8.5);

        // Act
        Movie movie = movieMapper.toEntity(movieDTO);

        // Assert
        assertNotNull(movie);
        assertEquals("Steven Spielberg", movie.getDirector().getDirectorName());
        assertTrue(movie.getGenres().stream().anyMatch(g -> g.getGenreName().equals("Action")));
        assertTrue(movie.getGenres().stream().anyMatch(g -> g.getGenreName().equals("Adventure")));
        assertTrue(movie.getActors().stream().anyMatch(a -> a.getActorName().equals("John Doe")));
        assertTrue(movie.getActors().stream().anyMatch(a -> a.getActorName().equals("Jane Smith")));
        assertEquals("Jurassic Park", movie.getTitle());
        assertEquals("A movie about dinosaurs.", movie.getDescription());
        assertEquals(movieDTO.getReleaseDate(), movie.getReleaseDate());
        assertEquals(127, movie.getDuration());
        assertEquals("poster-url", movie.getPoster());
        assertEquals("trailer-url", movie.getTrailer());
        assertEquals(8.5, movie.getAverageRating());
    }

    @Test
    void testConvertGenresToNames() {
        // Arrange
        Genre genre1 = new Genre();
        genre1.setGenreName("Action");
        Genre genre2 = new Genre();
        genre2.setGenreName("Adventure");
        Set<Genre> genres = new HashSet<>();
        genres.add(genre1);
        genres.add(genre2);

        // Act
        Set<String> genreNames = movieMapper.convertGenresToNames(genres);

        // Assert
        assertNotNull(genreNames);
        assertTrue(genreNames.contains("Action"));
        assertTrue(genreNames.contains("Adventure"));
    }

    @Test
    void testConvertActorsToNames() {
        // Arrange
        Actor actor1 = new Actor();
        actor1.setActorName("John Doe");
        Actor actor2 = new Actor();
        actor2.setActorName("Jane Smith");
        Set<Actor> actors = new HashSet<>();
        actors.add(actor1);
        actors.add(actor2);

        // Act
        Set<String> actorNames = movieMapper.convertActorsToNames(actors);

        // Assert
        assertNotNull(actorNames);
        assertTrue(actorNames.contains("John Doe"));
        assertTrue(actorNames.contains("Jane Smith"));
    }

    @Test
    void testConvertNamesToGenres() {
        // Arrange
        Set<String> genreNames = Set.of("Action", "Adventure");

        // Act
        Set<Genre> genres = movieMapper.convertNamesToGenres(genreNames);

        // Assert
        assertNotNull(genres);
        assertTrue(genres.stream().anyMatch(g -> g.getGenreName().equals("Action")));
        assertTrue(genres.stream().anyMatch(g -> g.getGenreName().equals("Adventure")));
    }

    @Test
    void testConvertNamesToActors() {
        // Arrange
        Set<String> actorNames = Set.of("John Doe", "Jane Smith");

        // Act
        Set<Actor> actors = movieMapper.convertNamesToActors(actorNames);

        // Assert
        assertNotNull(actors);
        assertTrue(actors.stream().anyMatch(a -> a.getActorName().equals("John Doe")));
        assertTrue(actors.stream().anyMatch(a -> a.getActorName().equals("Jane Smith")));
    }
}
