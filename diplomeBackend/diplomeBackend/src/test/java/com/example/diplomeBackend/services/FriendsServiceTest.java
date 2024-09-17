package com.example.diplomeBackend.services;

import com.example.diplomeBackend.models.Friends;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.repositories.FriendsRepository;
import com.example.diplomeBackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FriendsServiceTest {

    @InjectMocks
    private FriendsService friendsService;

    @Mock
    private FriendsRepository friendsRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFriend() {
        User user = new User();
        user.setUserId(1L);
        User friend = new User();
        friend.setUserId(2L);

        Friends friends = new Friends(user);
        friends.setFriends(new HashSet<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(friend));
        when(friendsRepository.findByUser(user)).thenReturn(Optional.of(friends));
        when(friendsRepository.save(any(Friends.class))).thenReturn(friends);

        Friends updatedFriends = friendsService.addFriend(1L, 2L);

        assertTrue(updatedFriends.getFriends().contains(friend));
        verify(friendsRepository, times(1)).save(updatedFriends);
    }

    @Test
    void testRemoveFriend() {
        User user = new User();
        user.setUserId(1L);
        User friend = new User();
        friend.setUserId(2L);

        Friends friends = new Friends(user);
        friends.setFriends(new HashSet<>(Set.of(friend)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(friend));
        when(friendsRepository.findByUser(user)).thenReturn(Optional.of(friends));
        when(friendsRepository.save(any(Friends.class))).thenReturn(friends);

        friendsService.removeFriend(1L, 2L);

        assertFalse(friends.getFriends().contains(friend));
        verify(friendsRepository, times(1)).save(friends);
    }

    @Test
    void testGetAllFriends() {
        User user = new User();
        user.setUserId(1L);
        User friend1 = new User();
        friend1.setUserId(2L);
        User friend2 = new User();
        friend2.setUserId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(friendsRepository.getAllUserFriends(user)).thenReturn(List.of(friend1, friend2));

        List<User> friends = friendsService.getAllFriends(1L);

        assertNotNull(friends);
        assertEquals(2, friends.size());
        assertTrue(friends.contains(friend1));
        assertTrue(friends.contains(friend2));
    }

    @Test
    void testAreMutualFriends() {
        User sender = new User();
        sender.setUserId(1L);
        User receiver = new User();
        receiver.setUserId(2L);
        User mutualFriend = new User();
        mutualFriend.setUserId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));
        when(friendsRepository.getAllUserFriends(sender)).thenReturn(List.of(mutualFriend));
        when(friendsRepository.getAllUserFriends(receiver)).thenReturn(List.of(mutualFriend));

        boolean areMutualFriends = friendsService.areMutualFriends(1L, 2L);

        assertTrue(areMutualFriends);
    }

    @Test
    void testGetMutualFriends() {
        User sender = new User();
        sender.setUserId(1L);
        User receiver = new User();
        receiver.setUserId(2L);
        User mutualFriend = new User();
        mutualFriend.setUserId(3L);

        when(friendsRepository.getAllUserFriends(sender)).thenReturn(List.of(mutualFriend));
        when(friendsRepository.getAllUserFriends(receiver)).thenReturn(List.of(mutualFriend));

        List<User> mutualFriends = friendsService.getMutualFriends(sender, receiver);

        assertNotNull(mutualFriends);
        assertEquals(1, mutualFriends.size());
        assertTrue(mutualFriends.contains(mutualFriend));
    }

    @Test
    void testAddFriendUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            friendsService.addFriend(1L, 2L);
        });
    }

    @Test
    void testRemoveFriendUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            friendsService.removeFriend(1L, 2L);
        });
    }

    @Test
    void testGetAllFriendsUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            friendsService.getAllFriends(1L);
        });
    }

    @Test
    void testAreMutualFriendsUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            friendsService.areMutualFriends(1L, 2L);
        });
    }

    @Test
    void testGetMutualFriendsUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            friendsService.getMutualFriends(new User(), new User());
        });
    }
}
