package com.example.diplomeBackend.repositories;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.diplomeBackend.models.MovieActor;

import java.util.List;

@Repository
public interface MovieActorRepository extends JpaRepository<MovieActor, Long> {
    @Query("SELECT ma.actor FROM MovieActor ma WHERE ma.movie.movieId = :movieId")
    List<Actor> findActorsByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT ma.movie FROM MovieActor ma WHERE ma.actor.actorName = :actorName")
    List<Movie> findByActorName(@Param("actorName")String actorName);
}






