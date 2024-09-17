package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(source = "director.directorName", target = "directorName")
    @Mapping(target = "genreNames", expression = "java(convertGenresToNames(movie.getGenres()))")
    @Mapping(target = "actorNames", expression = "java(convertActorsToNames(movie.getActors()))")
    MovieDTO toDTO(Movie movie);

    @Mapping(source = "directorName", target = "director.directorName")
    @Mapping(target = "genres", expression = "java(convertNamesToGenres(movieDTO.getGenreNames()))")
    @Mapping(target = "actors", expression = "java(convertNamesToActors(movieDTO.getActorNames()))")
    Movie toEntity(MovieDTO movieDTO);

    default Set<String> convertGenresToNames(Set<Genre> genres) {
        return genres.stream()
                .map(Genre::getGenreName)
                .collect(Collectors.toSet());
    }

    default Set<String> convertActorsToNames(Set<Actor> actors) {
        return actors.stream()
                .map(Actor::getActorName)
                .collect(Collectors.toSet());
    }

    default Set<Genre> convertNamesToGenres(Set<String> genreNames) {
        return genreNames.stream()
                .map(Genre::new)
                .collect(Collectors.toSet());
    }

    default Set<Actor> convertNamesToActors(Set<String> actorNames) {
        return actorNames.stream()
                .map(Actor::new)
                .collect(Collectors.toSet());
    }
}
