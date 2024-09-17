package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PreferenceTest {

    @Test
    public void testPreferenceDefaultConstructor() {
        Preference preference = new Preference();
        assertNotNull(preference);
        assertNull(preference.getPreferenceId());
        assertNull(preference.getUser());
        assertNull(preference.getGenre());
        assertNull(preference.getActor());
        assertNull(preference.getDirector());
        assertNull(preference.getPreferenceType());
    }

    @Test
    public void testPreferenceParameterizedConstructor() {
        User user = new User();
        Genre genre = new Genre("Action");
        Actor actor = new Actor("Actor Name");
        Director director = new Director("Director Name", "Bio", new Date());

        Preference preference = new Preference(user, genre, actor, director, "FAVORITE");

        assertNotNull(preference);
        assertEquals(user, preference.getUser());
        assertEquals(genre, preference.getGenre());
        assertEquals(actor, preference.getActor());
        assertEquals(director, preference.getDirector());
        assertEquals("FAVORITE", preference.getPreferenceType());
    }

    @Test
    public void testPreferenceSettersAndGetters() {
        User user = new User();
        Genre genre = new Genre("Action");
        Actor actor = new Actor("Actor Name");
        Director director = new Director("Director Name", "Bio", new Date());

        Preference preference = new Preference();
        preference.setUser(user);
        preference.setGenre(genre);
        preference.setActor(actor);
        preference.setDirector(director);
        preference.setPreferenceType("DISLIKED");

        assertEquals(user, preference.getUser());
        assertEquals(genre, preference.getGenre());
        assertEquals(actor, preference.getActor());
        assertEquals(director, preference.getDirector());
        assertEquals("DISLIKED", preference.getPreferenceType());
    }

    @Test
    public void testPreferenceTypeValidation() {
        Preference preference = new Preference();

        assertThrows(IllegalArgumentException.class, () -> preference.setPreferenceType("INVALID_TYPE"));

        // Valid types
        preference.setPreferenceType("FAVORITE");
        assertEquals("FAVORITE", preference.getPreferenceType());

        preference.setPreferenceType("DISLIKED");
        assertEquals("DISLIKED", preference.getPreferenceType());
    }
}
