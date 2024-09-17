package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FriendsDTOTest {

    @Test
    void testFriendsDTOConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        Long userId = 2L;
        List<Long> friendIds = Arrays.asList(10L, 20L, 30L);

        // Act
        FriendsDTO friendsDTO = new FriendsDTO();
        friendsDTO.setId(id);
        friendsDTO.setUserId(userId);
        friendsDTO.setFriendIds(friendIds);

        // Assert
        assertEquals(id, friendsDTO.getId());
        assertEquals(userId, friendsDTO.getUserId());
        assertEquals(friendIds, friendsDTO.getFriendIds());
    }

    @Test
    void testFriendsDTOSetters() {
        // Arrange
        FriendsDTO friendsDTO = new FriendsDTO();

        Long id = 3L;
        Long userId = 4L;
        List<Long> friendIds = Arrays.asList(40L, 50L, 60L);

        // Act
        friendsDTO.setId(id);
        friendsDTO.setUserId(userId);
        friendsDTO.setFriendIds(friendIds);

        // Assert
        assertEquals(id, friendsDTO.getId());
        assertEquals(userId, friendsDTO.getUserId());
        assertEquals(friendIds, friendsDTO.getFriendIds());
    }
}
