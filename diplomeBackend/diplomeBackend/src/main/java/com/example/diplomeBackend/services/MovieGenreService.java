package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.dto.MovieGenreDTO;
import com.example.diplomeBackend.mappers.MovieMapper;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieGenre;
import com.example.diplomeBackend.mappers.MovieGenreMapper;
import com.example.diplomeBackend.repositories.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieGenreService {

    @Autowired
    private MovieGenreRepository movieGenreRepository;

    @Autowired
    private MovieGenreMapper movieGenreMapper;

    @Autowired
    private MovieMapper movieMapper;

    public List<MovieGenreDTO> findAll() {
        List<MovieGenre> movieGenres = movieGenreRepository.findAll();
        return movieGenres.stream()
                .map(movieGenreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovieGenreDTO> findById(Long id) {
        Optional<MovieGenre> movieGenre = movieGenreRepository.findById(id);
        return movieGenre.map(movieGenreMapper::toDTO);
    }

    public List<MovieDTO> findByGenreName(String genreName) {
        List<Movie> movieGenres = movieGenreRepository.findByGenreName(genreName);
        return movieGenres.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MovieGenreDTO save(MovieGenreDTO movieGenreDTO) {
        MovieGenre movieGenre = movieGenreMapper.toEntity(movieGenreDTO);
        MovieGenre savedMovieGenre = movieGenreRepository.save(movieGenre);
        return movieGenreMapper.toDTO(savedMovieGenre);
    }

    public void deleteById(Long id) {
        movieGenreRepository.deleteById(id);
    }
}
