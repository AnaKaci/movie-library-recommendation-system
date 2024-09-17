package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.GenreDTO;
import com.example.diplomeBackend.mappers.GenreMapper;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.repositories.GenreRepository;
import com.example.diplomeBackend.repositories.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MovieGenreRepository movieGenreRepository;
    @Autowired
    private GenreMapper genreMapper;

    public List<Genre> findAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres;
    }
    public List<GenreDTO> findAllGenresForPreference() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Genre> findByName(String genreName) {
        return genreRepository.findByGenreName(genreName);
    }

    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteByName(String genreName) {
        Genre genre = genreRepository.findByGenreName(genreName)
                .orElseThrow(() -> new RuntimeException("Genre not found: " + genreName));
        movieGenreRepository.deleteByGenreName(genre.getGenreName());
        genreRepository.deleteByGenreName(genreName);
    }

}
