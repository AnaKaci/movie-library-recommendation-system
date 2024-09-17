package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.FeedbackDTO;
import com.example.diplomeBackend.mappers.FeedbackMapper;
import com.example.diplomeBackend.models.Feedback;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.repositories.FeedbackRepository;
import com.example.diplomeBackend.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private FeedbackMapper feedbackMapper;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // Arrange
        Feedback feedback = new Feedback();
        List<Feedback> feedbacks = List.of(feedback);

        when(feedbackRepository.findAll()).thenReturn(feedbacks);

        // Act
        List<Feedback> result = feedbackService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(feedback, result.get(0));

        verify(feedbackRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_Found() {
        // Arrange
        Long id = 1L;
        Feedback feedback = new Feedback();

        when(feedbackRepository.findById(id)).thenReturn(Optional.of(feedback));

        // Act
        Optional<Feedback> result = feedbackService.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(feedback, result.get());

        verify(feedbackRepository, times(1)).findById(id);
    }

    @Test
    public void testFindById_NotFound() {
        // Arrange
        Long id = 1L;

        when(feedbackRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Feedback> result = feedbackService.findById(id);

        // Assert
        assertFalse(result.isPresent());

        verify(feedbackRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        // Arrange
        Feedback feedback = new Feedback();

        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        // Act
        Feedback result = feedbackService.save(feedback);

        // Assert
        assertNotNull(result);
        assertEquals(feedback, result);

        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Long id = 1L;

        // Act
        feedbackService.deleteById(id);

        // Assert
        verify(feedbackRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetFeedbackByMovieId() {
        // Arrange
        Long movieId = 1L;
        Long userId = 1L;
        String username = "johnDoe";
        Feedback feedback = new Feedback();
        FeedbackDTO feedbackDTO = new FeedbackDTO(userId, movieId, username, "text", 5);
        List<Feedback> feedbacks = List.of(feedback);
        List<FeedbackDTO> feedbackDTOs = List.of(feedbackDTO);

        when(feedbackRepository.getFeedbacksByMovie(movieId)).thenReturn(feedbacks);
        when(feedbackMapper.toDTO(feedback)).thenReturn(feedbackDTO);

        // Act
        List<FeedbackDTO> result = feedbackService.getFeedbackByMovieId(movieId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(feedbackDTO, result.get(0));

        verify(feedbackRepository, times(1)).getFeedbacksByMovie(movieId);
        verify(feedbackMapper, times(1)).toDTO(feedback);
    }

    @Test
    public void testNewAverageRating() {
        // Arrange
        Feedback feedback = new Feedback();
        feedback.setFeedbackRating(4);
        Movie movie = new Movie();
        movie.setAverageRating(5.0);

        feedbackService.newAverageRating(feedback, movie);

        verify(movieRepository, times(1)).updateAverageRating(4.5, movie.getMovieId()); // (5.0 + 4) / 2 = 4.5
    }

    @Test
    public void testUpdateFeedback() {
        // Arrange
        Long movieId = 1L;
        Long userId = 1L;
        String username = "johnDoe";
        FeedbackDTO feedbackDTO = new FeedbackDTO(userId, movieId, username, "text", 5);
        Feedback existingFeedback = new Feedback();
        existingFeedback.setFeedbackText("Original text");
        existingFeedback.setFeedbackRating(3);
        existingFeedback.setFeedbackDate(new Date());

        Movie movie = new Movie();
        movie.setMovieId(1L);
        existingFeedback.setMovie(movie);


        when(feedbackRepository.save(existingFeedback)).thenReturn(existingFeedback);

        // Act
        feedbackService.updateFeedback(userId, feedbackDTO);

        // Assert
        assertEquals("Updated text", existingFeedback.getFeedbackText());
        assertEquals(4, existingFeedback.getFeedbackRating());

        verify(feedbackRepository, times(1)).getFeedbacksByMovieAndUser(userId, movieId);
        verify(feedbackRepository, times(1)).save(existingFeedback);
    }

    @Test
    public void testExistsByUserAndMovie_Found() {
        // Arrange
        User user = new User();
        user.setUserId(1L);
        Movie movie = new Movie();
        movie.setMovieId(1L);

        when(feedbackRepository.getFeedbacksByMovieAndUser(movie.getMovieId(), user.getUserId())).thenReturn(new Feedback());

        // Act
        boolean result = feedbackService.existsByUserAndMovie(user, movie);

        // Assert
        assertTrue(result);

        verify(feedbackRepository, times(1)).getFeedbacksByMovieAndUser(movie.getMovieId(), user.getUserId());
    }

    @Test
    public void testExistsByUserAndMovie_NotFound() {
        // Arrange
        User user = new User();
        user.setUserId(1L);
        Movie movie = new Movie();
        movie.setMovieId(1L);

        when(feedbackRepository.getFeedbacksByMovieAndUser(movie.getMovieId(), user.getUserId())).thenReturn(null);

        // Act
        boolean result = feedbackService.existsByUserAndMovie(user, movie);

        // Assert
        assertFalse(result);

        verify(feedbackRepository, times(1)).getFeedbacksByMovieAndUser(movie.getMovieId(), user.getUserId());
    }

    @Test
    public void testFindByUserAndMovie() {
        // Arrange
        Long userId = 1L;
        Long movieId = 1L;
        Feedback feedback = new Feedback();

        when(feedbackRepository.getFeedbacksByMovieAndUser(userId, movieId)).thenReturn(feedback);

        // Act
        Feedback result = feedbackService.findByUserAndMovie(userId, movieId);

        // Assert
        assertNotNull(result);
        assertEquals(feedback, result);

        verify(feedbackRepository, times(1)).getFeedbacksByMovieAndUser(userId, movieId);
    }

    @Test
    public void testDeleteFeedback() {
        // Arrange
        Long feedbackId = 1L;
        Feedback feedback = new Feedback();
        Movie movie = new Movie();
        movie.setMovieId(1L);
        feedback.setMovie(movie);

        when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
        when(feedbackRepository.getFeedbacksByMovie(movie.getMovieId())).thenReturn(List.of(feedback));
        when(feedbackRepository.getFeedbacksByMovie(movie.getMovieId())).thenReturn(List.of(feedback));

        // Act
        feedbackService.deleteFeedback(feedbackId);

        // Assert
        verify(feedbackRepository, times(1)).findById(feedbackId);
        verify(feedbackRepository, times(1)).deleteById(feedbackId);
        verify(movieRepository, times(1)).updateAverageRating(0.0, movie.getMovieId()); // Since we have one feedback with rating 0
    }
}
