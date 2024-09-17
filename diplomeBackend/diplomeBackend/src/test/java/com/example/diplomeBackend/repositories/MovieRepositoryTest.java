package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Feedback;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    private Movie movie1;
    private Movie movie2;
    private Actor actor;
    private Genre genre;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        // Set up test data
        actor = new Actor();
        actor.setActorName("John Doe");
        actorRepository.save(actor);

        genre = new Genre();
        genre.setGenreName("Action");
        genreRepository.save(genre);

        movie1 = new Movie();
        movie1.setTitle("Action Movie 1");
        movie1.setDescription("An action-packed movie.");
        movie1.setReleaseDate(new Date());
        movie1.setAverageRating(8.5);
        movie1.getActors().add(actor);
        movie1.getGenres().add(genre);
        movieRepository.save(movie1);

        movie2 = new Movie();
        movie2.setTitle("Drama Movie 1");
        movie2.setDescription("A dramatic story.");
        movie2.setReleaseDate(new Date());
        movie2.setAverageRating(7.5);
        movieRepository.save(movie2);

        feedback = new Feedback();
        feedback.setMovie(movie1);
        feedback.setFeedbackRating(9);
        feedbackRepository.save(feedback);
    }

    @Test
    void testSearchMovies() {
        List<Movie> movies = movieRepository.searchMovies("Action");
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("Action Movie 1", movies.get(0).getTitle());
    }

    @Test
    void testFindByMovieId() {
        Movie movie = movieRepository.findByMovieId(movie1.getMovieId());
        assertNotNull(movie);
        assertEquals(movie1.getMovieId(), movie.getMovieId());
    }

    @Test
    void testGetAllMovies() {
        List<Movie> movies = movieRepository.getAllMovies();
        assertNotNull(movies);
        assertTrue(movies.contains(movie1));
        assertTrue(movies.contains(movie2));
    }

    @Test
    void testSortByRating() {
        List<Movie> movies = movieRepository.sortByRating();
        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("Action Movie 1", movies.get(0).getTitle());
    }

    @Test
    void testSortByUpcoming() {
        List<Movie> movies = movieRepository.sortByUpcoming();
        assertNotNull(movies);
        assertTrue(movies.contains(movie2));
        assertFalse(movies.contains(movie1));
    }

    @Test
    void testSortByPopular() {
        List<Movie> movies = movieRepository.sortByPopular();
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("Action Movie 1", movies.get(0).getTitle());
    }

    @Test
    void testUpdateAverageRating() {
        movieRepository.updateAverageRating(9.0, movie1.getMovieId());
        Movie updatedMovie = movieRepository.findByMovieId(movie1.getMovieId());
        assertNotNull(updatedMovie);
        assertEquals(9.0, updatedMovie.getAverageRating());
    }
}
