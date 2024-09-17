package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.Watchlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    @Query("SELECT w.movie FROM Watchlist w WHERE w.user.userId = :userId")
    List<Movie> findMoviesByUserId(@Param("userId") Long userId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Watchlist w WHERE w.user.userId = :userId AND w.movie.movieId = :movieId")
    void deleteByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") Long movieId);

}
