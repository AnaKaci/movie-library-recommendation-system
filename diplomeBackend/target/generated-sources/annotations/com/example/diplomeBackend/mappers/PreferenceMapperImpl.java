package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.PreferenceDTO;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Director;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.models.Preference;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class PreferenceMapperImpl implements PreferenceMapper {

    @Override
    public PreferenceDTO toDTO(Preference preference) {
        if ( preference == null ) {
            return null;
        }

        PreferenceDTO preferenceDTO = new PreferenceDTO();

        preferenceDTO.setGenreName( preferenceGenreGenreName( preference ) );
        preferenceDTO.setActorName( preferenceActorActorName( preference ) );
        preferenceDTO.setDirectorName( preferenceDirectorDirectorName( preference ) );
        preferenceDTO.setPreferenceType( preference.getPreferenceType() );
        preferenceDTO.setPreferenceId( preference.getPreferenceId() );

        return preferenceDTO;
    }

    @Override
    public Preference toEntity(PreferenceDTO preferenceDTO) {
        if ( preferenceDTO == null ) {
            return null;
        }

        Preference preference = new Preference();

        preference.setGenre( preferenceDTOToGenre( preferenceDTO ) );
        preference.setActor( preferenceDTOToActor( preferenceDTO ) );
        preference.setDirector( preferenceDTOToDirector( preferenceDTO ) );
        preference.setPreferenceType( preferenceDTO.getPreferenceType() );
        preference.setPreferenceId( preferenceDTO.getPreferenceId() );

        return preference;
    }

    private String preferenceGenreGenreName(Preference preference) {
        if ( preference == null ) {
            return null;
        }
        Genre genre = preference.getGenre();
        if ( genre == null ) {
            return null;
        }
        String genreName = genre.getGenreName();
        if ( genreName == null ) {
            return null;
        }
        return genreName;
    }

    private String preferenceActorActorName(Preference preference) {
        if ( preference == null ) {
            return null;
        }
        Actor actor = preference.getActor();
        if ( actor == null ) {
            return null;
        }
        String actorName = actor.getActorName();
        if ( actorName == null ) {
            return null;
        }
        return actorName;
    }

    private String preferenceDirectorDirectorName(Preference preference) {
        if ( preference == null ) {
            return null;
        }
        Director director = preference.getDirector();
        if ( director == null ) {
            return null;
        }
        String directorName = director.getDirectorName();
        if ( directorName == null ) {
            return null;
        }
        return directorName;
    }

    protected Genre preferenceDTOToGenre(PreferenceDTO preferenceDTO) {
        if ( preferenceDTO == null ) {
            return null;
        }

        Genre genre = new Genre();

        genre.setGenreName( preferenceDTO.getGenreName() );

        return genre;
    }

    protected Actor preferenceDTOToActor(PreferenceDTO preferenceDTO) {
        if ( preferenceDTO == null ) {
            return null;
        }

        Actor actor = new Actor();

        actor.setActorName( preferenceDTO.getActorName() );

        return actor;
    }

    protected Director preferenceDTOToDirector(PreferenceDTO preferenceDTO) {
        if ( preferenceDTO == null ) {
            return null;
        }

        Director director = new Director();

        director.setDirectorName( preferenceDTO.getDirectorName() );

        return director;
    }
}
