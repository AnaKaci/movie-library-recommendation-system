package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MovieActorDTO;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieActor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class MovieActorMapperImpl implements MovieActorMapper {

    @Override
    public MovieActorDTO toDTO(MovieActor movieActor) {
        if ( movieActor == null ) {
            return null;
        }

        MovieActorDTO movieActorDTO = new MovieActorDTO();

        movieActorDTO.setMovieId( movieActorMovieMovieId( movieActor ) );
        movieActorDTO.setActorName( movieActorActorActorName( movieActor ) );

        return movieActorDTO;
    }

    @Override
    public MovieActor toEntity(MovieActorDTO movieActorDTO) {
        if ( movieActorDTO == null ) {
            return null;
        }

        MovieActor movieActor = new MovieActor();

        movieActor.setMovie( movieActorDTOToMovie( movieActorDTO ) );
        movieActor.setActor( movieActorDTOToActor( movieActorDTO ) );

        return movieActor;
    }

    private Long movieActorMovieMovieId(MovieActor movieActor) {
        if ( movieActor == null ) {
            return null;
        }
        Movie movie = movieActor.getMovie();
        if ( movie == null ) {
            return null;
        }
        Long movieId = movie.getMovieId();
        if ( movieId == null ) {
            return null;
        }
        return movieId;
    }

    private String movieActorActorActorName(MovieActor movieActor) {
        if ( movieActor == null ) {
            return null;
        }
        Actor actor = movieActor.getActor();
        if ( actor == null ) {
            return null;
        }
        String actorName = actor.getActorName();
        if ( actorName == null ) {
            return null;
        }
        return actorName;
    }

    protected Movie movieActorDTOToMovie(MovieActorDTO movieActorDTO) {
        if ( movieActorDTO == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setMovieId( movieActorDTO.getMovieId() );

        return movie;
    }

    protected Actor movieActorDTOToActor(MovieActorDTO movieActorDTO) {
        if ( movieActorDTO == null ) {
            return null;
        }

        Actor actor = new Actor();

        actor.setActorName( movieActorDTO.getActorName() );

        return actor;
    }
}
