package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieActorTest {

    @Test
    public void testMovieActorDefaultConstructor() {
        MovieActor movieActor = new MovieActor();
        assertNotNull(movieActor);
        assertNull(movieActor.getMovieActorId());
        assertNull(movieActor.getMovie());
        assertNull(movieActor.getActor());
    }

    @Test
    public void testMovieActorParameterizedConstructor() {
        Movie movie = new Movie("Movie Title", "Movie Description", new Date(), 120, null, "http://poster.url", "http://trailer.url", 8.5, null, null);
        Actor actor = new Actor("Actor Name");

        MovieActor movieActor = new MovieActor(movie, actor);

        assertNotNull(movieActor);
        assertEquals(movie, movieActor.getMovie());
        assertEquals(actor, movieActor.getActor());
    }

    @Test
    public void testMovieActorSettersAndGetters() {
        Movie movie = new Movie("Movie Title", "Movie Description", new Date(), 120, null, "http://poster.url", "http://trailer.url", 8.5, null, null);
        Actor actor = new Actor("Actor Name");

        MovieActor movieActor = new MovieActor();
        movieActor.setMovie(movie);
        movieActor.setActor(actor);

        assertEquals(movie, movieActor.getMovie());
        assertEquals(actor, movieActor.getActor());
    }

    @Test
    public void testMovieActorIdSetterAndGetter() {
        MovieActor movieActor = new MovieActor();
        movieActor.setMovieActorId(1L);

        assertEquals(1L, movieActor.getMovieActorId());
    }
}
