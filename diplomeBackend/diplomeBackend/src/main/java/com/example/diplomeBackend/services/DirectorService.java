package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.DirectorDTO;
import com.example.diplomeBackend.mappers.DirectorMapper;
import com.example.diplomeBackend.models.Director;
import com.example.diplomeBackend.repositories.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private DirectorMapper directorMapper;

    public List<DirectorDTO> findAll() {
        List<Director> directors = directorRepository.findAll();
        return directors.stream()
                .map(directorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Director> findById(Long id) {
        return directorRepository.findById(id);
    }

}
