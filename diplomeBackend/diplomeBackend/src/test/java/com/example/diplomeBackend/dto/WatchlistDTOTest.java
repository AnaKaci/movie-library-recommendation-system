package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WatchlistDTOTest {

    @Test
    void testWatchlistDTOConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        Long userId = 2L;
        Long movieId = 3L;
        Date dateAdded = new Date(); // Using current date for the test

        // Act
        WatchlistDTO watchlistDTO = new WatchlistDTO();
        watchlistDTO.setId(id);
        watchlistDTO.setUserId(userId);
        watchlistDTO.setMovieId(movieId);
        watchlistDTO.setDateAdded(dateAdded);

        // Assert
        assertEquals(id, watchlistDTO.getId());
        assertEquals(userId, watchlistDTO.getUserId());
        assertEquals(movieId, watchlistDTO.getMovieId());
        assertEquals(dateAdded, watchlistDTO.getDateAdded());
    }

    @Test
    void testWatchlistDTOSetters() {
        // Arrange
        WatchlistDTO watchlistDTO = new WatchlistDTO();
        Long id = 1L;
        Long userId = 2L;
        Long movieId = 3L;
        Date dateAdded = new Date(); // Using current date for the test

        // Act
        watchlistDTO.setId(id);
        watchlistDTO.setUserId(userId);
        watchlistDTO.setMovieId(movieId);
        watchlistDTO.setDateAdded(dateAdded);

        // Assert
        assertEquals(id, watchlistDTO.getId());
        assertEquals(userId, watchlistDTO.getUserId());
        assertEquals(movieId, watchlistDTO.getMovieId());
        assertEquals(dateAdded, watchlistDTO.getDateAdded());
    }
}
