package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.UserDTO;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.models.Watchlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "user.userId"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "firstName", source = "user.firstName"),
            @Mapping(target = "lastName", source = "user.lastName"),
            @Mapping(target = "dateOfBirth", source = "user.dateOfBirth"),
            @Mapping(target = "watchlistIds", expression = "java(mapWatchlistIds(user.getWatchlists()))")
    })
    UserDTO toDTO(User user);

    @Mappings({
            @Mapping(target = "userId", source = "userDTO.id"),
            @Mapping(target = "username", source = "userDTO.username"),
            @Mapping(target = "email", source = "userDTO.email"),
            @Mapping(target = "firstName", source = "userDTO.firstName"),
            @Mapping(target = "lastName", source = "userDTO.lastName"),
            @Mapping(target = "dateOfBirth", source = "userDTO.dateOfBirth"),
            @Mapping(target = "watchlists", ignore = true)
    })
    User toEntity(UserDTO userDTO);

    default List<Long> mapWatchlistIds(List<Watchlist> watchlists) {
        return watchlists.stream()
                .map(Watchlist::getWatchlistId)
                .collect(Collectors.toList());
    }
}
