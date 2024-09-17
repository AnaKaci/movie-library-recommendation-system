package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieTest {

    @Test
    public void testMovieDefaultConstructor() {
        Movie movie = new Movie();
        assertNotNull(movie);
        assertNull(movie.getMovieId());
        assertNull(movie.getTitle());
        assertNull(movie.getDescription());
        assertNull(movie.getReleaseDate());
        assertEquals(0, movie.getDuration());
        assertNull(movie.getDirector());
        assertNull(movie.getPoster());
        assertNull(movie.getTrailer());
        assertNull(movie.getAverageRating());
        assertTrue(movie.getFeedbacks().isEmpty());
        assertTrue(movie.getGenres().isEmpty());
        assertTrue(movie.getActors().isEmpty());
    }

    @Test
    public void testMovieParameterizedConstructor() {
        Director director = new Director("Director Name", "Director Bio", new Date());
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("Action"));
        Set<Actor> actors = new HashSet<>();
        actors.add(new Actor("Actor Name"));

        Movie movie = new Movie("Title", "Description", new Date(), 120, director,
                "http://poster.url", "http://trailer.url", 8.5, genres, actors);

        assertNotNull(movie);
        assertEquals("Title", movie.getTitle());
        assertEquals("Description", movie.getDescription());
        assertNotNull(movie.getReleaseDate());
        assertEquals(120, movie.getDuration());
        assertEquals(director, movie.getDirector());
        assertEquals("http://poster.url", movie.getPoster());
        assertEquals("http://trailer.url", movie.getTrailer());
        assertEquals(8.5, movie.getAverageRating());
        assertFalse(movie.getGenres().isEmpty());
        assertFalse(movie.getActors().isEmpty());
    }

    @Test
    public void testMovieSettersAndGetters() {
        Movie movie = new Movie();
        Director director = new Director("Director Name", "Director Bio", new Date());
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("Comedy"));
        Set<Actor> actors = new HashSet<>();
        actors.add(new Actor("Actor Name"));

        movie.setTitle("New Title");
        movie.setDescription("New Description");
        movie.setReleaseDate(new Date());
        movie.setDuration(150);
        movie.setDirector(director);
        movie.setPoster("http://newposter.url");
        movie.setTrailer("http://newtrailer.url");
        movie.setAverageRating(9.0);
        movie.setGenres(genres);
        movie.setActors(actors);

        assertEquals("New Title", movie.getTitle());
        assertEquals("New Description", movie.getDescription());
        assertNotNull(movie.getReleaseDate());
        assertEquals(150, movie.getDuration());
        assertEquals(director, movie.getDirector());
        assertEquals("http://newposter.url", movie.getPoster());
        assertEquals("http://newtrailer.url", movie.getTrailer());
        assertEquals(9.0, movie.getAverageRating());
        assertFalse(movie.getGenres().isEmpty());
        assertFalse(movie.getActors().isEmpty());
    }
}
