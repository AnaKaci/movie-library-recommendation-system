package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.PreferenceDTO;
import com.example.diplomeBackend.mappers.PreferenceMapper;
import com.example.diplomeBackend.models.*;
import com.example.diplomeBackend.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreferenceServiceTest {

    @InjectMocks
    private PreferenceService preferenceService;

    @Mock
    private PreferenceRepository preferenceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private PreferenceMapper preferenceMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Preference preference = new Preference();
        when(preferenceRepository.findAll()).thenReturn(List.of(preference));

        List<Preference> result = preferenceService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(preferenceRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long preferenceId = 1L;
        Preference preference = new Preference();
        when(preferenceRepository.findById(preferenceId)).thenReturn(Optional.of(preference));

        Optional<Preference> result = preferenceService.findById(preferenceId);

        assertTrue(result.isPresent());
        assertEquals(preference, result.get());
        verify(preferenceRepository, times(1)).findById(preferenceId);
    }

    @Test
    void testSave() {
        Preference preference = new Preference();
        when(preferenceRepository.save(preference)).thenReturn(preference);

        Preference result = preferenceService.save(preference);

        assertNotNull(result);
        assertEquals(preference, result);
        verify(preferenceRepository, times(1)).save(preference);
    }

    @Test
    void testDeleteById() {
        Long preferenceId = 1L;

        doNothing().when(preferenceRepository).deleteById(preferenceId);

        preferenceService.deleteById(preferenceId);

        verify(preferenceRepository, times(1)).deleteById(preferenceId);
    }

    @Test
    void testGetFavoritePreferences() {
        Long userId = 1L;
        Preference preference = new Preference();
        PreferenceDTO preferenceDTO = new PreferenceDTO();

        when(preferenceRepository.findByUserIdAndPreferenceTypes(userId, "FAVORITE")).thenReturn(List.of(preference));
        when(preferenceMapper.toDTO(preference)).thenReturn(preferenceDTO);

        List<PreferenceDTO> result = preferenceService.getFavoritePreferences(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(preferenceDTO, result.get(0));
        verify(preferenceRepository, times(1)).findByUserIdAndPreferenceTypes(userId, "FAVORITE");
        verify(preferenceMapper, times(1)).toDTO(preference);
    }

    @Test
    void testGetDislikedPreferences() {
        Long userId = 1L;
        Preference preference = new Preference();
        PreferenceDTO preferenceDTO = new PreferenceDTO();

        when(preferenceRepository.findByUserIdAndPreferenceTypes(userId, "DISLIKED")).thenReturn(List.of(preference));
        when(preferenceMapper.toDTO(preference)).thenReturn(preferenceDTO);

        List<PreferenceDTO> result = preferenceService.getDislikedPreferences(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(preferenceDTO, result.get(0));
        verify(preferenceRepository, times(1)).findByUserIdAndPreferenceTypes(userId, "DISLIKED");
        verify(preferenceMapper, times(1)).toDTO(preference);
    }

    @Test
    void testSavePreference() {
        Long userId = 1L;
        String actorName = "ActorName";
        String directorName = "DirectorName";
        String genreName = "GenreName";
        String preferenceType = "FAVORITE";

        User user = new User();
        Actor actor = new Actor();
        Director director = new Director();
        Genre genre = new Genre();
        Preference preference = new Preference();
        PreferenceDTO preferenceDTO = new PreferenceDTO();
        preferenceDTO.setActorName(actorName);
        preferenceDTO.setDirectorName(directorName);
        preferenceDTO.setGenreName(genreName);
        preferenceDTO.setPreferenceType(preferenceType);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(actorRepository.findByActorName(actorName)).thenReturn(Optional.of(actor));
        when(directorRepository.findByDirectorName(directorName)).thenReturn(Optional.of(director));
        when(genreRepository.findByGenreName(genreName)).thenReturn(Optional.of(genre));
        when(preferenceRepository.save(any(Preference.class))).thenReturn(preference);

        Preference result = preferenceService.savePreference(userId, preferenceDTO);

        assertNotNull(result);
        assertEquals(preference, result);
        verify(userRepository, times(1)).findById(userId);
        verify(actorRepository, times(1)).findByActorName(actorName);
        verify(directorRepository, times(1)).findByDirectorName(directorName);
        verify(genreRepository, times(1)).findByGenreName(genreName);
        verify(preferenceRepository, times(1)).save(any(Preference.class));
    }
}
