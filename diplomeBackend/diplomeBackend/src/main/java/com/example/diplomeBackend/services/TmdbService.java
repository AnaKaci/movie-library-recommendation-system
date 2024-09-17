
//A service to get examples for real movies to the database


/*package com.example.diplomeBackend.services;

import com.example.diplomeBackend.models.*;
import com.example.diplomeBackend.repositories.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private static final String TMDB_URL = "https://api.themoviedb.org/3/movie/popular?api_key=";
    private static final String TMDB_MOVIE_DETAILS_URL = "https://api.themoviedb.org/3/movie/";
    private static final String TMDB_GENRE_LIST_URL = "https://api.themoviedb.org/3/genre/movie/list?api_key=";

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;

    public TmdbService(MovieRepository movieRepository, DirectorRepository directorRepository,
                       GenreRepository genreRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
    }

    public List<Movie> fetchPopularMovies() throws IOException {
        List<Movie> movies = new ArrayList<>();
        List<Actor> allActors = new ArrayList<>();
        List<Genre> allGenres = new ArrayList<>();
        List<Director> allDirectors = new ArrayList<>();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String searchUrl = TMDB_URL + apiKey;

        HttpGet request = new HttpGet(searchUrl);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode resultsNode = rootNode.path("results");

            for (JsonNode node : resultsNode) {
                Long movieId = node.path("id").asLong();
                fetchActorsAndDirectors(movieId, allActors, allDirectors);
                fetchGenres(node.path("genre_ids"), allGenres);
            }

            saveActors(allActors);
            saveGenres(allGenres);
            saveDirectors(allDirectors);

            for (JsonNode node : resultsNode) {
                Long movieId = node.path("id").asLong();
                String title = node.path("title").asText();
                String description = node.path("overview").asText();
                Date releaseDate = parseDate(node.path("release_date").asText());
                int duration = fetchMovieDuration(movieId);
                Double averageRating = node.path("vote_average").asDouble();
                String poster = node.path("poster_path").asText();
                String trailer = fetchMovieTrailer(movieId);

                Set<Genre> genres = fetchGenresByName(node.path("genre_ids"));
                Set<Actor> actors = fetchActorsByName(movieId);
                Director director = fetchDirectorByName(movieId);

                Movie movie = new Movie(title, description, releaseDate, duration, director,
                        poster, trailer, averageRating, genres, actors);
                movieRepository.save(movie);
                movies.add(movie);
            }
        }

        return movies;
    }

    private void fetchActorsAndDirectors(Long movieId, List<Actor> allActors, List<Director> allDirectors) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(TMDB_MOVIE_DETAILS_URL + movieId + "/credits?api_key=" + apiKey);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode castNode = rootNode.path("cast");
            JsonNode crewNode = rootNode.path("crew");

            for (JsonNode node : castNode) {
                String actorName = node.path("name").asText();
                Actor actor = actorRepository.findByActorName(actorName).orElseGet(() -> {
                    Actor newActor = new Actor();
                    newActor.setActorName(actorName);
                    try {
                        newActor.setBio(fetchActorBio(actorName));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        newActor.setDateOfBirth(fetchActorDateOfBirth(actorName));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    return newActor;
                });
                allActors.add(actor);
            }

            for (JsonNode node : crewNode) {
                if (node.path("job").asText().equals("Director")) {
                    String directorName = node.path("name").asText();
                    Director director = directorRepository.findByDirectorName(directorName).orElseGet(() -> {
                        Director newDirector = new Director();
                        newDirector.setDirectorName(directorName);
                        try {
                            newDirector.setBio(fetchDirectorBio(directorName));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            newDirector.setDateOfBirth(fetchDirectorDateOfBirth(directorName));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        return newDirector;
                    });
                    allDirectors.add(director);
                }
            }
        }
    }

    private void fetchGenres(JsonNode genreIdsNode, List<Genre> allGenres) {
        for (JsonNode genreIdNode : genreIdsNode) {
            String genreName = null;
            try {
                genreName = fetchGenreName(genreIdNode.asLong());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String finalGenreName = genreName;
            Genre genre = genreRepository.findByGenreName(genreName).orElseGet(() -> {
                Genre newGenre = new Genre();
                newGenre.setGenreName(finalGenreName);
                return newGenre;
            });
            allGenres.add(genre);
        }
    }

    private void saveActors(List<Actor> allActors) {
        for (Actor actor : allActors) {
            actorRepository.findByActorName(actor.getActorName()).ifPresentOrElse(
                    existingActor -> {
                        System.out.println("Actor already exists: " + existingActor.getActorName());
                    },
                    () -> actorRepository.save(actor)
            );
        }
    }

    private void saveGenres(List<Genre> allGenres) {
        for (Genre genre : allGenres) {
            genreRepository.findByGenreName(genre.getGenreName()).ifPresentOrElse(
                    existingGenre -> {
                        System.out.println("Genre already exists: " + existingGenre.getGenreName());
                    },
                    () -> genreRepository.save(genre)
            );
        }
    }

    private void saveDirectors(List<Director> allDirectors) {
        for (Director director : allDirectors) {
            directorRepository.findByDirectorName(director.getDirectorName()).ifPresentOrElse(
                    existingDirector -> {
                        System.out.println("Director already exists: " + existingDirector.getDirectorName());
                    },
                    () -> directorRepository.save(director)
            );
        }
    }

    private int fetchMovieDuration(Long movieId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(TMDB_MOVIE_DETAILS_URL + movieId + "?api_key=" + apiKey);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            return rootNode.path("runtime").asInt();
        }
    }

    private String fetchMovieTrailer(Long movieId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(TMDB_MOVIE_DETAILS_URL + movieId + "/videos?api_key=" + apiKey);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode resultsNode = rootNode.path("results");

            for (JsonNode node : resultsNode) {
                if (node.path("site").asText().equals("YouTube") && node.path("type").asText().equals("Trailer")) {
                    return "https://www.youtube.com/watch?v=" + node.path("key").asText();
                }
            }
        }

        return "";
    }

    private Set<Genre> fetchGenresByName(JsonNode genreIdsNode) throws IOException {
        Set<Genre> genres = new HashSet<>();
        for (JsonNode genreIdNode : genreIdsNode) {
            String genreName = fetchGenreName(genreIdNode.asLong());
            Genre genre = genreRepository.findByGenreName(genreName).orElseThrow(() ->
                    new RuntimeException("Genre not found: " + genreName));
            genres.add(genre);
        }
        return genres;
    }

    private Set<Actor> fetchActorsByName(Long movieId) throws IOException {
        Set<Actor> actors = new HashSet<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(TMDB_MOVIE_DETAILS_URL + movieId + "/credits?api_key=" + apiKey);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode castNode = rootNode.path("cast");

            for (JsonNode node : castNode) {
                String actorName = node.path("name").asText();
                Actor actor = actorRepository.findByActorName(actorName).orElseThrow(() ->
                        new RuntimeException("Actor not found: " + actorName));
                actors.add(actor);
            }
        }
        return actors;
    }

    private Director fetchDirectorByName(Long movieId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(TMDB_MOVIE_DETAILS_URL + movieId + "/credits?api_key=" + apiKey);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode crewNode = rootNode.path("crew");

            for (JsonNode node : crewNode) {
                if (node.path("job").asText().equals("Director")) {
                    String directorName = node.path("name").asText();
                    return directorRepository.findByDirectorName(directorName).orElseThrow(() ->
                            new RuntimeException("Director not found: " + directorName));
                }
            }
        }

        return null;
    }

    private String fetchGenreName(Long genreId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(TMDB_GENRE_LIST_URL + apiKey);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode genresNode = rootNode.path("genres");

            for (JsonNode node : genresNode) {
                if (node.path("id").asLong() == genreId) {
                    return node.path("name").asText();
                }
            }
        }

        return null;
    }

    private String fetchActorBio(String actorName) throws IOException {
        // Implement this method to fetch actor bio
        return "Bio not available";
    }

    private Date fetchActorDateOfBirth(String actorName) throws IOException, ParseException {
        // Implement this method to fetch actor date of birth
        return new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01");
    }

    private String fetchDirectorBio(String directorName) throws IOException {
        // Implement this method to fetch director bio
        return "Bio not available";
    }

    private Date fetchDirectorDateOfBirth(String directorName) throws IOException, ParseException {
        // Implement this method to fetch director date of birth
        return new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01");
    }

    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}*/
