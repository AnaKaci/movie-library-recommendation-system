package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieDTOTest {

    @Test
    void testMovieDTOConstructorAndGettersSetters() {
        // Arrange
        Long movieId = 1L;
        String title = "Inception";
        String description = "A mind-bending thriller";
        Date releaseDate = new Date();
        int duration = 148;
        String directorName = "Christopher Nolan";
        String poster = "posterUrl";
        String trailer = "trailerUrl";
        Double averageRating = 8.8;

        Set<String> genreNames = new HashSet<>();
        genreNames.add("Sci-Fi");
        genreNames.add("Thriller");

        Set<String> actorNames = new HashSet<>();
        actorNames.add("Leonardo DiCaprio");
        actorNames.add("Joseph Gordon-Levitt");

        // Act
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieId(movieId);
        movieDTO.setTitle(title);
        movieDTO.setDescription(description);
        movieDTO.setReleaseDate(releaseDate);
        movieDTO.setDuration(duration);
        movieDTO.setDirectorName(directorName);
        movieDTO.setPoster(poster);
        movieDTO.setTrailer(trailer);
        movieDTO.setAverageRating(averageRating);
        movieDTO.setGenreNames(genreNames);
        movieDTO.setActorNames(actorNames);

        // Assert
        assertEquals(movieId, movieDTO.getMovieId());
        assertEquals(title, movieDTO.getTitle());
        assertEquals(description, movieDTO.getDescription());
        assertEquals(releaseDate, movieDTO.getReleaseDate());
        assertEquals(duration, movieDTO.getDuration());
        assertEquals(directorName, movieDTO.getDirectorName());
        assertEquals(poster, movieDTO.getPoster());
        assertEquals(trailer, movieDTO.getTrailer());
        assertEquals(averageRating, movieDTO.getAverageRating());
        assertEquals(genreNames, movieDTO.getGenreNames());
        assertEquals(actorNames, movieDTO.getActorNames());
    }

    @Test
    void testMovieDTOSetters() {
        // Arrange
        MovieDTO movieDTO = new MovieDTO();
        Long movieId = 1L;
        String title = "Inception";
        String description = "A mind-bending thriller";
        Date releaseDate = new Date();
        int duration = 148;
        String directorName = "Christopher Nolan";
        String poster = "posterUrl";
        String trailer = "trailerUrl";
        Double averageRating = 8.8;

        Set<String> genreNames = new HashSet<>();
        genreNames.add("Sci-Fi");
        genreNames.add("Thriller");

        Set<String> actorNames = new HashSet<>();
        actorNames.add("Leonardo DiCaprio");
        actorNames.add("Joseph Gordon-Levitt");

        // Act
        movieDTO.setMovieId(movieId);
        movieDTO.setTitle(title);
        movieDTO.setDescription(description);
        movieDTO.setReleaseDate(releaseDate);
        movieDTO.setDuration(duration);
        movieDTO.setDirectorName(directorName);
        movieDTO.setPoster(poster);
        movieDTO.setTrailer(trailer);
        movieDTO.setAverageRating(averageRating);
        movieDTO.setGenreNames(genreNames);
        movieDTO.setActorNames(actorNames);

        // Assert
        assertEquals(movieId, movieDTO.getMovieId());
        assertEquals(title, movieDTO.getTitle());
        assertEquals(description, movieDTO.getDescription());
        assertEquals(releaseDate, movieDTO.getReleaseDate());
        assertEquals(duration, movieDTO.getDuration());
        assertEquals(directorName, movieDTO.getDirectorName());
        assertEquals(poster, movieDTO.getPoster());
        assertEquals(trailer, movieDTO.getTrailer());
        assertEquals(averageRating, movieDTO.getAverageRating());
        assertEquals(genreNames, movieDTO.getGenreNames());
        assertEquals(actorNames, movieDTO.getActorNames());
    }
}
