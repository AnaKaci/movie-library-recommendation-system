package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.UserDTO;
import com.example.diplomeBackend.exceptions.ResourceNotFoundException;
import com.example.diplomeBackend.mappers.UserMapper;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        User user = new User();
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testSave() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteById() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setPassword("plainPassword");
        String hashedPassword = "hashedPassword";

        when(passwordEncoder.encode("plainPassword")).thenReturn(hashedPassword);
        when(userRepository.save(user)).thenReturn(user);

        userService.saveUser(user);

        assertEquals(hashedPassword, user.getPassword());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testHashPassword() {
        String rawPassword = "plainPassword";
        String hashedPassword = "hashedPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);

        String result = userService.hashPassword(rawPassword);

        assertEquals(hashedPassword, result);
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }

    @Test
    void testCheckPassword() {
        String rawPassword = "plainPassword";
        String encodedPassword = "hashedPassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean result = userService.checkPassword(rawPassword, encodedPassword);

        assertTrue(result);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindUserDTOById() {
        Long userId = 1L;
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.findUserById(userId)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.findUserDTOById(userId);

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userRepository, times(1)).findUserById(userId);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testFindUserById() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findUserById(userId)).thenReturn(user);

        User result = userService.findUserById(userId);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).findUserById(userId);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User existingUser = new User();
        User updatedUser = new User();
        updatedUser.setFirstName("NewFirstName");
        updatedUser.setLastName("NewLastName");
        updatedUser.setEmail("newemail@example.com");
        updatedUser.setPassword("newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("NewFirstName", existingUser.getFirstName());
        assertEquals("NewLastName", existingUser.getLastName());
        assertEquals("newemail@example.com", existingUser.getEmail());
        assertEquals("encodedPassword", existingUser.getPassword());
        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).encode(updatedUser.getPassword());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_NotFound() {
        Long userId = 1L;
        User updatedUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });

        String expectedMessage = "User not found with id " + userId;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testSearchUser() {
        String query = "test";
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.searchUser(query)).thenReturn(List.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        List<UserDTO> result = userService.searchUser(query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDTO, result.get(0));
        verify(userRepository, times(1)).searchUser(query);
        verify(userMapper, times(1)).toDTO(user);
    }
}
