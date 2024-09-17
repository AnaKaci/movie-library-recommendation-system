package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.DirectorDTO;
import com.example.diplomeBackend.models.Director;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class DirectorMapperImpl implements DirectorMapper {

    @Override
    public DirectorDTO toDTO(Director director) {
        if ( director == null ) {
            return null;
        }

        DirectorDTO directorDTO = new DirectorDTO();

        directorDTO.setName( director.getDirectorName() );
        directorDTO.setBio( director.getBio() );
        directorDTO.setDateOfBirth( director.getDateOfBirth() );

        return directorDTO;
    }

    @Override
    public Director toEntity(DirectorDTO directorDTO) {
        if ( directorDTO == null ) {
            return null;
        }

        Director director = new Director();

        director.setDirectorName( directorDTO.getName() );
        director.setBio( directorDTO.getBio() );
        director.setDateOfBirth( directorDTO.getDateOfBirth() );

        return director;
    }
}
