package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Preference;
import com.example.diplomeBackend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class PreferenceRepositoryTest {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Preference favoritePreference;
    private Preference dislikedPreference;

    @BeforeEach
    void setUp() {
        // Set up test data
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        userRepository.save(user);

        favoritePreference = new Preference();
        favoritePreference.setUser(user);
        favoritePreference.setPreferenceType("FAVORITE");
        preferenceRepository.save(favoritePreference);

        dislikedPreference = new Preference();
        dislikedPreference.setUser(user);
        dislikedPreference.setPreferenceType("DISLIKED");
        preferenceRepository.save(dislikedPreference);
    }

    @Test
    void testFindByUserIdAndPreferenceTypes() {
        List<Preference> favorites = preferenceRepository.findByUserIdAndPreferenceTypes(user.getUserId(), "FAVORITE");
        List<Preference> dislikes = preferenceRepository.findByUserIdAndPreferenceTypes(user.getUserId(), "DISLIKED");

        assertNotNull(favorites);
        assertNotNull(dislikes);

        assertEquals(1, favorites.size());
        assertEquals(1, dislikes.size());

        assertEquals("FAVORITE", favorites.get(0).getPreferenceType());
        assertEquals("DISLIKED", dislikes.get(0).getPreferenceType());
    }
}
