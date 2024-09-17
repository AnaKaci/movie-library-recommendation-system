package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MovieActorDTO;
import com.example.diplomeBackend.models.MovieActor;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MovieActorMapper {

    @Mapping(source = "movie.movieId", target = "movieId")
    @Mapping(source = "actor.actorName", target = "actorName")
    MovieActorDTO toDTO(MovieActor movieActor);

    @Mapping(source = "movieId", target = "movie.movieId")
    @Mapping(source = "actorName", target = "actor.actorName")
    MovieActor toEntity(MovieActorDTO movieActorDTO);

    @Named("mapActorName")
    default Actor mapActorName(String actorName) {
        return new Actor(actorName);
    }

    @Named("mapMovieId")
    default Movie mapMovieId(Long movieId) {
        return new Movie(movieId);
    }
}
