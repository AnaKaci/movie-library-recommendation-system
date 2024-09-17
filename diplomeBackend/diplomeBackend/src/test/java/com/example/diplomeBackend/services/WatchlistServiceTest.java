package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.mappers.MovieMapper;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.Watchlist;
import com.example.diplomeBackend.repositories.WatchlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WatchlistServiceTest {

    @InjectMocks
    private WatchlistService watchlistService;

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Watchlist watchlist = new Watchlist();
        when(watchlistRepository.findAll()).thenReturn(List.of(watchlist));

        List<Watchlist> result = watchlistService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(watchlist, result.get(0));
        verify(watchlistRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long watchlistId = 1L;
        Watchlist watchlist = new Watchlist();
        when(watchlistRepository.findById(watchlistId)).thenReturn(Optional.of(watchlist));

        Optional<Watchlist> result = watchlistService.findById(watchlistId);

        assertTrue(result.isPresent());
        assertEquals(watchlist, result.get());
        verify(watchlistRepository, times(1)).findById(watchlistId);
    }

    @Test
    void testSave() {
        Watchlist watchlist = new Watchlist();
        when(watchlistRepository.save(watchlist)).thenReturn(watchlist);

        Watchlist result = watchlistService.save(watchlist);

        assertNotNull(result);
        assertEquals(watchlist, result);
        verify(watchlistRepository, times(1)).save(watchlist);
    }

    @Test
    void testDeleteById() {
        Long watchlistId = 1L;

        doNothing().when(watchlistRepository).deleteById(watchlistId);

        watchlistService.deleteById(watchlistId);

        verify(watchlistRepository, times(1)).deleteById(watchlistId);
    }

    @Test
    void testGetWatchlistByUserId() {
        Long userId = 1L;
        Movie movie = new Movie();
        MovieDTO movieDTO = new MovieDTO();

        when(watchlistRepository.findMoviesByUserId(userId)).thenReturn(List.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);

        List<MovieDTO> result = watchlistService.getWatchlistByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(movieDTO, result.get(0));
        verify(watchlistRepository, times(1)).findMoviesByUserId(userId);
        verify(movieMapper, times(1)).toDTO(movie);
    }

    @Test
    void testDeleteByUserIdAndMovieId() {
        Long userId = 1L;
        Long movieId = 1L;

        doNothing().when(watchlistRepository).deleteByUserIdAndMovieId(userId, movieId);

        watchlistService.deleteByUserIdAndMovieId(userId, movieId);

        verify(watchlistRepository, times(1)).deleteByUserIdAndMovieId(userId, movieId);
    }
}
