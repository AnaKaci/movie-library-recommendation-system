package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.FriendsDTO;
import com.example.diplomeBackend.models.Friends;
import com.example.diplomeBackend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FriendsMapperTest {

    private FriendsMapper friendsMapper;

    @BeforeEach
    void setUp() {
        friendsMapper = Mappers.getMapper(FriendsMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        User user1 = new User();
        user1.setUserId(1L);

        User user2 = new User();
        user2.setUserId(2L);

        Set<User> friends = new HashSet<>();
        friends.add(user1);
        friends.add(user2);

        User mainUser = new User();
        mainUser.setUserId(3L);

        Friends friendsEntity = new Friends();
        friendsEntity.setId(100L);
        friendsEntity.setUser(mainUser);
        friendsEntity.setFriends(friends);

        // Act
        FriendsDTO friendsDTO = friendsMapper.toDTO(friendsEntity);

        // Assert
        assertNotNull(friendsDTO);
        assertEquals(100L, friendsDTO.getId());
        assertEquals(3L, friendsDTO.getUserId());
        assertEquals(2, friendsDTO.getFriendIds().size());
        assertTrue(friendsDTO.getFriendIds().contains(1L));
        assertTrue(friendsDTO.getFriendIds().contains(2L));
    }

    @Test
    void testToEntity() {
        // Arrange
        FriendsDTO friendsDTO = new FriendsDTO();
        friendsDTO.setId(100L);
        friendsDTO.setUserId(3L);
        friendsDTO.setFriendIds(List.of(1L, 2L));

        User user = new User();
        user.setUserId(3L);

        // Act
        Friends friendsEntity = friendsMapper.toEntity(friendsDTO, user);

        // Assert
        assertNotNull(friendsEntity);
        assertEquals(100L, friendsEntity.getId());
        assertNotNull(friendsEntity.getUser());
        assertEquals(3L, friendsEntity.getUser().getUserId());
        assertNull(friendsEntity.getFriends()); // Ensure friends are not set as per mapping logic
    }
}
