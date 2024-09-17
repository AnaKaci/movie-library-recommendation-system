package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.mappers.ActorMapper;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.repositories.ActorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ActorMapper actorMapper;

    @InjectMocks
    private ActorService actorService;

    public ActorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // Arrange
        String actorName = "Test Actor";
        Set<Long> movieIds = new HashSet<>();
        movieIds.add(1L);// Example movie ID
        Actor actor = new Actor(actorName);
        ActorDTO actorDTO = new ActorDTO(actorName, "Bio", new Date(), movieIds);
        List<Actor> actors = List.of(actor);

        when(actorRepository.findAll()).thenReturn(actors);
        when(actorMapper.toDTO(actor)).thenReturn(actorDTO);

        // Act
        List<ActorDTO> result = actorService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(actorDTO, result.getFirst());

        verify(actorRepository, times(1)).findAll();
        verify(actorMapper, times(1)).toDTO(actor);
    }

    @Test
    public void testFindByName_Exists() {
        // Arrange
        String actorName = "Test Actor";
        Set<Long> movieIds = new HashSet<>();
        movieIds.add(1L);
        Actor actor = new Actor(actorName);
        ActorDTO actorDTO = new ActorDTO(actorName, "Bio", new Date(), movieIds);

        when(actorRepository.findByActorName(actorName)).thenReturn(Optional.of(actor));
        when(actorMapper.toDTO(actor)).thenReturn(actorDTO);

        // Act
        Optional<ActorDTO> result = actorService.findByName(actorName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(actorDTO, result.get());

        verify(actorRepository, times(1)).findByActorName(actorName);
        verify(actorMapper, times(1)).toDTO(actor);
    }

    @Test
    public void testFindByName_NotFound() {
        // Arrange
        String actorName = "Nonexistent Actor";

        when(actorRepository.findByActorName(actorName)).thenReturn(Optional.empty());

        // Act
        Optional<ActorDTO> result = actorService.findByName(actorName);

        // Assert
        assertFalse(result.isPresent());

        verify(actorRepository, times(1)).findByActorName(actorName);
        verify(actorMapper, never()).toDTO(any(Actor.class));
    }
}
