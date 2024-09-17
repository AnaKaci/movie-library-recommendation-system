package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Director;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DirectorRepositoryTest {

    @Autowired
    private DirectorRepository directorRepository;

    private Director director;

    @BeforeEach
    void setUp() {
        // Create and save a Director entity in the in-memory database
        director = new Director();
        director.setDirectorName("Christopher Nolan");
        directorRepository.save(director);
    }

    @Test
    void testFindByDirectorName_Found() {
        // Test when the director exists in the database
        Optional<Director> foundDirector = directorRepository.findByDirectorName("Christopher Nolan");

        assertTrue(foundDirector.isPresent());
        assertEquals(director.getDirectorName(), foundDirector.get().getDirectorName());
    }

    @Test
    void testFindByDirectorName_NotFound() {
        // Test when the director doesn't exist in the database
        Optional<Director> foundDirector = directorRepository.findByDirectorName("Quentin Tarantino");

        assertFalse(foundDirector.isPresent());
    }
}
