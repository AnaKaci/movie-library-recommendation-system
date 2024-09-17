package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
    void testUserDTOConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        String username = "johndoe";
        String email = "johndoe@example.com";
        List<Long> watchlistIds = Arrays.asList(101L, 102L, 103L);
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date(); // Using current date for the test

        // Act
        UserDTO userDTO = new UserDTO(id, username, email, watchlistIds, firstName, lastName, dateOfBirth);

        // Assert
        assertEquals(id, userDTO.getId());
        assertEquals(username, userDTO.getUsername());
        assertEquals(email, userDTO.getEmail());
        assertEquals(watchlistIds, userDTO.getWatchlistIds());
        assertEquals(firstName, userDTO.getFirstName());
        assertEquals(lastName, userDTO.getLastName());
        assertEquals(dateOfBirth, userDTO.getDateOfBirth());
    }

    @Test
    void testUserDTOSetters() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        Long id = 1L;
        String username = "johndoe";
        String email = "johndoe@example.com";
        List<Long> watchlistIds = Arrays.asList(101L, 102L, 103L);
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date(); // Using current date for the test

        // Act
        userDTO.setId(id);
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        userDTO.setWatchlistIds(watchlistIds);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setDateOfBirth(dateOfBirth);

        // Assert
        assertEquals(id, userDTO.getId());
        assertEquals(username, userDTO.getUsername());
        assertEquals(email, userDTO.getEmail());
        assertEquals(watchlistIds, userDTO.getWatchlistIds());
        assertEquals(firstName, userDTO.getFirstName());
        assertEquals(lastName, userDTO.getLastName());
        assertEquals(dateOfBirth, userDTO.getDateOfBirth());
    }
}
