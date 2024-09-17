package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieActor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class MovieActorRepositoryTest {

    @Autowired
    private MovieActorRepository movieActorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieRepository movieRepository;

    private Movie movie;
    private Actor actor;
    private MovieActor movieActor;

    @BeforeEach
    void setUp() {
        // Set up test data
        actor = new Actor();
        actor.setActorName("John Doe");
        actorRepository.save(actor);

        movie = new Movie();
        movie.setTitle("Action Movie");
        movieRepository.save(movie);

        movieActor = new MovieActor();
        movieActor.setActor(actor);
        movieActor.setMovie(movie);
        movieActorRepository.save(movieActor);
    }

    @Test
    void testFindActorsByMovieId() {
        List<Actor> actors = movieActorRepository.findActorsByMovieId(movie.getMovieId());
        assertNotNull(actors);
        assertEquals(1, actors.size());
        assertEquals("John Doe", actors.get(0).getActorName());
    }

    @Test
    void testFindByActorName() {
        List<Movie> movies = movieActorRepository.findByActorName("John Doe");
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("Action Movie", movies.get(0).getTitle());
    }
}
