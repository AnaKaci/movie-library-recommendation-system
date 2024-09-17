package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.models.Actor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ActorMapperImpl implements ActorMapper {

    @Override
    public ActorDTO toDTO(Actor actor) {
        if ( actor == null ) {
            return null;
        }

        ActorDTO actorDTO = new ActorDTO();

        actorDTO.setName( actor.getActorName() );
        actorDTO.setMovieIds( moviesToMovieIds( actor.getMovies() ) );
        actorDTO.setBio( actor.getBio() );
        actorDTO.setDateOfBirth( actor.getDateOfBirth() );

        return actorDTO;
    }

    @Override
    public Actor toEntity(ActorDTO actorDTO) {
        if ( actorDTO == null ) {
            return null;
        }

        Actor actor = new Actor();

        actor.setActorName( actorDTO.getName() );
        actor.setBio( actorDTO.getBio() );
        actor.setDateOfBirth( actorDTO.getDateOfBirth() );

        return actor;
    }
}
