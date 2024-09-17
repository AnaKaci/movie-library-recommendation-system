package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        // Add test data
        Genre genre1 = new Genre();
        genre1.setGenreName("Action");
        genreRepository.save(genre1);

        Genre genre2 = new Genre();
        genre2.setGenreName("Comedy");
        genreRepository.save(genre2);
    }

    @Test
    void testDeleteByGenreName() {
        // Delete genre by name
        genreRepository.deleteByGenreName("Comedy");

        // Verify the genre is deleted
        Optional<Genre> deletedGenre = genreRepository.findByGenreName("Comedy");
        assertFalse(deletedGenre.isPresent());

        // Verify other genres are still present
        Optional<Genre> existingGenre = genreRepository.findByGenreName("Action");
        assertTrue(existingGenre.isPresent());
    }

    @Test
    void testFindByGenreName_Found() {
        // Find genre by name
        Optional<Genre> foundGenre = genreRepository.findByGenreName("Action");

        assertTrue(foundGenre.isPresent());
        assertEquals("Action", foundGenre.get().getGenreName());
    }

    @Test
    void testFindByGenreName_NotFound() {
        // Find genre by name that does not exist
        Optional<Genre> foundGenre = genreRepository.findByGenreName("Nonexistent");

        assertFalse(foundGenre.isPresent());
    }

    @Test
    void testFindGenres() {
        // Find all genres
        List<Genre> genres = genreRepository.findGenres();

        assertNotNull(genres);
        assertEquals(2, genres.size()); // We added 2 genres in setUp
    }
}
