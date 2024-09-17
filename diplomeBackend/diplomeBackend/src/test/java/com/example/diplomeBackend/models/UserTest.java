package com.example.diplomeBackend.models;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
        assertNull(user.getUserId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getDateOfBirth());
        assertNull(user.getProfilePicture());
        assertNull(user.getDateJoined());
        assertNull(user.getPreferences());
        assertNull(user.getFeedbacks());
        assertNull(user.getWatchlists());
        assertNull(user.getFriends());
    }

    @Test
    public void testUserParameterizedConstructor() {
        User user = new User(1L);
        user.setUsername("john_doe");
        user.setPassword("securePassword123");
        user.setEmail("john.doe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDateOfBirth(new Date(90, 1, 1)); // Using deprecated Date constructor for simplicity
        user.setProfilePicture("profile.jpg");
        user.setDateJoined(new Date());
        user.setPreferences("Some preferences");

        assertNotNull(user);
        assertEquals(1L, user.getUserId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("securePassword123", user.getPassword());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(new Date(90, 1, 1), user.getDateOfBirth());
        assertEquals("profile.jpg", user.getProfilePicture());
        assertNotNull(user.getDateJoined());
        assertEquals("Some preferences", user.getPreferences());
    }

    @Test
    public void testUserSettersAndGetters() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("john_doe");
        user.setPassword("securePassword123");
        user.setEmail("john.doe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDateOfBirth(new Date(90, 1, 1));
        user.setProfilePicture("profile.jpg");
        user.setDateJoined(new Date());
        user.setPreferences("Some preferences");

        Feedback feedback = new Feedback();
        Watchlist watchlist = new Watchlist();
        Set<Friends> friends = new HashSet<>();

        user.setFeedbacks(Collections.singletonList(feedback));
        user.setWatchlists(Collections.singletonList(watchlist));
        user.setFriends(friends);

        assertEquals(1L, user.getUserId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("securePassword123", user.getPassword());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(new Date(90, 1, 1), user.getDateOfBirth());
        assertEquals("profile.jpg", user.getProfilePicture());
        assertNotNull(user.getDateJoined());
        assertEquals("Some preferences", user.getPreferences());
        assertEquals(Collections.singletonList(feedback), user.getFeedbacks());
        assertEquals(Collections.singletonList(watchlist), user.getWatchlists());
        assertEquals(friends, user.getFriends());
    }

    @Test
    public void testConstraints() {
        User user = new User();

        // Test maximum size constraints
        assertThrows(ConstraintViolationException.class, () -> {
            user.setUsername("a".repeat(51)); // Username too long
        });

        assertThrows(ConstraintViolationException.class, () -> {
            user.setPassword("a".repeat(101)); // Password too long
        });

        assertThrows(ConstraintViolationException.class, () -> {
            user.setEmail("a".repeat(101) + "@example.com"); // Email too long
        });

        assertThrows(ConstraintViolationException.class, () -> {
            user.setFirstName("a".repeat(51)); // First name too long
        });

        assertThrows(ConstraintViolationException.class, () -> {
            user.setLastName("a".repeat(51)); // Last name too long
        });
    }
}
