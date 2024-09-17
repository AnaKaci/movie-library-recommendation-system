package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.DirectorDTO;
import com.example.diplomeBackend.models.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    @Mappings({
            @Mapping(source = "directorName", target = "name")
    })
    DirectorDTO toDTO(Director director);

    @Mappings({
            @Mapping(source = "name", target = "directorName")
    })
    Director toEntity(DirectorDTO directorDTO);
}
