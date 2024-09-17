package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.FriendsDTO;
import com.example.diplomeBackend.models.Friends;
import com.example.diplomeBackend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FriendsMapper {

    @Mappings({
            @Mapping(target = "id", source = "friends.id"),
            @Mapping(target = "userId", source = "friends.user.userId"),
            @Mapping(target = "friendIds", expression = "java(mapFriendIds(friends.getFriends()))")
    })
    FriendsDTO toDTO(Friends friends);

    @Mappings({
            @Mapping(target = "id", source = "friendsDTO.id"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "friends", ignore = true)
    })
    Friends toEntity(FriendsDTO friendsDTO, User user);

    default List<Long> mapFriendIds(Set<User> friends) {
        return friends.stream()
                .map(User::getUserId)
                .collect(Collectors.toList());
    }
}
