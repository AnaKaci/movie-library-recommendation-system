package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.FriendsDTO;
import com.example.diplomeBackend.models.Friends;
import com.example.diplomeBackend.models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class FriendsMapperImpl implements FriendsMapper {

    @Override
    public FriendsDTO toDTO(Friends friends) {
        if ( friends == null ) {
            return null;
        }

        FriendsDTO friendsDTO = new FriendsDTO();

        friendsDTO.setId( friends.getId() );
        friendsDTO.setUserId( friendsUserUserId( friends ) );

        friendsDTO.setFriendIds( mapFriendIds(friends.getFriends()) );

        return friendsDTO;
    }

    @Override
    public Friends toEntity(FriendsDTO friendsDTO, User user) {
        if ( friendsDTO == null && user == null ) {
            return null;
        }

        Friends friends = new Friends();

        if ( friendsDTO != null ) {
            friends.setId( friendsDTO.getId() );
        }
        friends.setUser( user );

        return friends;
    }

    private Long friendsUserUserId(Friends friends) {
        if ( friends == null ) {
            return null;
        }
        User user = friends.getUser();
        if ( user == null ) {
            return null;
        }
        Long userId = user.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }
}
