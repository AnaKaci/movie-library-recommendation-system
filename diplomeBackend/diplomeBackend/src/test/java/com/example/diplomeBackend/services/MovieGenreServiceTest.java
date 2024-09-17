package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.dto.MovieGenreDTO;
import com.example.diplomeBackend.mappers.MovieGenreMapper;
import com.example.diplomeBackend.mappers.MovieMapper;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieGenre;
import com.example.diplomeBackend.repositories.MovieGenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieGenreServiceTest {

    @InjectMocks
    private MovieGenreService movieGenreService;

    @Mock
    private MovieGenreRepository movieGenreRepository;

    @Mock
    private MovieGenreMapper movieGenreMapper;

    @Mock
    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        MovieGenre movieGenre = new MovieGenre();
        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();
        when(movieGenreRepository.findAll()).thenReturn(List.of(movieGenre));
        when(movieGenreMapper.toDTO(movieGenre)).thenReturn(movieGenreDTO);

        List<MovieGenreDTO> result = movieGenreService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movieGenreRepository, times(1)).findAll();
        verify(movieGenreMapper, times(1)).toDTO(movieGenre);
    }

    @Test
    void testFindById() {
        Long genreId = 1L;
        MovieGenre movieGenre = new MovieGenre();
        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();
        when(movieGenreRepository.findById(genreId)).thenReturn(Optional.of(movieGenre));
        when(movieGenreMapper.toDTO(movieGenre)).thenReturn(movieGenreDTO);

        Optional<MovieGenreDTO> result = movieGenreService.findById(genreId);

        assertTrue(result.isPresent());
        assertEquals(movieGenreDTO, result.get());
        verify(movieGenreRepository, times(1)).findById(genreId);
        verify(movieGenreMapper, times(1)).toDTO(movieGenre);
    }

    @Test
    void testFindByGenreName() {
        String genreName = "Action";
        Movie movie = new Movie();
        MovieDTO movieDTO = new MovieDTO();

        when(movieGenreRepository.findByGenreNameWithSorting(genreName)).thenReturn(List.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);

        List<MovieDTO> result = movieGenreService.findByGenreName(genreName);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(movieDTO, result.get(0));
        verify(movieGenreRepository, times(1)).findByGenreNameWithSorting(genreName);
        verify(movieMapper, times(1)).toDTO(movie);
    }

    @Test
    void testSave() {
        MovieGenre movieGenre = new MovieGenre();
        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();

        when(movieGenreMapper.toEntity(movieGenreDTO)).thenReturn(movieGenre);
        when(movieGenreRepository.save(movieGenre)).thenReturn(movieGenre);
        when(movieGenreMapper.toDTO(movieGenre)).thenReturn(movieGenreDTO);

        MovieGenreDTO result = movieGenreService.save(movieGenreDTO);

        assertNotNull(result);
        assertEquals(movieGenreDTO, result);
        verify(movieGenreMapper, times(1)).toEntity(movieGenreDTO);
        verify(movieGenreRepository, times(1)).save(movieGenre);
        verify(movieGenreMapper, times(1)).toDTO(movieGenre);
    }

    @Test
    void testDeleteById() {
        Long genreId = 1L;

        doNothing().when(movieGenreRepository).deleteById(genreId);

        movieGenreService.deleteById(genreId);

        verify(movieGenreRepository, times(1)).deleteById(genreId);
    }
}
