package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.dto.UserDTO;
import com.example.diplomeBackend.dto.WatchlistDTO;
import com.example.diplomeBackend.exceptions.DatabaseException;
import com.example.diplomeBackend.exceptions.ErrorResponse;
import com.example.diplomeBackend.exceptions.InvalidInputException;
import com.example.diplomeBackend.exceptions.UserNotFoundException;
import com.example.diplomeBackend.mappers.MovieMapper;
import com.example.diplomeBackend.mappers.WatchlistMapper;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.Watchlist;
import com.example.diplomeBackend.repositories.MovieRepository;
import com.example.diplomeBackend.services.MovieActorService;
import com.example.diplomeBackend.services.MovieService;
import com.example.diplomeBackend.services.UserService;
import com.example.diplomeBackend.services.WatchlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class MovieControllerTest {

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieActorService movieActorService;

    @Mock
    private MovieService movieService;

    @Mock
    private UserService userService;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private WatchlistMapper watchlistMapper;

    @Mock
    private WatchlistService watchlistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMovies_Success() {
        MovieDTO movieDTO = new MovieDTO();
        List<MovieDTO> movies = Collections.singletonList(movieDTO);

        when(movieService.getAllMovies()).thenReturn(movies);

        ResponseEntity<List<MovieDTO>> response = movieController.getAllMovies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    @Test
    public void testGetAllMovies_NoContent() {
        when(movieService.getAllMovies()).thenReturn(Collections.emptyList());

        ResponseEntity<List<MovieDTO>> response = movieController.getAllMovies();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void testGetMoviesByCategory_Success() {
        MovieDTO movieDTO = new MovieDTO();
        List<MovieDTO> movies = Collections.singletonList(movieDTO);

        when(movieService.findMoviesByCategory(anyString())).thenReturn(movies);

        ResponseEntity<List<MovieDTO>> response = movieController.getMoviesByCategory("Action");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    @Test
    public void testGetMoviesByCategory_NoContent() {
        when(movieService.findMoviesByCategory(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<MovieDTO>> response = movieController.getMoviesByCategory("Action");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void testGetMovie_Success() {
        Movie movie = new Movie();
        MovieDTO movieDTO = new MovieDTO();

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));
        when(movieMapper.toDTO(any(Movie.class))).thenReturn(movieDTO);

        ResponseEntity<MovieDTO> response = movieController.getMovie(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieDTO, response.getBody());
    }

    @Test
    public void testGetMovie_NotFound() {
        when(movieRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<MovieDTO> response = movieController.getMovie(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetActorsByMovieId_Success() {
        ActorDTO actorDTO = new ActorDTO();
        List<ActorDTO> actors = Collections.singletonList(actorDTO);

        when(movieActorService.findActorsByMovieId(anyLong())).thenReturn(actors);

        ResponseEntity<List<ActorDTO>> response = movieController.getActorsByMovieId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(actors, response.getBody());
    }

    @Test
    public void testSearchMovies_Success() {
        MovieDTO movieDTO = new MovieDTO();
        List<MovieDTO> movies = Collections.singletonList(movieDTO);

        when(movieService.searchMovies(anyString())).thenReturn(movies);

        ResponseEntity<List<MovieDTO>> response = movieController.searchMovies("query");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    @Test
    public void testSearchMovies_NoContent() {
        when(movieService.searchMovies(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<MovieDTO>> response = movieController.searchMovies("query");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void testSearchMovies_InternalServerError() {
        when(movieService.searchMovies(anyString())).thenThrow(new RuntimeException());

        ResponseEntity<List<MovieDTO>> response = movieController.searchMovies("query");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Collections.singletonList(new MovieDTO()), response.getBody());
    }

    @Test
    public void testSearchUsers_Success() {
        UserDTO userDTO = new UserDTO();
        List<UserDTO> users = Collections.singletonList(userDTO);

        when(userService.searchUser(anyString())).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = movieController.searchUsers("query");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testSearchUsers_NoContent() {
        when(userService.searchUser(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserDTO>> response = movieController.searchUsers("query");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void testSearchUsers_InternalServerError() {
        when(userService.searchUser(anyString())).thenThrow(new RuntimeException());

        ResponseEntity<List<UserDTO>> response = movieController.searchUsers("query");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Collections.singletonList(new UserDTO()), response.getBody());
    }

    @Test
    public void testSaveToWatchlist_Success() {
        WatchlistDTO watchlistDTO = new WatchlistDTO();
        Watchlist watchlist = new Watchlist();

        when(watchlistMapper.toEntity(any(WatchlistDTO.class))).thenReturn(watchlist);
        when(watchlistService.save(any(Watchlist.class))).thenReturn(watchlist);
        when(watchlistMapper.toDTO(any(Watchlist.class))).thenReturn(watchlistDTO);

        ResponseEntity<WatchlistDTO> response = movieController.saveToWatchlist(watchlistDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(watchlistDTO, response.getBody());
    }

    @Test
    public void testSaveToWatchlist_InternalServerError() {
        when(watchlistMapper.toEntity(any(WatchlistDTO.class))).thenThrow(new RuntimeException());

        ResponseEntity<WatchlistDTO> response = movieController.saveToWatchlist(new WatchlistDTO());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveFromWatchlist_Success() {
        WatchlistDTO watchlistDTO = new WatchlistDTO();
        Watchlist watchlist = new Watchlist();

        when(watchlistMapper.toEntity(any(WatchlistDTO.class))).thenReturn(watchlist);

        ResponseEntity<Void> response = movieController.removeFromWatchlist(watchlistDTO);

        verify(watchlistService).deleteByUserIdAndMovieId(anyLong(), anyLong());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testRemoveFromWatchlist_InternalServerError() {
        when(watchlistMapper.toEntity(any(WatchlistDTO.class))).thenThrow(new RuntimeException());

        ResponseEntity<Void> response = movieController.removeFromWatchlist(new WatchlistDTO());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetMoviesByUserId_Success() {
        MovieDTO movieDTO = new MovieDTO();
        List<MovieDTO> movies = Collections.singletonList(movieDTO);

        when(watchlistService.getWatchlistByUserId(anyLong())).thenReturn(movies);

        ResponseEntity<List<MovieDTO>> response = movieController.getMoviesByUserId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    @Test
    public void testGetMoviesByUserId_NoContent() {
        when(watchlistService.getWatchlistByUserId(anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity<List<MovieDTO>> response = movieController.getMoviesByUserId(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void testGetMoviesByUserId_NotFound() {
        when(watchlistService.getWatchlistByUserId(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<List<MovieDTO>> response = movieController.getMoviesByUserId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void testGetRecommendations_Success() {
        MovieDTO movieDTO = new MovieDTO();
        List<MovieDTO> recommendedMovies = Collections.singletonList(movieDTO);

        when(movieService.recommendMovies(anyLong())).thenReturn(recommendedMovies);

        ResponseEntity<Object> response = movieController.getRecommendations(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recommendedMovies, response.getBody());
    }

    @Test
    public void testGetRecommendations_UserNotFound() {
        when(movieService.recommendMovies(anyLong())).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<Object> response = movieController.getRecommendations(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ErrorResponse("User Not Found", "The user with ID 1 was not found."), response.getBody());
    }

    @Test
    public void testGetRecommendations_InvalidInput() {
        when(movieService.recommendMovies(anyLong())).thenThrow(new InvalidInputException("Invalid input"));

        ResponseEntity<Object> response = movieController.getRecommendations(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(new ErrorResponse("Invalid Input", "The provided user ID is invalid."), response.getBody());
    }

    @Test
    public void testGetRecommendations_DatabaseException() {
        when(movieService.recommendMovies(anyLong())).thenThrow(new DatabaseException("Database error"));

        ResponseEntity<Object> response = movieController.getRecommendations(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(new ErrorResponse("Database Error", "An error occurred while accessing the database: Database error"), response.getBody());
    }

    @Test
    public void testGetRecommendations_InternalServerError() {
        when(movieService.recommendMovies(anyLong())).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<Object> response = movieController.getRecommendations(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(new ErrorResponse("Internal Server Error", "An unexpected error occurred: Unexpected error"), response.getBody());
    }
}
