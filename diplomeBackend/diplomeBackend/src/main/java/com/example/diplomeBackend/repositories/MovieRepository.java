package com.example.diplomeBackend.repositories;
import com.example.diplomeBackend.models.Movie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m LEFT JOIN m.genres g LEFT JOIN m.actors a WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(m.description) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "OR LOWER(a.actorName) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "OR LOWER(g.genreName) LIKE LOWER(CONCAT('%', :query, '%'))" +
             "OR LOWER(m.director.directorName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Movie> searchMovies(@Param("query") String query);

    @Query("SELECT m FROM Movie m WHERE m.movieId = :movieId")
    Movie findByMovieId (@Param("movieId") Long movieId);

    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.genres LEFT JOIN FETCH m.actors ORDER BY m.title ASC")
    List<Movie> getAllMovies();

    @Query("SELECT m FROM Movie m ORDER BY m.averageRating DESC")
    List<Movie> sortByRating();

    @Query("select m from Movie m where m.releaseDate >= CURRENT_DATE")
    List<Movie> sortByUpcoming();

    @Query("SELECT m FROM Movie m LEFT JOIN m.feedbacks f " +
            "GROUP BY m " +
            "ORDER BY (0.5 * AVG(f.feedbackRating) + 0.5 * COUNT(f)) DESC")
    List<Movie> sortByPopular();

    @Transactional
    @Modifying
    @Query("UPDATE Movie m SET m.averageRating = :averageRating WHERE m.movieId = :movieId")
    void updateAverageRating(@Param("averageRating") double averageRating, @Param("movieId") Long movieId);


}
