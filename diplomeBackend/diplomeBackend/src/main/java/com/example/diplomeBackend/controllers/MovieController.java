package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.ActorDTO;
import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.dto.UserDTO;
import com.example.diplomeBackend.dto.WatchlistDTO;
import com.example.diplomeBackend.exceptions.DatabaseException;
import com.example.diplomeBackend.exceptions.ErrorResponse;
import com.example.diplomeBackend.exceptions.InvalidInputException;
import com.example.diplomeBackend.exceptions.UserNotFoundException;
import com.example.diplomeBackend.mappers.MovieMapper;
import com.example.diplomeBackend.mappers.WatchlistMapper;
import com.example.diplomeBackend.models.*;
import com.example.diplomeBackend.repositories.MovieRepository;
import com.example.diplomeBackend.services.MovieActorService;
import com.example.diplomeBackend.services.MovieService;
import com.example.diplomeBackend.services.UserService;
import com.example.diplomeBackend.services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieActorService movieActorService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private WatchlistMapper watchlistMapper;

    @Autowired
    private WatchlistService watchlistService;

    public MovieController(MovieActorService movieActorService, MovieService movieService,
                           UserService userService, WatchlistService watchlistService){

        this.movieActorService = movieActorService;
        this.movieService = movieService;
        this.userService = userService;
        this.watchlistService = watchlistService;
    }


    @GetMapping("/allmovies")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        try {
            List<MovieDTO> movies = movieService.getAllMovies();
            if (movies.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(movies);
            }
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMoviesByCategory(@RequestParam String category) {
        try {
            List<MovieDTO> movies = movieService.findMoviesByCategory(category);
            if (movies.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(movies);
            }
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable("id") Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie
                .map(m -> ResponseEntity.ok(movieMapper.toDTO(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{movieId}/actors")
    public ResponseEntity<List<ActorDTO>> getActorsByMovieId(@PathVariable Long movieId) {
        List<ActorDTO> actors = movieActorService.findActorsByMovieId(movieId);
        return ResponseEntity.ok(actors);
    }


    @GetMapping("/search/movies")
    public ResponseEntity<List<MovieDTO>> searchMovies(@RequestParam String query) {
        try {
            List<MovieDTO> movies = movieService.searchMovies(query);

            if (movies.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(movies);
            }
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(new MovieDTO()));
        }
    }

    @GetMapping("/search/users")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String query) {
        try {
            List<UserDTO> users = userService.searchUser(query);

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users);
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(new UserDTO()));
        }
    }



    @PostMapping("/addWatchlist")
    public ResponseEntity<WatchlistDTO> saveToWatchlist(@RequestBody WatchlistDTO watchlistDTO) {
        try {
            Watchlist watchlist = watchlistMapper.toEntity(watchlistDTO);
            watchlist.setDateAdded(new Date());

            Watchlist savedWatchlist = watchlistService.save(watchlist);

            WatchlistDTO savedWatchlistDTO = watchlistMapper.toDTO(savedWatchlist);

            return ResponseEntity.ok(savedWatchlistDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/removeWatchlist")
    public ResponseEntity<Void> removeFromWatchlist(@RequestBody WatchlistDTO watchlistDTO) {
        try {
            Watchlist watchlist = watchlistMapper.toEntity(watchlistDTO);

            watchlistService.deleteByUserIdAndMovieId(watchlist.getUser().getUserId(), watchlist.getMovie().getMovieId());

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/watchlist/{userId}")
    public ResponseEntity<List<MovieDTO>> getMoviesByUserId(@PathVariable("userId") Long userId) {
        try {
            List<MovieDTO> movies = watchlistService.getWatchlistByUserId(userId);
            if (movies.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(movies);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<Object> getRecommendations(@PathVariable("userId") Long userId) {
        try {
            List<MovieDTO> recommendedMovies = movieService.recommendMovies(userId);
            return ResponseEntity.ok(recommendedMovies);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User Not Found", "The user with ID " + userId + " was not found."));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid Input", "The provided user ID is invalid."));
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database Error", "An error occurred while accessing the database: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal Server Error", "An unexpected error occurred: " + e.getMessage()));
        }


    }


}



