package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ActorTest {

    @Test
    public void testActorDefaultConstructor() {
        Actor actor = new Actor();
        assertNotNull(actor);
        assertNull(actor.getActorName());
        assertNull(actor.getBio());
        assertNull(actor.getDateOfBirth());
        assertNotNull(actor.getMovies());
        assertEquals(0, actor.getMovies().size());
    }

    @Test
    public void testActorParameterizedConstructor() {
        String actorName = "John Doe";
        String bio = "An experienced actor.";
        Date dateOfBirth = new Date();
        Actor actor = new Actor(actorName, bio, dateOfBirth);

        assertNotNull(actor);
        assertEquals(actorName, actor.getActorName());
        assertEquals(bio, actor.getBio());
        assertEquals(dateOfBirth, actor.getDateOfBirth());
    }

    @Test
    public void testActorSettersAndGetters() {
        Actor actor = new Actor();
        String actorName = "Jane Doe";
        String bio = "An award-winning actress.";
        Date dateOfBirth = new Date();

        actor.setActorName(actorName);
        actor.setBio(bio);
        actor.setDateOfBirth(dateOfBirth);

        assertEquals(actorName, actor.getActorName());
        assertEquals(bio, actor.getBio());
        assertEquals(dateOfBirth, actor.getDateOfBirth());
    }

    @Test
    public void testActorMoviesRelationship() {
        Actor actor = new Actor("Actor Name");

        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");

        Set<Movie> movies = new HashSet<>();
        movies.add(movie1);
        movies.add(movie2);

        actor.setMovies(movies);

        assertEquals(2, actor.getMovies().size());
        assertTrue(actor.getMovies().contains(movie1));
        assertTrue(actor.getMovies().contains(movie2));
    }
}
