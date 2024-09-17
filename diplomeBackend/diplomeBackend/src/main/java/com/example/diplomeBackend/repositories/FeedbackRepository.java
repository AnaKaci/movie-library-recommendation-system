package com.example.diplomeBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.diplomeBackend.models.Feedback;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f from Feedback f WHERE f.movie.movieId = :movieId")
    List<Feedback> getFeedbacksByMovie(@Param("movieId") Long movieId);

    @Query("SELECT f from Feedback f WHERE f.user.userId = :userId AND f.movie.movieId = :movieId")
    Feedback getFeedbacksByMovieAndUser(@Param("userId") Long userId, @Param("movieId") Long movieId);


}
