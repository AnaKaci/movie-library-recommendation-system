package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.dto.UserDTO;
import com.example.diplomeBackend.mappers.UserMapper;
import com.example.diplomeBackend.models.LogInUser;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.services.ActorService;
import com.example.diplomeBackend.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServ;

    @MockBean
    private ActorService actorService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByEmail_Success() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        when(userServ.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/user/email/{email}", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    public void testGetUserByEmail_NotFound() throws Exception {
        when(userServ.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/user/email/{email}", "nonexistent@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllActors_Success() throws Exception {
        List<ActorDTO> actors = Arrays.asList(new ActorDTO("John Doe", "Famous Actor", new Date(), Set.of(1L, 2L)),
        new ActorDTO("Jane Smith", "Award-Winning Actress", new Date(), Set.of(3L, 4L)));
        when(actorService.findAll()).thenReturn(actors);

        mockMvc.perform(get("/api/getactors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].name", is("Jane Smith")));
    }

    @Test
    public void testGetAllActors_NoContent() throws Exception {
        when(actorService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/getactors"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testLogin_Success() throws Exception {
        LogInUser loginRequest = new LogInUser("test@example.com", "password");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashedPassword");

        when(userServ.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userServ.checkPassword("password", user.getPassword())).thenReturn(true);
        when(userMapper.toDTO(user)).thenReturn(new UserDTO(1L,"testexample", "test@example.com", "test", "example", new Date()));

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Login successful!")));
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        LogInUser loginRequest = new LogInUser("test@example.com", "wrongPassword");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("correctPassword");

        when(userServ.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userServ.checkPassword("wrongPassword", user.getPassword())).thenReturn(false);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid email or password.")));
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        User user = new User();
        user.setEmail("newuser@example.com");

        Mockito.doThrow(new RuntimeException("Error")).when(userServ).saveUser(Mockito.any(User.class));

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("User registered successfully!")));
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        User user = new User();
        user.setUserId(1L);

        when(userServ.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/api/user/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userServ, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        when(userServ.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/user/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}
