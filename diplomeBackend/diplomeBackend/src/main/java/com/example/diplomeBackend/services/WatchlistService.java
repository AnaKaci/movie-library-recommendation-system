package com.example.diplomeBackend.services;
import com.example.diplomeBackend.dto.MovieDTO;
import com.example.diplomeBackend.mappers.MovieMapper;
import com.example.diplomeBackend.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diplomeBackend.models.Watchlist;
import com.example.diplomeBackend.repositories.WatchlistRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;
    @Autowired
    private MovieMapper movieMapper;

    public List<Watchlist> findAll() {
        return watchlistRepository.findAll();
    }

    public Optional<Watchlist> findById(Long id) {
        return watchlistRepository.findById(id);
    }



    public Watchlist save(Watchlist watchlist) {
        return watchlistRepository.save(watchlist);
    }

    public void deleteById(Long id) {
        watchlistRepository.deleteById(id);
    }
    public List<MovieDTO> getWatchlistByUserId(Long userId) {
        List<Movie> movies = watchlistRepository.findMoviesByUserId(userId);
        return movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }
    public void deleteByUserIdAndMovieId(Long userId, Long movieId) {
        watchlistRepository.deleteByUserIdAndMovieId(userId, movieId);
    }




}
