package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.FeedbackDTO;
import com.example.diplomeBackend.models.Feedback;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FeedbackMapperTest {

    private FeedbackMapper feedbackMapper;

    @BeforeEach
    void setUp() {
        feedbackMapper = Mappers.getMapper(FeedbackMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        User user = new User();
        user.setUserId(1L);
        user.setUsername("john_doe");

        Movie movie = new Movie();
        movie.setMovieId(10L);

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setMovie(movie);
        feedback.setFeedbackText("Great movie!");
        feedback.setFeedbackRating(5);

        // Act
        FeedbackDTO feedbackDTO = feedbackMapper.toDTO(feedback);

        // Assert
        assertNotNull(feedbackDTO);
        assertEquals(1L, feedbackDTO.getUserId());
        assertEquals("john_doe", feedbackDTO.getUsername());
        assertEquals(10L, feedbackDTO.getMovieId());
        assertEquals("Great movie!", feedbackDTO.getFeedbackText());
        assertEquals(5, feedbackDTO.getFeedbackRating());
    }

    @Test
    void testToEntity() {
        // Arrange
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setUserId(1L);
        feedbackDTO.setUsername("john_doe");
        feedbackDTO.setMovieId(10L);
        feedbackDTO.setFeedbackText("Great movie!");
        feedbackDTO.setFeedbackRating(5);

        // Act
        Feedback feedback = feedbackMapper.toEntity(feedbackDTO);

        // Assert
        assertNotNull(feedback);
        assertNotNull(feedback.getUser());
        assertNotNull(feedback.getMovie());
        assertEquals(1L, feedback.getUser().getUserId());
        assertEquals("john_doe", feedback.getUser().getUsername());
        assertEquals(10L, feedback.getMovie().getMovieId());
        assertEquals("Great movie!", feedback.getFeedbackText());
        assertEquals(5, feedback.getFeedbackRating());
    }
}
