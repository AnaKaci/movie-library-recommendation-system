package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.models.Director;
import com.example.diplomeBackend.models.Movie;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class MovieMapperImpl implements MovieMapper {

    @Override
    public MovieDTO toDTO(Movie movie) {
        if ( movie == null ) {
            return null;
        }

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setDirectorName( movieDirectorDirectorName( movie ) );
        movieDTO.setMovieId( movie.getMovieId() );
        movieDTO.setTitle( movie.getTitle() );
        movieDTO.setDescription( movie.getDescription() );
        movieDTO.setReleaseDate( movie.getReleaseDate() );
        movieDTO.setDuration( movie.getDuration() );
        movieDTO.setPoster( movie.getPoster() );
        movieDTO.setTrailer( movie.getTrailer() );
        movieDTO.setAverageRating( movie.getAverageRating() );

        movieDTO.setGenreNames( convertGenresToNames(movie.getGenres()) );
        movieDTO.setActorNames( convertActorsToNames(movie.getActors()) );

        return movieDTO;
    }

    @Override
    public Movie toEntity(MovieDTO movieDTO) {
        if ( movieDTO == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setDirector( movieDTOToDirector( movieDTO ) );
        movie.setMovieId( movieDTO.getMovieId() );
        movie.setTitle( movieDTO.getTitle() );
        movie.setDescription( movieDTO.getDescription() );
        movie.setReleaseDate( movieDTO.getReleaseDate() );
        movie.setDuration( movieDTO.getDuration() );
        movie.setPoster( movieDTO.getPoster() );
        movie.setTrailer( movieDTO.getTrailer() );
        movie.setAverageRating( movieDTO.getAverageRating() );

        movie.setGenres( convertNamesToGenres(movieDTO.getGenreNames()) );
        movie.setActors( convertNamesToActors(movieDTO.getActorNames()) );

        return movie;
    }

    private String movieDirectorDirectorName(Movie movie) {
        if ( movie == null ) {
            return null;
        }
        Director director = movie.getDirector();
        if ( director == null ) {
            return null;
        }
        String directorName = director.getDirectorName();
        if ( directorName == null ) {
            return null;
        }
        return directorName;
    }

    protected Director movieDTOToDirector(MovieDTO movieDTO) {
        if ( movieDTO == null ) {
            return null;
        }

        Director director = new Director();

        director.setDirectorName( movieDTO.getDirectorName() );

        return director;
    }
}
