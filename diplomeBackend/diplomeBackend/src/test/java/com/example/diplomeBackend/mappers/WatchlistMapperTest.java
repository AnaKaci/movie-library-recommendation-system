package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.WatchlistDTO;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.models.Watchlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WatchlistMapperTest {

    private WatchlistMapper watchlistMapper;

    @BeforeEach
    void setUp() {
        watchlistMapper = Mappers.getMapper(WatchlistMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        User user = new User();
        user.setUserId(1L);

        Movie movie = new Movie();
        movie.setMovieId(2L);

        Watchlist watchlist = new Watchlist();
        watchlist.setWatchlistId(3L);
        watchlist.setUser(user);
        watchlist.setMovie(movie);

        // Act
        WatchlistDTO watchlistDTO = watchlistMapper.toDTO(watchlist);

        // Assert
        assertNotNull(watchlistDTO);
        assertEquals(1L, watchlistDTO.getUserId());
        assertEquals(2L, watchlistDTO.getMovieId());
    }

    @Test
    void testToEntity() {
        // Arrange
        WatchlistDTO watchlistDTO = new WatchlistDTO();
        watchlistDTO.setId(3L);
        watchlistDTO.setUserId(1L);
        watchlistDTO.setMovieId(2L);

        // Act
        Watchlist watchlist = watchlistMapper.toEntity(watchlistDTO);

        // Assert
        assertNotNull(watchlist);
        assertEquals(1L, watchlist.getUser().getUserId());
        assertEquals(2L, watchlist.getMovie().getMovieId());
    }
}
