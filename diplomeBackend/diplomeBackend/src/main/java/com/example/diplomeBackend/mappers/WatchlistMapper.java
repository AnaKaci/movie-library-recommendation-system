package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.WatchlistDTO;
import com.example.diplomeBackend.models.Watchlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WatchlistMapper {

    @Mappings({
            @Mapping(source = "user.userId", target = "userId"),
            @Mapping(source = "movie.movieId", target = "movieId")
    })
    WatchlistDTO toDTO(Watchlist watchlist);

    @Mappings({
            @Mapping(source = "userId", target = "user.userId"),
            @Mapping(source = "movieId", target = "movie.movieId")
    })
    Watchlist toEntity(WatchlistDTO watchlistDTO);

}
