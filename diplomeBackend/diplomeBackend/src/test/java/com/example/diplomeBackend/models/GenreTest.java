package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GenreTest {

    @Test
    public void testGenreDefaultConstructor() {
        Genre genre = new Genre();
        assertNotNull(genre);
        assertNull(genre.getGenreName());
    }

    @Test
    public void testGenreParameterizedConstructor() {
        String genreName = "Action";
        Genre genre = new Genre(genreName);
        assertNotNull(genre);
        assertEquals(genreName, genre.getGenreName());
    }

    @Test
    public void testGenreSettersAndGetters() {
        Genre genre = new Genre();
        String genreName = "Comedy";
        genre.setGenreName(genreName);

        assertEquals(genreName, genre.getGenreName());
    }
}
