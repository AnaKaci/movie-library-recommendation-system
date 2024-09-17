package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DirectorTest {

    @Test
    public void testDirectorDefaultConstructor() {
        Director director = new Director();
        assertNotNull(director);
        assertNull(director.getDirectorName());
        assertNull(director.getBio());
        assertNull(director.getDateOfBirth());
    }

    @Test
    public void testDirectorParameterizedConstructor() {
        String directorName = "Steven Spielberg";
        String bio = "Famous director known for many blockbuster movies.";
        Date dateOfBirth = new Date();
        Director director = new Director(directorName, bio, dateOfBirth);

        assertNotNull(director);
        assertEquals(directorName, director.getDirectorName());
        assertEquals(bio, director.getBio());
        assertEquals(dateOfBirth, director.getDateOfBirth());
    }

    @Test
    public void testDirectorSettersAndGetters() {
        Director director = new Director();
        String directorName = "Martin Scorsese";
        String bio = "Legendary director with a long filmography.";
        Date dateOfBirth = new Date();

        director.setDirectorName(directorName);
        director.setBio(bio);
        director.setDateOfBirth(dateOfBirth);

        assertEquals(directorName, director.getDirectorName());
        assertEquals(bio, director.getBio());
        assertEquals(dateOfBirth, director.getDateOfBirth());
    }
}
