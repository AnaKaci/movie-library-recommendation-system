package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieGenreTest {

    @Test
    public void testMovieGenreDefaultConstructor() {
        MovieGenre movieGenre = new MovieGenre();
        assertNotNull(movieGenre);
        assertNull(movieGenre.getMovieGenreId());
        assertNull(movieGenre.getMovie());
        assertNull(movieGenre.getGenre());
    }

    @Test
    public void testMovieGenreParameterizedConstructor() {
        Movie movie = new Movie("Movie Title", "Movie Description", new Date(), 120, null, "http://poster.url", "http://trailer.url", 8.5, null, null);
        Genre genre = new Genre("Genre Name");

        MovieGenre movieGenre = new MovieGenre(movie, genre);

        assertNotNull(movieGenre);
        assertEquals(movie, movieGenre.getMovie());
        assertEquals(genre, movieGenre.getGenre());
    }

    @Test
    public void testMovieGenreSettersAndGetters() {
        Movie movie = new Movie("Movie Title", "Movie Description", new Date(), 120, null, "http://poster.url", "http://trailer.url", 8.5, null, null);
        Genre genre = new Genre("Genre Name");

        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setMovie(movie);
        movieGenre.setGenre(genre);

        assertEquals(movie, movieGenre.getMovie());
        assertEquals(genre, movieGenre.getGenre());
    }

    @Test
    public void testMovieGenreIdSetterAndGetter() {
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setMovieGenreId(1L);

        assertEquals(1L, movieGenre.getMovieGenreId());
    }
}
