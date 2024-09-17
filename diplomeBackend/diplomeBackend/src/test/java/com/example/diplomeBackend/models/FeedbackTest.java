package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FeedbackTest {

    @Test
    public void testFeedbackDefaultConstructor() {
        Feedback feedback = new Feedback();
        assertNotNull(feedback);
        assertNull(feedback.getFeedbackId());
        assertNull(feedback.getUser());
        assertNull(feedback.getMovie());
        assertNull(feedback.getFeedbackText());
        assertEquals(0, feedback.getFeedbackRating());
        assertNull(feedback.getFeedbackDate());
    }

    @Test
    public void testFeedbackParameterizedConstructor() {
        User user = new User(); // Assuming User class is available
        Movie movie = new Movie(); // Assuming Movie class is available
        String feedbackText = "Great movie!";
        int feedbackRating = 5;
        Date feedbackDate = new Date();

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setMovie(movie);
        feedback.setFeedbackText(feedbackText);
        feedback.setFeedbackRating(feedbackRating);
        feedback.setFeedbackDate(feedbackDate);

        assertNotNull(feedback);
        assertEquals(user, feedback.getUser());
        assertEquals(movie, feedback.getMovie());
        assertEquals(feedbackText, feedback.getFeedbackText());
        assertEquals(feedbackRating, feedback.getFeedbackRating());
        assertEquals(feedbackDate, feedback.getFeedbackDate());
    }

    @Test
    public void testFeedbackSettersAndGetters() {
        Feedback feedback = new Feedback();
        User user = new User(); // Assuming User class is available
        Movie movie = new Movie(); // Assuming Movie class is available
        String feedbackText = "Average movie.";
        int feedbackRating = 3;
        Date feedbackDate = new Date();

        feedback.setUser(user);
        feedback.setMovie(movie);
        feedback.setFeedbackText(feedbackText);
        feedback.setFeedbackRating(feedbackRating);
        feedback.setFeedbackDate(feedbackDate);

        assertEquals(user, feedback.getUser());
        assertEquals(movie, feedback.getMovie());
        assertEquals(feedbackText, feedback.getFeedbackText());
        assertEquals(feedbackRating, feedback.getFeedbackRating());
        assertEquals(feedbackDate, feedback.getFeedbackDate());
    }
}
