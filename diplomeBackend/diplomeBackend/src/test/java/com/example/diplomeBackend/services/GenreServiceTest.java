package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.GenreDTO;
import com.example.diplomeBackend.mappers.GenreMapper;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.repositories.GenreRepository;
import com.example.diplomeBackend.repositories.MovieGenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GenreServiceTest {

    @InjectMocks
    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private MovieGenreRepository movieGenreRepository;

    @Mock
    private GenreMapper genreMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllGenres() {
        Genre genre1 = new Genre();
        genre1.setGenreName("Action");
        Genre genre2 = new Genre();
        genre2.setGenreName("Comedy");

        when(genreRepository.findAll()).thenReturn(List.of(genre1, genre2));

        List<Genre> genres = genreService.findAllGenres();

        assertNotNull(genres);
        assertEquals(2, genres.size());
        assertTrue(genres.contains(genre1));
        assertTrue(genres.contains(genre2));
    }

    @Test
    void testFindAllGenresForPreference() {
        Genre genre1 = new Genre();
        genre1.setGenreName("Action");
        Genre genre2 = new Genre();
        genre2.setGenreName("Comedy");

        GenreDTO genreDTO1 = new GenreDTO();
        genreDTO1.setGenreName("Action");
        GenreDTO genreDTO2 = new GenreDTO();
        genreDTO2.setGenreName("Comedy");

        when(genreRepository.findAll()).thenReturn(List.of(genre1, genre2));
        when(genreMapper.toDTO(genre1)).thenReturn(genreDTO1);
        when(genreMapper.toDTO(genre2)).thenReturn(genreDTO2);

        List<GenreDTO> genreDTOs = genreService.findAllGenresForPreference();

        assertNotNull(genreDTOs);
        assertEquals(2, genreDTOs.size());
        assertTrue(genreDTOs.contains(genreDTO1));
        assertTrue(genreDTOs.contains(genreDTO2));
    }

    @Test
    void testFindByName() {
        Genre genre = new Genre();
        genre.setGenreName("Action");

        when(genreRepository.findByGenreName("Action")).thenReturn(Optional.of(genre));

        Optional<Genre> foundGenre = genreService.findByName("Action");

        assertTrue(foundGenre.isPresent());
        assertEquals("Action", foundGenre.get().getGenreName());
    }

    @Test
    void testSave() {
        Genre genre = new Genre();
        genre.setGenreName("Action");

        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        Genre savedGenre = genreService.save(genre);

        assertNotNull(savedGenre);
        assertEquals("Action", savedGenre.getGenreName());
    }

    @Test
    void testDeleteByName() {
        Genre genre = new Genre();
        genre.setGenreName("Action");

        when(genreRepository.findByGenreName("Action")).thenReturn(Optional.of(genre));
        doNothing().when(movieGenreRepository).deleteByGenreName("Action");
        doNothing().when(genreRepository).deleteByGenreName("Action");

        genreService.deleteByName("Action");

        verify(movieGenreRepository, times(1)).deleteByGenreName("Action");
        verify(genreRepository, times(1)).deleteByGenreName("Action");
    }

    @Test
    void testDeleteByNameGenreNotFound() {
        when(genreRepository.findByGenreName("Action")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            genreService.deleteByName("Action");
        });

        assertEquals("Genre not found: Action", exception.getMessage());
    }
}
