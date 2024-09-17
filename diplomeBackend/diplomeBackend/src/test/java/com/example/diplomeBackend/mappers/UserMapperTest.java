package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.UserDTO;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.models.Watchlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        Watchlist watchlist1 = new Watchlist();
        watchlist1.setWatchlistId(1L);
        Watchlist watchlist2 = new Watchlist();
        watchlist2.setWatchlistId(2L);

        User user = new User();
        user.setUserId(1L);
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDateOfBirth(new Date());
        user.setWatchlists(Arrays.asList(watchlist1, watchlist2));

        // Act
        UserDTO userDTO = userMapper.toDTO(user);

        // Assert
        assertNotNull(userDTO);
        assertEquals(1L, userDTO.getId());
        assertEquals("john_doe", userDTO.getUsername());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals("John", userDTO.getFirstName());
        assertEquals("Doe", userDTO.getLastName());
        assertNotNull(userDTO.getDateOfBirth());
        assertEquals(Arrays.asList(1L, 2L), userDTO.getWatchlistIds());
    }

    @Test
    void testToEntity() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("john_doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setDateOfBirth(new Date());
        userDTO.setWatchlistIds(Arrays.asList(1L, 2L));

        // Act
        User user = userMapper.toEntity(userDTO);

        // Assert
        assertNotNull(user);
        assertEquals(1L, user.getUserId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertNotNull(user.getDateOfBirth());
        // watchlists are not set in the mapper, so only check non-null
        assertNull(user.getWatchlists());
    }
}
