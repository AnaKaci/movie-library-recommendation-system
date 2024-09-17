package com.example.diplomeBackend.services;
import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.mappers.ActorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diplomeBackend.models.Actor;
import com.example.diplomeBackend.repositories.ActorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ActorMapper actorMapper;

    public List<ActorDTO> findAll() {

        List<Actor> actors = actorRepository.findAll();

        return actors.stream()
                .map(actorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ActorDTO> findByName(String actorName) {
        return actorRepository.findByActorName(actorName)
                .map(actorMapper::toDTO);
    }

}
