package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.*;
import com.example.diplomeBackend.mappers.FriendsMapper;
import com.example.diplomeBackend.mappers.PreferenceMapper;
import com.example.diplomeBackend.mappers.UserMapper;
import com.example.diplomeBackend.models.*;
import com.example.diplomeBackend.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class HomeController {
    @Autowired
    private UserService userServ;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ActorService actorService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private PreferenceMapper preferenceMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private FriendsMapper friendsMapper;


    public HomeController(UserService userServ, ActorService actorService,
                          DirectorService directorService, GenreService genreService,
                          PreferenceService preferenceService, FriendsService friendsService){

        this.userServ = userServ;
        this.actorService = actorService;
        this.genreService = genreService;
        this.directorService = directorService;
        this.preferenceService = preferenceService;
        this.friendsService = friendsService;
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userServ.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getactors")
    public ResponseEntity<List<ActorDTO>> getAllActors() {
        try {
            List<ActorDTO> actors = actorService.findAll();
            if (actors.isEmpty()) {
                return ResponseEntity.noContent().build(); // No content found
            }
            return ResponseEntity.ok(actors);
        } catch (Exception e) {
            // Log the exception if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getdirectors")
    public ResponseEntity<List<DirectorDTO>> getAllDirectors() {
        try {
            List<DirectorDTO> directors = directorService.findAll();
            if (directors.isEmpty()) {
                return ResponseEntity.noContent().build(); // No content found
            }
            return ResponseEntity.ok(directors);
        } catch (Exception e) {
            // Log the exception if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle unexpected errors
        }
    }

    @GetMapping("/actor/{actorName}")
    public ResponseEntity<ActorDTO> getActorByName(@PathVariable String actorName) {
        Optional<ActorDTO> actorDTO = actorService.findByName(actorName);
        return actorDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/getgenres")
    public ResponseEntity<List<GenreDTO>> getAllGenresForPreference() {
        try {
            List<GenreDTO> genres = genreService.findAllGenresForPreference();
            if (genres.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(genres);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LogInUser loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> optionalUser = userServ.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userServ.checkPassword(password, user.getPassword())) {
                UserDTO userDTO = userMapper.toDTO(user);

                LoginResponse response = new LoginResponse(true, "Login successful!", userDTO);
                return ResponseEntity.ok(response);
            } else {
                LoginResponse response = new LoginResponse(false, "Invalid email or password.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } else {
            LoginResponse response = new LoginResponse(false, "Invalid email or password.", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logout successful.");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        try {
            UserDTO userDTO = userServ.findUserDTOById(id);

            if (userDTO != null) {
                return ResponseEntity.ok(userDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            user.setDateJoined(new Date());
            userServ.saveUser(user);
            response.put("success", true);
            response.put("message", "User registered successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred while registering the user.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> user = userServ.findById(id);
        if (user.isPresent()) {
            userServ.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/user/{userId}/preferences")
    public ResponseEntity<PreferenceDTO> addPreferences(@PathVariable Long userId, @RequestBody PreferenceDTO preferencesDTOs) {
        try {
            Preference savedPreference = preferenceService.savePreference(userId, preferencesDTOs);
            PreferenceDTO preferenceDTO = preferenceMapper.toDTO(savedPreference);
            return ResponseEntity.ok(preferenceDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userServ.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user/{userId}/friends/{friendId}")
    public ResponseEntity<FriendsDTO> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        try {
            Friends friends = friendsService.addFriend(userId, friendId);
            FriendsDTO friendsDTO = friendsMapper.toDTO(friends);
            return ResponseEntity.ok(friendsDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{userId}/friends")
    public ResponseEntity<List<UserDTO>> getAllFriends(@PathVariable Long userId) {
        try {
            List<User> friendsList = friendsService.getAllFriends(userId);
            List<UserDTO> friendsDTOList = friendsList.stream()
                    .map(userMapper::toDTO)
                    .collect(Collectors.toList());
            if (friendsDTOList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(friendsDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/remove/friend")
    public ResponseEntity<String> removeFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        try {
            friendsService.removeFriend(userId, friendId);
            return ResponseEntity.ok("Friend removed successfully.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the friend.");
        }

    }

    @GetMapping("/mutual-friends")
    public ResponseEntity<Boolean> areFriends(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {

        boolean areFriends = friendsService.areMutualFriends(senderId, receiverId);

        return ResponseEntity.ok(areFriends);
    }



}



