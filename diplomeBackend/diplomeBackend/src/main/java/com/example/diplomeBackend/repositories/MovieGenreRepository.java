package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieGenre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {

    @Query("SELECT mg.movie FROM MovieGenre mg WHERE LOWER(mg.genre) = LOWER(:genreName)")
    List<Movie> findByGenreName(@Param("genreName") String genreName);

    @Transactional
    @Modifying
    @Query("DELETE FROM MovieGenre mg WHERE LOWER(mg.genre) = LOWER(:genreName)")
    void deleteByGenreName(@Param("genreName") String genreName);
}
