package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.FeedbackDTO;
import com.example.diplomeBackend.mappers.FeedbackMapper;
import com.example.diplomeBackend.models.Feedback;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.services.FeedbackService;
import com.example.diplomeBackend.services.MovieService;
import com.example.diplomeBackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class FeedbackControllerTest {

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private UserService userService;

    @Mock
    private MovieService movieService;

    @Mock
    private FeedbackMapper feedbackMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFeedback_Success() {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setUserId(1L);
        feedbackDTO.setMovieId(1L);
        feedbackDTO.setFeedbackText("Great movie!");
        feedbackDTO.setFeedbackRating(5);

        User user = new User();
        Movie movie = new Movie();

        when(userService.findById(anyLong())).thenReturn(Optional.of(user));
        when(movieService.findById(anyLong())).thenReturn(Optional.of(movie));
        when(feedbackService.existsByUserAndMovie(user, movie)).thenReturn(false);
        when(feedbackMapper.toDTO(any(Feedback.class))).thenReturn(feedbackDTO);

        ResponseEntity<String> response = feedbackController.addFeedback(feedbackDTO);

        verify(feedbackService).save(any(Feedback.class));
        verify(feedbackService).newAverageRating(any(Feedback.class), any(Movie.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Feedback added successfully", response.getBody());
    }

    @Test
    public void testGetFeedbacksByMovieId() {
        List<FeedbackDTO> feedbacks = new ArrayList<>();
        feedbacks.add(new FeedbackDTO());

        when(feedbackService.getFeedbackByMovieId(anyLong())).thenReturn(feedbacks);

        ResponseEntity<List<FeedbackDTO>> response = feedbackController.getFeedbacksByMovieId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(feedbacks, response.getBody());
    }

    @Test
    public void testGetFeedbacksByMovieAndUser_NotFound() {
        when(feedbackService.findByUserAndMovie(anyLong(), anyLong())).thenReturn(null);

        ResponseEntity<FeedbackDTO> response = feedbackController.getFeedbacksByMovieAndUser(1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateFeedback_Success() {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(1L);

        when(feedbackService.findByUserAndMovie(anyLong(), anyLong())).thenReturn(feedback);

        ResponseEntity<String> response = feedbackController.updateFeedback(1L, 1L, feedbackDTO);

        verify(feedbackService).updateFeedback(anyLong(), any(FeedbackDTO.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Feedback updated successfully", response.getBody());
    }

    @Test
    public void testDeleteFeedback_NotFound() {
        when(feedbackService.findByUserAndMovie(anyLong(), anyLong())).thenReturn(null);

        ResponseEntity<String> response = feedbackController.deleteFeedback(1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Feedback not found", response.getBody());
    }

}
