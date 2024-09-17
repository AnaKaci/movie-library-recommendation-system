package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MovieGenreDTO;
import com.example.diplomeBackend.models.Genre;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieGenre;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:45+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class MovieGenreMapperImpl implements MovieGenreMapper {

    @Override
    public MovieGenreDTO toDTO(MovieGenre movieGenre) {
        if ( movieGenre == null ) {
            return null;
        }

        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();

        movieGenreDTO.setMovieId( movieGenreMovieMovieId( movieGenre ) );
        movieGenreDTO.setGenreName( movieGenreGenreGenreName( movieGenre ) );

        return movieGenreDTO;
    }

    @Override
    public MovieGenre toEntity(MovieGenreDTO movieGenreDTO) {
        if ( movieGenreDTO == null ) {
            return null;
        }

        MovieGenre movieGenre = new MovieGenre();

        movieGenre.setMovie( movieGenreDTOToMovie( movieGenreDTO ) );
        movieGenre.setGenre( movieGenreDTOToGenre( movieGenreDTO ) );

        return movieGenre;
    }

    private Long movieGenreMovieMovieId(MovieGenre movieGenre) {
        if ( movieGenre == null ) {
            return null;
        }
        Movie movie = movieGenre.getMovie();
        if ( movie == null ) {
            return null;
        }
        Long movieId = movie.getMovieId();
        if ( movieId == null ) {
            return null;
        }
        return movieId;
    }

    private String movieGenreGenreGenreName(MovieGenre movieGenre) {
        if ( movieGenre == null ) {
            return null;
        }
        Genre genre = movieGenre.getGenre();
        if ( genre == null ) {
            return null;
        }
        String genreName = genre.getGenreName();
        if ( genreName == null ) {
            return null;
        }
        return genreName;
    }

    protected Movie movieGenreDTOToMovie(MovieGenreDTO movieGenreDTO) {
        if ( movieGenreDTO == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setMovieId( movieGenreDTO.getMovieId() );

        return movie;
    }

    protected Genre movieGenreDTOToGenre(MovieGenreDTO movieGenreDTO) {
        if ( movieGenreDTO == null ) {
            return null;
        }

        Genre genre = new Genre();

        genre.setGenreName( movieGenreDTO.getGenreName() );

        return genre;
    }
}
