package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.dto.PreferenceDTO;
import com.example.diplomeBackend.exceptions.InvalidInputException;
import com.example.diplomeBackend.mappers.MovieMapper;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.repositories.MovieRepository;
import com.example.diplomeBackend.repositories.WatchlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private PreferenceService preferenceService;

    @Mock
    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setMovieId(movieId);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.findById(movieId);

        assertTrue(result.isPresent());
        assertEquals(movieId, result.get().getMovieId());
    }

    @Test
    void testSave() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieService.save(movie);

        assertNotNull(result);
        assertEquals("Test Movie", result.getTitle());
    }

    @Test
    void testDeleteById() {
        Long movieId = 1L;

        doNothing().when(movieRepository).deleteById(movieId);

        movieService.deleteById(movieId);

        verify(movieRepository, times(1)).deleteById(movieId);
    }

    @Test
    void testUpdateMovie() {
        Long movieId = 1L;
        Movie existingMovie = new Movie();
        existingMovie.setMovieId(movieId);

        Movie updatedDetails = new Movie();
        updatedDetails.setTitle("Updated Title");

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(existingMovie)).thenReturn(existingMovie);

        Movie result = movieService.updateMovie(movieId, updatedDetails);

        assertEquals("Updated Title", result.getTitle());
        verify(movieRepository, times(1)).save(existingMovie);
    }

    @Test
    void testSearchMovies() {
        String query = "Inception";
        Movie movie = new Movie();
        movie.setTitle("Inception");
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Inception");

        when(movieRepository.searchMovies(query)).thenReturn(List.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);

        List<MovieDTO> result = movieService.searchMovies(query);

        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }

    @Test
    void testRecommendMovies() {
        Long userId = 1L;
        PreferenceDTO preferenceDTO = new PreferenceDTO();
        preferenceDTO.setGenreName("Action");
        List<PreferenceDTO> favoritePreferences = List.of(preferenceDTO);
        List<PreferenceDTO> dislikedPreferences = Collections.emptyList();

        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setMovieId(1L);
        movie.setAverageRating(8.0);
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Test Movie");
        movieDTO.setMovieId(1L);
        movieDTO.setAverageRating(8.0);

        when(preferenceService.getFavoritePreferences(userId)).thenReturn(favoritePreferences);
        when(preferenceService.getDislikedPreferences(userId)).thenReturn(dislikedPreferences);
        when(watchlistRepository.findMoviesByUserId(userId)).thenReturn(Collections.emptyList());
        when(movieRepository.findAll()).thenReturn(List.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);

        List<MovieDTO> result = movieService.recommendMovies(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getTitle());
    }

    @Test
    void testRecommendMovies_InvalidUserId() {
        Long invalidUserId = -1L;

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            movieService.recommendMovies(invalidUserId);
        });

        assertEquals("User ID must be a positive number", exception.getMessage());
    }

    @Test
    void testGetAllMovies() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Test Movie");

        when(movieRepository.getAllMovies()).thenReturn(List.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);

        List<MovieDTO> result = movieService.getAllMovies();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getTitle());
    }

    @Test
    void testFindMoviesByCategory_TopRated() {
        Movie movie = new Movie();
        movie.setTitle("Top Rated Movie");
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Top Rated Movie");

        when(movieRepository.sortByRating()).thenReturn(List.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);

        List<MovieDTO> result = movieService.findMoviesByCategory("TopRated");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Top Rated Movie", result.get(0).getTitle());
    }

    @Test
    void testFindMoviesByCategory_Upcoming() {
        Movie movie = new Movie();
        movie.setTitle("Upcoming Movie");
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Upcoming Movie");

        when(movieRepository.sortByUpcoming()).thenReturn(List.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);

        List<MovieDTO> result = movieService.findMoviesByCategory("Upcoming");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Upcoming Movie", result.get(0).getTitle());
    }

    @Test
    void testFindMoviesByCategory_Popular() {
        Movie movie = new Movie();
        movie.setTitle("Popular Movie");
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Popular Movie");

        when(movieRepository.sortByPopular()).thenReturn(List.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);

        List<MovieDTO> result = movieService.findMoviesByCategory("Popular");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Popular Movie", result.get(0).getTitle());
    }
}
