package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Feedback;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    private Movie movie;
    private User user;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        // Create a user
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        userRepository.save(user);

        // Create a movie
        movie = new Movie();
        movie.setTitle("Inception");
        movie.setDescription("A mind-bending thriller");
        movieRepository.save(movie);

        // Create a feedback
        feedback = new Feedback();
        feedback.setMovie(movie);
        feedback.setUser(user);
        feedback.setFeedbackText("Amazing movie!");
        feedback.setFeedbackRating(5);
        feedbackRepository.save(feedback);
    }

    @Test
    void testGetFeedbacksByMovie_Found() {
        // Test retrieving feedback for a movie
        List<Feedback> feedbacks = feedbackRepository.getFeedbacksByMovie(movie.getMovieId());

        assertFalse(feedbacks.isEmpty());
        assertEquals(1, feedbacks.size());
        assertEquals(feedback.getFeedbackText(), feedbacks.get(0).getFeedbackText());
    }

    @Test
    void testGetFeedbacksByMovie_NotFound() {
        // Test retrieving feedback for a non-existent movie
        List<Feedback> feedbacks = feedbackRepository.getFeedbacksByMovie(999L);

        assertTrue(feedbacks.isEmpty());
    }

    @Test
    void testGetFeedbacksByMovieAndUser_Found() {
        // Test retrieving feedback for a specific movie and user
        Feedback foundFeedback = feedbackRepository.getFeedbacksByMovieAndUser(user.getUserId(), movie.getMovieId());

        assertNotNull(foundFeedback);
        assertEquals(feedback.getFeedbackText(), foundFeedback.getFeedbackText());
    }

    @Test
    void testGetFeedbacksByMovieAndUser_NotFound() {
        // Test retrieving feedback for a non-existent combination of movie and user
        Feedback foundFeedback = feedbackRepository.getFeedbacksByMovieAndUser(999L, 999L);

        assertNull(foundFeedback);
    }
}
