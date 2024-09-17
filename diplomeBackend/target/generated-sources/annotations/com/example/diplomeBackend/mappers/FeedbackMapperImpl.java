package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.FeedbackDTO;
import com.example.diplomeBackend.models.Feedback;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class FeedbackMapperImpl implements FeedbackMapper {

    @Override
    public FeedbackDTO toDTO(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }

        FeedbackDTO feedbackDTO = new FeedbackDTO();

        feedbackDTO.setUserId( feedbackUserUserId( feedback ) );
        feedbackDTO.setUsername( feedbackUserUsername( feedback ) );
        feedbackDTO.setMovieId( feedbackMovieMovieId( feedback ) );
        feedbackDTO.setFeedbackText( feedback.getFeedbackText() );
        feedbackDTO.setFeedbackRating( feedback.getFeedbackRating() );

        return feedbackDTO;
    }

    @Override
    public Feedback toEntity(FeedbackDTO feedbackDTO) {
        if ( feedbackDTO == null ) {
            return null;
        }

        Feedback feedback = new Feedback();

        feedback.setUser( feedbackDTOToUser( feedbackDTO ) );
        feedback.setMovie( feedbackDTOToMovie( feedbackDTO ) );
        feedback.setFeedbackText( feedbackDTO.getFeedbackText() );
        feedback.setFeedbackRating( feedbackDTO.getFeedbackRating() );

        return feedback;
    }

    private Long feedbackUserUserId(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }
        User user = feedback.getUser();
        if ( user == null ) {
            return null;
        }
        Long userId = user.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private String feedbackUserUsername(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }
        User user = feedback.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private Long feedbackMovieMovieId(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }
        Movie movie = feedback.getMovie();
        if ( movie == null ) {
            return null;
        }
        Long movieId = movie.getMovieId();
        if ( movieId == null ) {
            return null;
        }
        return movieId;
    }

    protected User feedbackDTOToUser(FeedbackDTO feedbackDTO) {
        if ( feedbackDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( feedbackDTO.getUserId() );
        user.setUsername( feedbackDTO.getUsername() );

        return user;
    }

    protected Movie feedbackDTOToMovie(FeedbackDTO feedbackDTO) {
        if ( feedbackDTO == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setMovieId( feedbackDTO.getMovieId() );

        return movie;
    }
}
