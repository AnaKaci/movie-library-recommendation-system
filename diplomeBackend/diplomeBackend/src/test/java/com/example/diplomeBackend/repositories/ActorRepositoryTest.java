package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor();
        actor.setActorName("Robert Downey Jr.");
        actorRepository.save(actor); // Save the actor in the in-memory database
    }

    @Test
    void testFindByActorName_Found() {
        Optional<Actor> foundActor = actorRepository.findByActorName("Robert Downey Jr.");

        assertTrue(foundActor.isPresent());
        assertEquals(actor.getActorName(), foundActor.get().getActorName());
    }

    @Test
    void testFindByActorName_NotFound() {
        Optional<Actor> foundActor = actorRepository.findByActorName("Chris Evans");

        assertFalse(foundActor.isPresent());
    }
}
