package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    @Mappings({
            @Mapping(source = "actorName", target = "name"),
            @Mapping(source = "movies", target = "movieIds", qualifiedByName = "moviesToMovieIds")
    })
    ActorDTO toDTO(Actor actor);

    @Mappings({
            @Mapping(source = "name", target = "actorName"),
            @Mapping(target = "movies", ignore = true)
    })
    Actor toEntity(ActorDTO actorDTO);

    @Named("moviesToMovieIds")
    default Set<Long> moviesToMovieIds(Set<Movie> movies) {
        return movies.stream()
                .map(Movie::getMovieId)
                .collect(Collectors.toSet());
    }
}
