package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.services.GenreService;
import com.example.diplomeBackend.services.MovieGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
public class GenreMovieController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private MovieGenreService movieGenreService;

    public GenreMovieController(GenreService genreService, MovieGenreService movieGenreService){
        this.genreService = genreService;
        this.movieGenreService = movieGenreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        try {
            List<Genre> genres = genreService.findAllGenres();
            if (genres.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(genres);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/{genreName}")
    public ResponseEntity<Genre> getGenreByName(@PathVariable String genreName) {
        Optional<Genre> genre = genreService.findByName(genreName);
        return genre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{genreName}/movies")
    public ResponseEntity<?> getMoviesByGenre(@PathVariable String genreName) {
        List<MovieDTO> movies = movieGenreService.findByGenreName(genreName);
        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No movies found for the genre.");
        }
        return ResponseEntity.ok(movies);
    }


}
