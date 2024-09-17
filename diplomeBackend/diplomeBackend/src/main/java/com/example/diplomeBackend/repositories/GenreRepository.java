package com.example.diplomeBackend.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.diplomeBackend.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Genre g WHERE g.genreName = :genreName")
    void deleteByGenreName(String genreName);

    @Query("SELECT g FROM Genre g WHERE g.genreName = :genreName")
    Optional<Genre> findByGenreName(String genreName);

    @Query("SELECT g FROM Genre g")
    List<Genre> findGenres();
}
