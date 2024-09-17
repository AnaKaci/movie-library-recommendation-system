package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.DirectorDTO;
import com.example.diplomeBackend.mappers.DirectorMapper;
import com.example.diplomeBackend.models.Director;
import com.example.diplomeBackend.repositories.DirectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private DirectorMapper directorMapper;

    @InjectMocks
    private DirectorService directorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // Arrange
        Director director = new Director();
        DirectorDTO directorDTO = new DirectorDTO("Director Name", "Bio", new Date()); // Adjust the constructor if needed
        List<Director> directors = List.of(director);

        when(directorRepository.findAll()).thenReturn(directors);
        when(directorMapper.toDTO(director)).thenReturn(directorDTO);

        // Act
        List<DirectorDTO> result = directorService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(directorDTO, result.get(0));

        verify(directorRepository, times(1)).findAll();
        verify(directorMapper, times(1)).toDTO(director);
    }

    @Test
    public void testFindById_Found() {
        // Arrange
        Long id = 1L;
        Director director = new Director(); // Initialize with required fields
        DirectorDTO directorDTO = new DirectorDTO("Director Name", "Bio", new Date()); // Adjust the constructor if needed

        when(directorRepository.findById(id)).thenReturn(Optional.of(director));
        when(directorMapper.toDTO(director)).thenReturn(directorDTO);

        // Act
        Optional<DirectorDTO> result = directorService.findById(id).map(directorMapper::toDTO);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(directorDTO, result.get());

        verify(directorRepository, times(1)).findById(id);
        verify(directorMapper, times(1)).toDTO(director);
    }

    @Test
    public void testFindById_NotFound() {
        // Arrange
        Long id = 1L;

        when(directorRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<DirectorDTO> result = directorService.findById(id).map(directorMapper::toDTO);

        // Assert
        assertFalse(result.isPresent());

        verify(directorRepository, times(1)).findById(id);
        verify(directorMapper, times(0)).toDTO(any());
    }
}
