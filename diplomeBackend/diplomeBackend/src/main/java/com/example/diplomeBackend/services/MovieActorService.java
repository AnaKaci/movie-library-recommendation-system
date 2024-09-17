package com.example.diplomeBackend.services;
import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.mappers.ActorMapper;
import com.example.diplomeBackend.models.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diplomeBackend.models.MovieActor;
import com.example.diplomeBackend.repositories.MovieActorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieActorService {

    @Autowired
    private MovieActorRepository movieActorRepository;

    @Autowired
    private ActorMapper actorMapper;

    public List<MovieActor> findAll() {
        return movieActorRepository.findAll();
    }

    public Optional<MovieActor> findById(Long id) {
        return movieActorRepository.findById(id);
    }

    public MovieActor save(MovieActor movieActor) {
        return movieActorRepository.save(movieActor);
    }

    public void deleteById(Long id) {
        movieActorRepository.deleteById(id);
    }

    public List<ActorDTO> findActorsByMovieId(Long movieId) {
        List<Actor> actors = movieActorRepository.findActorsByMovieId(movieId);
        return actors.stream()
                .map(actorMapper::toDTO)
                .collect(Collectors.toList());
    }


}

