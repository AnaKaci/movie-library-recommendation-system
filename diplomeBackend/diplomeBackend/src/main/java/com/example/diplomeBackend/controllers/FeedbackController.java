package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.FeedbackDTO;
import com.example.diplomeBackend.mappers.FeedbackMapper;
import com.example.diplomeBackend.models.*;
import com.example.diplomeBackend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rate")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private FeedbackMapper feedbackMapper;

    public FeedbackController(FeedbackService feedbackService, UserService userService, MovieService movieService){
        this.feedbackService = feedbackService;
        this.movieService = movieService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        User user = userService.findById(feedbackDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = movieService.findById(feedbackDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Check if feedback from this user for this movie already exists
        boolean feedbackExists = feedbackService.existsByUserAndMovie(user, movie);

        if (feedbackExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Feedback from this user for this movie already exists");
        }

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setMovie(movie);
        feedback.setFeedbackText(feedbackDTO.getFeedbackText());
        feedback.setFeedbackRating(feedbackDTO.getFeedbackRating());
        feedback.setFeedbackDate(new Date());
        feedbackService.save(feedback);
        feedbackService.newAverageRating(feedback, movie);

        return ResponseEntity.ok("Feedback added successfully");
    }

    @GetMapping("/feedback/{movieId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByMovieId(@PathVariable Long movieId) {
        List<FeedbackDTO> feedbacks = feedbackService.getFeedbackByMovieId(movieId);

        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/feedback/{userId}/{movieId}")
    public ResponseEntity<FeedbackDTO> getFeedbacksByMovieAndUser(@PathVariable Long movieId, @PathVariable Long userId) {
        Feedback feedback = feedbackService.findByUserAndMovie(userId, movieId);

        if (feedback == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if feedback not found
        }

        FeedbackDTO dto = feedbackMapper.toDTO(feedback);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PutMapping("/update/{userId}/{movieId}")
    public ResponseEntity<String> updateFeedback(
            @PathVariable Long userId,
            @PathVariable Long movieId,
            @RequestBody FeedbackDTO feedbackDTO) {

        try {
            Feedback feedback = feedbackService.findByUserAndMovie(userId, movieId);
            feedbackService.updateFeedback(feedback.getFeedbackId(), feedbackDTO);
            return ResponseEntity.ok("Feedback updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{userId}/{movieId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) {
        System.out.println("Deleting feedback for userId: " + userId + " and movieId: " + movieId);
        try {
            Feedback feedback = feedbackService.findByUserAndMovie(userId, movieId);
            System.out.println("Found feedback: " + feedback);
            if (feedback == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feedback not found");
            }
            feedbackService.deleteFeedback(feedback.getFeedbackId());
            return ResponseEntity.ok("Feedback deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }




}
