package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.GenreDTO;
import com.example.diplomeBackend.models.Genre;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class GenreMapperImpl implements GenreMapper {

    @Override
    public GenreDTO toDTO(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setGenreName( genre.getGenreName() );

        return genreDTO;
    }

    @Override
    public Genre toEntity(GenreDTO genreDTO) {
        if ( genreDTO == null ) {
            return null;
        }

        Genre genre = new Genre();

        genre.setGenreName( genreDTO.getGenreName() );

        return genre;
    }
}
