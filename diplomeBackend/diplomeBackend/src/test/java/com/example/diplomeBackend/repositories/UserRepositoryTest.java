package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        // Set up test data
        user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setUsername("johndoe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password123");
        userRepository.save(user1);

        user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setUsername("janesmith");
        user2.setEmail("jane.smith@example.com");
        user2.setPassword("password456");
        userRepository.save(user2);
    }

    @Test
    void testSearchUser() {
        List<User> users = userRepository.searchUser("john");
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user1.getUsername(), users.get(0).getUsername());

        users = userRepository.searchUser("smith");
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user2.getUsername(), users.get(0).getUsername());
    }

    @Test
    void testFindByUsername() {
        Optional<User> foundUser = userRepository.findByUsername("johndoe");
        assertTrue(foundUser.isPresent());
        assertEquals(user1.getEmail(), foundUser.get().getEmail());

        foundUser = userRepository.findByUsername("nonexistentuser");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindUserById() {
        User foundUser = userRepository.findUserById(user1.getUserId());
        assertNotNull(foundUser);
        assertEquals(user1.getUsername(), foundUser.getUsername());

        User notFoundUser = userRepository.findUserById(-1L);
        assertNull(notFoundUser);
    }

    @Test
    void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("john.doe@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals(user1.getUsername(), foundUser.get().getUsername());

        foundUser = userRepository.findByEmail("nonexistent.email@example.com");
        assertFalse(foundUser.isPresent());
    }
}
