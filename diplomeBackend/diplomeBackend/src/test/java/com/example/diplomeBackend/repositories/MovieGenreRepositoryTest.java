package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieGenre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MovieGenreRepositoryTest {

    @Autowired
    private MovieGenreRepository movieGenreRepository;

    @Autowired
    private MovieRepository movieRepository;

    private Movie movie;
    private MovieGenre movieGenre;

    @BeforeEach
    void setUp() {
        // Set up test data
        movie = new Movie();
        movie.setTitle("Action Movie");
        movieRepository.save(movie);

        movieGenre = new MovieGenre();
        Genre genre = new Genre();
        genre.setGenreName("Action");
        movieGenre.setMovie(movie);
        movieGenre.setGenre(genre);
        movieGenreRepository.save(movieGenre);
    }

    @Test
    void testFindByGenreNameWithSorting() {
        List<Movie> movies = movieGenreRepository.findByGenreName("Action");
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("Action Movie", movies.get(0).getTitle());
    }

    @Test
    void testDeleteByGenreName() {
        movieGenreRepository.deleteByGenreName("Action");
        List<MovieGenre> movieGenres = movieGenreRepository.findAll();
        assertTrue(movieGenres.isEmpty());
    }
}
