package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.GenreDTO;
import com.example.diplomeBackend.models.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDTO toDTO(Genre genre);
    Genre toEntity(GenreDTO genreDTO);
}
