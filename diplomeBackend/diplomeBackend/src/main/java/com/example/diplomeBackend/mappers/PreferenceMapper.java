package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.PreferenceDTO;
import com.example.diplomeBackend.models.Preference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PreferenceMapper {

    @Mapping(source = "genre.genreName", target = "genreName")
    @Mapping(source = "actor.actorName", target = "actorName")
    @Mapping(source = "director.directorName", target = "directorName")
    @Mapping(source = "preferenceType", target = "preferenceType")
    PreferenceDTO toDTO(Preference preference);

    @Mapping(source = "genreName", target = "genre.genreName")
    @Mapping(source = "actorName", target = "actor.actorName")
    @Mapping(source = "directorName", target = "director.directorName")
    @Mapping(source = "preferenceType", target = "preferenceType")
    Preference toEntity(PreferenceDTO preferenceDTO);
}

