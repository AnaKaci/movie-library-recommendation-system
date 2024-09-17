package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FriendsTest {

    @Test
    public void testFriendsDefaultConstructor() {
        Friends friends = new Friends();
        assertNotNull(friends);
        assertNull(friends.getId());
        assertNull(friends.getUser());
        assertNotNull(friends.getFriends());
        assertTrue(friends.getFriends().isEmpty());
    }

    @Test
    public void testFriendsParameterizedConstructor() {
        User user = new User(); // Assuming User class is available
        Friends friends = new Friends(user);
        assertNotNull(friends);
        assertEquals(user, friends.getUser());
        assertNotNull(friends.getFriends());
        assertTrue(friends.getFriends().isEmpty());
    }

    @Test
    public void testFriendsSettersAndGetters() {
        User user = new User(); // Assuming User class is available
        User friend1 = new User(); // Assuming User class is available
        User friend2 = new User(); // Assuming User class is available
        Set<User> friendsSet = new HashSet<>();
        friendsSet.add(friend1);
        friendsSet.add(friend2);

        Friends friends = new Friends();
        friends.setUser(user);
        friends.setFriends(friendsSet);

        assertEquals(user, friends.getUser());
        assertEquals(friendsSet, friends.getFriends());
    }
}
