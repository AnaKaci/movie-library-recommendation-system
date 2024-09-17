package com.example.diplomeBackend.services;
import com.example.diplomeBackend.dto.PreferenceDTO;
import com.example.diplomeBackend.mappers.PreferenceMapper;
import com.example.diplomeBackend.models.*;
import com.example.diplomeBackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PreferenceMapper preferenceMapper;

    public List<Preference> findAll() {
        return preferenceRepository.findAll();
    }

    public Optional<Preference> findById(Long id) {
        return preferenceRepository.findById(id);
    }

    public Preference save(Preference preference) {
        return preferenceRepository.save(preference);
    }

    public void deleteById(Long id) {
        preferenceRepository.deleteById(id);
    }

    public List<PreferenceDTO> getFavoritePreferences(Long userId) {
        String favoriteTypes = "FAVORITE";
        List<Preference> preferences = preferenceRepository.findByUserIdAndPreferenceTypes(userId, favoriteTypes);

        return preferences.stream()
                .map(preferenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PreferenceDTO> getDislikedPreferences(Long userId) {
        String dislikedTypes = "DISLIKED";
        List<Preference> preferences = preferenceRepository.findByUserIdAndPreferenceTypes(userId, dislikedTypes);

        return preferences.stream()
                .map(preferenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Preference savePreference(Long userId, PreferenceDTO preferenceDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Actor actor = null;
        Director director = null;
        Genre genre = null;

        if (preferenceDTO.getActorName() != null) {
            actor = actorRepository.findByActorName(preferenceDTO.getActorName())
                    .orElseThrow(() -> new RuntimeException("Actor not found with name: " + preferenceDTO.getActorName()));
        }
        if (preferenceDTO.getDirectorName() != null) {
            director = directorRepository.findByDirectorName(preferenceDTO.getDirectorName())
                    .orElseThrow(() -> new RuntimeException("Director not found with name: " + preferenceDTO.getDirectorName()));
        }
        if (preferenceDTO.getGenreName() != null) {
            genre = genreRepository.findByGenreName(preferenceDTO.getGenreName())
                    .orElseThrow(() -> new RuntimeException("Genre not found with name: " + preferenceDTO.getGenreName()));
        }

        Preference newPreference = new Preference();
        newPreference.setUser(user);
        newPreference.setActor(actor);
        newPreference.setDirector(director);
        newPreference.setGenre(genre);
        newPreference.setPreferenceType(preferenceDTO.getPreferenceType());

        return preferenceRepository.save(newPreference);
    }






}
