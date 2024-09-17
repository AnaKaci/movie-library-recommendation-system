package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Friends;
import com.example.diplomeBackend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FriendsRepositoryTest {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private User friend1;
    private User friend2;
    private Friends friends;

    @BeforeEach
    void setUp() {
        // Create the main user
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        userRepository.save(user);

        // Create friends
        friend1 = new User();
        friend1.setFirstName("Jane");
        friend1.setLastName("Smith");
        friend1.setEmail("jane.smith@example.com");
        userRepository.save(friend1);

        friend2 = new User();
        friend2.setFirstName("Emily");
        friend2.setLastName("Johnson");
        friend2.setEmail("emily.johnson@example.com");
        userRepository.save(friend2);

        // Create a Friends entity
        friends = new Friends();
        friends.setUser(user);
        friends.setFriends(Set.of(friend1, friend2));  // Assuming the Friends model supports storing a list of friends
        friendsRepository.save(friends);
    }

    @Test
    void testGetAllUserFriends_Found() {
        // Test retrieving all friends for a user
        List<User> userFriends = friendsRepository.getAllUserFriends(user);

        assertNotNull(userFriends);
        assertEquals(2, userFriends.size());
        assertTrue(userFriends.contains(friend1));
        assertTrue(userFriends.contains(friend2));
    }

    @Test
    void testGetAllUserFriends_NotFound() {
        // Create a new user who has no friends
        User newUser = new User();
        newUser.setFirstName("New");
        newUser.setLastName("User");
        newUser.setEmail("new.user@example.com");
        userRepository.save(newUser);

        // Test retrieving friends for a user with no friends
        List<User> userFriends = friendsRepository.getAllUserFriends(newUser);

        assertNotNull(userFriends);
        assertTrue(userFriends.isEmpty());
    }

    @Test
    void testFindByUser_Found() {
        // Test finding Friends entity by user
        Optional<Friends> foundFriends = friendsRepository.findByUser(user);

        assertTrue(foundFriends.isPresent());
        assertEquals(user, foundFriends.get().getUser());
        assertEquals(2, foundFriends.get().getFriends().size());
    }

    @Test
    void testFindByUser_NotFound() {
        // Test finding Friends entity for a user without friends
        User newUser = new User();
        newUser.setFirstName("Lonely");
        newUser.setLastName("User");
        newUser.setEmail("lonely.user@example.com");
        userRepository.save(newUser);

        Optional<Friends> foundFriends = friendsRepository.findByUser(newUser);

        assertFalse(foundFriends.isPresent());
    }
}
