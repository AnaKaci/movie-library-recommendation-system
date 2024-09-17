package com.example.diplomeBackend.services;
import com.example.diplomeBackend.dto.FeedbackDTO;
import com.example.diplomeBackend.mappers.FeedbackMapper;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diplomeBackend.models.Feedback;
import com.example.diplomeBackend.repositories.FeedbackRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    public List<FeedbackDTO> getFeedbackByMovieId(Long movieId){
        List<Feedback> feedbacks = feedbackRepository.getFeedbacksByMovie(movieId);
        return feedbacks.stream()
                .map(feedbackMapper::toDTO)
                .collect(Collectors.toList());
    }
    public void newAverageRating(Feedback feedback, Movie movie){
        double movieAverageRating = movie.getAverageRating();
        movieAverageRating = (movieAverageRating + (double) feedback.getFeedbackRating())/2;
        movieRepository.updateAverageRating(movieAverageRating, movie.getMovieId());
    }

    public void updateFeedback(Long feedbackId, FeedbackDTO feedbackDTO) {
        Feedback existingFeedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        existingFeedback.setFeedbackText(feedbackDTO.getFeedbackText());
        existingFeedback.setFeedbackRating(feedbackDTO.getFeedbackRating());
        existingFeedback.setFeedbackDate(new Date());

        Feedback updatedFeedback = feedbackRepository.save(existingFeedback);

        Movie movie = existingFeedback.getMovie();
        newAverageRating(updatedFeedback, movie);

    }

    public boolean existsByUserAndMovie(User user, Movie movie) {
        return feedbackRepository.getFeedbacksByMovieAndUser(movie.getMovieId(), user.getUserId()) != null;
    }

    public Feedback findByUserAndMovie(Long userId, Long movieId) {
        return feedbackRepository.getFeedbacksByMovieAndUser(userId, movieId);
    }

    public void deleteFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        Movie movie = feedback.getMovie();
        feedbackRepository.deleteById(feedbackId);

        List<Feedback> remainingFeedbacks = feedbackRepository.getFeedbacksByMovie(movie.getMovieId());
        double newAverageRating = remainingFeedbacks.stream()
                .mapToDouble(Feedback::getFeedbackRating)
                .average()
                .orElse(0.0);

        movieRepository.updateAverageRating(newAverageRating, movie.getMovieId());
    }






}
