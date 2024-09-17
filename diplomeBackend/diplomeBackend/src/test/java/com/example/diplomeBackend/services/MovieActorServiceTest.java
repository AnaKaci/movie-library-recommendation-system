package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.mappers.ActorMapper;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.models.Movie;
import com.example.diplomeBackend.models.MovieActor;
import com.example.diplomeBackend.repositories.MovieActorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MovieActorServiceTest {

    @InjectMocks
    private MovieActorService movieActorService;

    @Mock
    private MovieActorRepository movieActorRepository;

    @Mock
    private ActorMapper actorMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        MovieActor movieActor = new MovieActor();
        when(movieActorRepository.findAll()).thenReturn(List.of(movieActor));

        List<MovieActor> result = movieActorService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movieActorRepository, times(1)).findAll();
    }


    @Test
    void testSave() {
        MovieActor movieActor = new MovieActor();
        movieActor.setMovie(new Movie());

        when(movieActorRepository.save(any(MovieActor.class))).thenReturn(movieActor);

        MovieActor result = movieActorService.save(movieActor);

        assertNotNull(result);
        assertEquals(1L, result.getMovieActorId());
        verify(movieActorRepository, times(1)).save(movieActor);
    }

    @Test
    void testDeleteById() {
        Long actorId = 1L;

        doNothing().when(movieActorRepository).deleteById(actorId);

        movieActorService.deleteById(actorId);

        verify(movieActorRepository, times(1)).deleteById(actorId);
    }

    @Test
    void testFindActorsByMovieId() {
        Long movieId = 1L;
        Actor actor = new Actor();
        actor.setActorName("1L");
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setName("1L");

        when(movieActorRepository.findActorsByMovieId(movieId)).thenReturn(List.of(actor));
        when(actorMapper.toDTO(actor)).thenReturn(actorDTO);

        List<ActorDTO> result = movieActorService.findActorsByMovieId(movieId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movieActorRepository, times(1)).findActorsByMovieId(movieId);
        verify(actorMapper, times(1)).toDTO(actor);
    }
}
