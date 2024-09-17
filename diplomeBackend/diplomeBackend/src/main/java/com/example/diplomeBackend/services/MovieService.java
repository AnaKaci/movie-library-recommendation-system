package com.example.diplomeBackend.services;


import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.dto.PreferenceDTO;
import com.example.diplomeBackend.exceptions.DatabaseException;
import com.example.diplomeBackend.exceptions.InvalidInputException;
import com.example.diplomeBackend.mappers.PreferenceMapper;
import com.example.diplomeBackend.models.Movie;
import org.hibernate.Hibernate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diplomeBackend.mappers.MovieMapper;
import com.example.diplomeBackend.repositories.*;// Import the mapper

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private MovieMapper movieMapper;
    private static final Logger logger = Logger.getLogger(MovieService.class.getName());

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(movieDetails.getTitle());
                    movie.setDescription(movieDetails.getDescription());
                    movie.setReleaseDate(movieDetails.getReleaseDate());
                    movie.setDuration(movieDetails.getDuration());
                    movie.setDirector(movieDetails.getDirector());
                    movie.setPoster(movieDetails.getPoster());
                    movie.setTrailer(movieDetails.getTrailer());
                    movie.setAverageRating(movieDetails.getAverageRating());
                    movie.setFeedbacks(movieDetails.getFeedbacks());
                    movie.setGenres(movieDetails.getGenres());
                    movie.setActors(movieDetails.getActors());
                    return movieRepository.save(movie);
                })
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public List<MovieDTO> searchMovies(String query) {
        return movieRepository.searchMovies(query).stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> recommendMovies(Long userId) {
        if (userId == null || userId <= 0) {
            throw new InvalidInputException("User ID must be a positive number");
        }

        try {

            List<PreferenceDTO> userPreferences = preferenceService.getFavoritePreferences(userId);

            // Extract preferred genres, actors, and directors
            Set<String> preferredGenres = userPreferences.stream()
                    .map(PreferenceDTO::getGenreName)
                    .collect(Collectors.toSet());

            Set<String> preferredActors = userPreferences.stream()
                    .map(PreferenceDTO::getActorName)
                    .collect(Collectors.toSet());

            Set<String> preferredDirectors = userPreferences.stream()
                    .map(PreferenceDTO::getDirectorName)
                    .collect(Collectors.toSet());


            List<PreferenceDTO> dislikedPreferences = preferenceService.getDislikedPreferences(userId);

            // Extract disliked genres, actors, and directors
            Set<String> dislikedGenres = dislikedPreferences.stream()
                    .map(PreferenceDTO::getGenreName)
                    .collect(Collectors.toSet());

            Set<String> dislikedActors = dislikedPreferences.stream()
                    .map(PreferenceDTO::getActorName)
                    .collect(Collectors.toSet());

            Set<String> dislikedDirectors = dislikedPreferences.stream()
                    .map(PreferenceDTO::getDirectorName)
                    .collect(Collectors.toSet());

            // Get watched movie IDs
            List<Movie> userWatchlist = watchlistRepository.findMoviesByUserId(userId);
            Set<Long> watchedMovieIds = userWatchlist.stream()
                    .map(Movie::getMovieId)
                    .collect(Collectors.toSet());

            // Fetch all movies and filter based on preferences and dislikes
            List<MovieDTO> recommendedMovies = movieRepository.findAll().stream()
                    .map(movieMapper::toDTO)
                    .filter(movie -> (movie.getGenreNames().stream().anyMatch(preferredGenres::contains) ||
                            movie.getActorNames().stream().anyMatch(preferredActors::contains) ||
                            preferredDirectors.contains(movie.getDirectorName())) &&
                            !dislikedGenres.contains(movie.getGenreNames()) &&
                            !dislikedActors.contains(movie.getActorNames()) &&
                            !dislikedDirectors.contains(movie.getDirectorName()))
                    .filter(movie -> !watchedMovieIds.contains(movie.getMovieId()))
                    .sorted((m1, m2) -> m2.getAverageRating().compareTo(m1.getAverageRating()))
                    .limit(10)
                    .collect(Collectors.toList());

            logger.info(String.format("Recommended movies for userId %d: %s", userId, recommendedMovies));
            return recommendedMovies;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while recommending movies for userId " + userId, e);
            throw new DatabaseException("An error occurred while fetching recommendations.", e);
        }
    }



    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.getAllMovies();
        return movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> findMoviesByCategory(String category) {
        List<Movie> movies;

        String normalizedCategory = category.replaceAll("\\s", "").toLowerCase();

        if ("toprated".equals(normalizedCategory)) {
            movies = movieRepository.sortByRating();
        } else if ("upcoming".equals(normalizedCategory)){
            movies = movieRepository.sortByUpcoming();
        } else {
            movies = movieRepository.sortByPopular();
        }

        movies.forEach(movie -> Hibernate.initialize(movie.getDirector()));

        return movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }
}
