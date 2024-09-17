package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MovieGenreDTO;
import com.example.diplomeBackend.models.MovieGenre;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MovieGenreMapper {

    @Mapping(source = "movie.movieId", target = "movieId")
    @Mapping(source = "genre.genreName", target = "genreName")
    MovieGenreDTO toDTO(MovieGenre movieGenre);

    @Mapping(source = "movieId", target = "movie.movieId")
    @Mapping(source = "genreName", target = "genre.genreName")
    MovieGenre toEntity(MovieGenreDTO movieGenreDTO);

    @Named("mapGenreName")
    default Genre mapGenreName(String genreName) {
        return new Genre(genreName);
    }

    @Named("mapMovieId")
    default Movie mapMovieId(Long movieId) {
        return new Movie(movieId);
    }
}
