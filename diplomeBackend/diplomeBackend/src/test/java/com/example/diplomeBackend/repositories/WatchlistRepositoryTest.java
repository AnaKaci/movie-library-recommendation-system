package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.models.Watchlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WatchlistRepositoryTest {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    private User user;
    private Movie movie1;
    private Movie movie2;
    private Watchlist watchlist1;
    private Watchlist watchlist2;

    @BeforeEach
    void setUp() {
        // Set up test data
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        userRepository.save(user);

        movie1 = new Movie();
        movie1.setTitle("Inception");
        movie1.setDescription("A mind-bending thriller");
        movieRepository.save(movie1);

        movie2 = new Movie();
        movie2.setTitle("The Matrix");
        movie2.setDescription("A hacker learns about the true nature of reality");
        movieRepository.save(movie2);

        watchlist1 = new Watchlist();
        watchlist1.setUser(user);
        watchlist1.setMovie(movie1);
        watchlistRepository.save(watchlist1);

        watchlist2 = new Watchlist();
        watchlist2.setUser(user);
        watchlist2.setMovie(movie2);
        watchlistRepository.save(watchlist2);
    }

    @Test
    void testFindMoviesByUserId() {
        List<Movie> movies = watchlistRepository.findMoviesByUserId(user.getUserId());
        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertTrue(movies.contains(movie1));
        assertTrue(movies.contains(movie2));
    }

    @Test
    @Transactional
    void testDeleteByUserIdAndMovieId() {
        watchlistRepository.deleteByUserIdAndMovieId(user.getUserId(), movie1.getMovieId());

        List<Movie> movies = watchlistRepository.findMoviesByUserId(user.getUserId());
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertFalse(movies.contains(movie1));
        assertTrue(movies.contains(movie2));
    }
}
