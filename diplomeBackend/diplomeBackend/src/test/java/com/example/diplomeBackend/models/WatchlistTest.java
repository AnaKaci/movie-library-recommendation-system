package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class WatchlistTest {

    @Test
    public void testWatchlistDefaultConstructor() {
        Watchlist watchlist = new Watchlist();
        assertNotNull(watchlist);
        assertNull(watchlist.getWatchlistId());
        assertNull(watchlist.getUser());
        assertNull(watchlist.getMovie());
        assertNull(watchlist.getDateAdded());
    }

    @Test
    public void testWatchlistParameterizedConstructor() {
        User user = new User();
        Movie movie = new Movie();
        Date dateAdded = new Date();

        Watchlist watchlist = new Watchlist(user, movie, dateAdded);

        assertNotNull(watchlist);
        assertEquals(user, watchlist.getUser());
        assertEquals(movie, watchlist.getMovie());
        assertEquals(dateAdded, watchlist.getDateAdded());
    }

    @Test
    public void testWatchlistSettersAndGetters() {
        Watchlist watchlist = new Watchlist();
        User user = new User();
        Movie movie = new Movie();
        Date dateAdded = new Date();

        watchlist.setWatchlistId(1L);
        watchlist.setUser(user);
        watchlist.setMovie(movie);
        watchlist.setDateAdded(dateAdded);

        assertEquals(1L, watchlist.getWatchlistId());
        assertEquals(user, watchlist.getUser());
        assertEquals(movie, watchlist.getMovie());
        assertEquals(dateAdded, watchlist.getDateAdded());
    }

}
