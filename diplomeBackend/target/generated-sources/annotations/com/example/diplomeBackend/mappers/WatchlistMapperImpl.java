package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.WatchlistDTO;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.models.Watchlist;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class WatchlistMapperImpl implements WatchlistMapper {

    @Override
    public WatchlistDTO toDTO(Watchlist watchlist) {
        if ( watchlist == null ) {
            return null;
        }

        WatchlistDTO watchlistDTO = new WatchlistDTO();

        watchlistDTO.setUserId( watchlistUserUserId( watchlist ) );
        watchlistDTO.setMovieId( watchlistMovieMovieId( watchlist ) );
        watchlistDTO.setDateAdded( watchlist.getDateAdded() );

        return watchlistDTO;
    }

    @Override
    public Watchlist toEntity(WatchlistDTO watchlistDTO) {
        if ( watchlistDTO == null ) {
            return null;
        }

        Watchlist watchlist = new Watchlist();

        watchlist.setUser( watchlistDTOToUser( watchlistDTO ) );
        watchlist.setMovie( watchlistDTOToMovie( watchlistDTO ) );
        watchlist.setDateAdded( watchlistDTO.getDateAdded() );

        return watchlist;
    }

    private Long watchlistUserUserId(Watchlist watchlist) {
        if ( watchlist == null ) {
            return null;
        }
        User user = watchlist.getUser();
        if ( user == null ) {
            return null;
        }
        Long userId = user.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private Long watchlistMovieMovieId(Watchlist watchlist) {
        if ( watchlist == null ) {
            return null;
        }
        Movie movie = watchlist.getMovie();
        if ( movie == null ) {
            return null;
        }
        Long movieId = movie.getMovieId();
        if ( movieId == null ) {
            return null;
        }
        return movieId;
    }

    protected User watchlistDTOToUser(WatchlistDTO watchlistDTO) {
        if ( watchlistDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( watchlistDTO.getUserId() );

        return user;
    }

    protected Movie watchlistDTOToMovie(WatchlistDTO watchlistDTO) {
        if ( watchlistDTO == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setMovieId( watchlistDTO.getMovieId() );

        return movie;
    }
}
