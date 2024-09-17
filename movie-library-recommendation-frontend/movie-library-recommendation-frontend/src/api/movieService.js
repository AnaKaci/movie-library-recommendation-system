import axios from 'axios';

const apiClient1 = axios.create({
    baseURL: 'http://localhost:8082',
    headers: {
        'Content-Type': 'application/json',
    },
});



// Movie api services
export const fetchMovieById = (id) => {
    return apiClient1.get(`/api/movies/${id}`);
};

export const getAllMovies = () => {
    return apiClient1.get(`/api/movies/allmovies`);
};

export const getActorByName = async (actorName) => {
    return apiClient1.get(`/api/actor/${actorName}`);
};


export const getActorsByMovieId = async (movieId) => {
    return apiClient1.get(`/api/movies/${movieId}/actors`);
};

export const fetchUserMovies = (userId) => {
    return apiClient1.get(`/api/movies/watchlist/${userId}`);
};

export const getMoviesByCategory = async (category) => {
    return apiClient1.get(`/api/movies?category=${category}`);
};

export const fetchMoviesSearch = async (query) => {
    const response = await apiClient1.get(`/api/movies/search/movies?query=${query}`);
    return response.data;
};

export const fetchUserSearch = async (query) => {
    const response = await apiClient1.get(`/api/movies/search/users?query=${query}`);
    return response.data;
};



// Watchlist api services
export const recommendedMovies = (id) => {
    return apiClient1.get(`/api/movies/recommendations/${id}`);
};

export const watchlistMovies = async (userId, movieId) => {
    try {
        const response = await apiClient1.post('/api/movies/addWatchlist', {
            userId,
            movieId
        });
        return response;
    } catch (error) {
        console.error('Error adding movie to watchlist:', error);
        throw error;
    }
};

export const deleteFromWatchlist = (userId, movieId) => {
    return apiClient1.delete(`/api/movies/removeWatchlist`, {
        data: {
            userId,
            movieId
        }
    });
};





