package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.dto.MovieGenreDTO;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.services.GenreService;
import com.example.diplomeBackend.services.MovieGenreService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class GenreMovieControllerTest {

    @InjectMocks
    private GenreMovieController genreMovieController;

    @Mock
    private GenreService genreService;

    @Mock
    private MovieGenreService movieGenreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllGenres_Success() {
        Genre genre = new Genre();
        genre.setGenreName("Action");
        List<Genre> genres = Collections.singletonList(genre);

        when(genreService.findAllGenres()).thenReturn(genres);

        ResponseEntity<List<Genre>> response = genreMovieController.getAllGenres();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genres, response.getBody());
    }

    @Test
    public void testGetAllGenres_NoContent() {
        when(genreService.findAllGenres()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Genre>> response = genreMovieController.getAllGenres();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void testGetGenreByName_Found() {
        Genre genre = new Genre();
        genre.setGenreName("Action");

        when(genreService.findByName(anyString())).thenReturn(Optional.of(genre));

        ResponseEntity<Genre> response = genreMovieController.getGenreByName("Action");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genre, response.getBody());
    }

    @Test
    public void testGetGenreByName_NotFound() {
        when(genreService.findByName(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Genre> response = genreMovieController.getGenreByName("Nonexistent");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateGenre() {
        Genre genre = new Genre();
        genre.setGenreName("Action");

        when(genreService.save(any(Genre.class))).thenReturn(genre);

        Genre result = genreMovieController.createGenre(genre);

        assertEquals(genre, result);
    }

    @Test
    public void testUpdateGenre_Success() {
        Genre existingGenre = new Genre();
        existingGenre.setGenreName("Action");
        Genre updatedGenre = new Genre();
        updatedGenre.setGenreName("Adventure");

        when(genreService.findByName(anyString())).thenReturn(Optional.of(existingGenre));
        when(genreService.save(any(Genre.class))).thenReturn(updatedGenre);

        ResponseEntity<Genre> response = genreMovieController.updateGenre("Action", updatedGenre);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedGenre, response.getBody());
    }

    @Test
    public void testUpdateGenre_NotFound() {
        Genre updatedGenre = new Genre();
        updatedGenre.setGenreName("Adventure");

        when(genreService.findByName(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Genre> response = genreMovieController.updateGenre("Nonexistent", updatedGenre);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteGenre_Success() {
        when(genreService.findByName(anyString())).thenReturn(Optional.of(new Genre()));

        ResponseEntity<Void> response = genreMovieController.deleteGenre("Action");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteGenre_NotFound() {
        when(genreService.findByName(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Void> response = genreMovieController.deleteGenre("Nonexistent");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetMoviesByGenre_Found() {
        MovieDTO movie = new MovieDTO();
        List<MovieDTO> movies = Collections.singletonList(movie);

        when(movieGenreService.findByGenreName(anyString())).thenReturn(movies);

        ResponseEntity<?> response = genreMovieController.getMoviesByGenre("Action");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    @Test
    public void testGetMoviesByGenre_NotFound() {
        when(movieGenreService.findByGenreName(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = genreMovieController.getMoviesByGenre("Action");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No movies found for the genre.", response.getBody());
    }

    @Test
    public void testAddMovieToGenre_Success() {
        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();
        Genre genre = new Genre();
        genre.setGenreName("Action");

        when(genreService.findByName(anyString())).thenReturn(Optional.of(genre));
        when(movieGenreService.save(any(MovieGenreDTO.class))).thenReturn(movieGenreDTO);

        MovieGenreDTO result = genreMovieController.addMovieToGenre("Action", movieGenreDTO);

        assertEquals(movieGenreDTO, result);
    }

    @Test
    public void testAddMovieToGenre_NotFound() {
        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();

        when(genreService.findByName(anyString())).thenReturn(Optional.empty());

        RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            genreMovieController.addMovieToGenre("Nonexistent", movieGenreDTO);
        });

        assertEquals("Genre not found", thrown.getMessage());
    }

    @Test
    public void testDeleteMovieGenre_Success() {
        when(movieGenreService.findById(anyLong())).thenReturn(Optional.of(new MovieGenreDTO()));

        ResponseEntity<Void> response = genreMovieController.deleteMovieGenre(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteMovieGenre_NotFound() {
        when(movieGenreService.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Void> response = genreMovieController.deleteMovieGenre(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
