package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedbackDTOTest {

    @Test
    void testFeedbackDTOConstructorAndGettersSetters() {
        // Arrange
        Long userId = 1L;
        Long movieId = 100L;
        String username = "john_doe";
        String feedbackText = "Great movie!";
        int feedbackRating = 5;

        // Act
        FeedbackDTO feedbackDTO = new FeedbackDTO(userId, movieId, username, feedbackText, feedbackRating);

        // Assert
        assertEquals(userId, feedbackDTO.getUserId());
        assertEquals(movieId, feedbackDTO.getMovieId());
        assertEquals(username, feedbackDTO.getUsername());
        assertEquals(feedbackText, feedbackDTO.getFeedbackText());
        assertEquals(feedbackRating, feedbackDTO.getFeedbackRating());
    }

    @Test
    void testFeedbackDTOSetters() {
        // Arrange
        FeedbackDTO feedbackDTO = new FeedbackDTO();

        Long userId = 2L;
        Long movieId = 200L;
        String username = "jane_doe";
        String feedbackText = "Not bad.";
        int feedbackRating = 3;

        // Act
        feedbackDTO.setUserId(userId);
        feedbackDTO.setMovieId(movieId);
        feedbackDTO.setUsername(username);
        feedbackDTO.setFeedbackText(feedbackText);
        feedbackDTO.setFeedbackRating(feedbackRating);

        // Assert
        assertEquals(userId, feedbackDTO.getUserId());
        assertEquals(movieId, feedbackDTO.getMovieId());
        assertEquals(username, feedbackDTO.getUsername());
        assertEquals(feedbackText, feedbackDTO.getFeedbackText());
        assertEquals(feedbackRating, feedbackDTO.getFeedbackRating());
    }
}
