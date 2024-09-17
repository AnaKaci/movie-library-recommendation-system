import * as TYPES from '../actions/types';

const initialState = {
    actors: [],
    genres: [],
    directors: [],
    loading: false,
    error: null,
    results: {
        favoriteGenres: '',
        dislikedGenres: '',
        favoriteActors: '',
        dislikedActors: '',
        favoriteDirectors: '',
        dislikedDirectors: '',
        preferenceType: ['FAVORITE', 'DISLIKED']
    }
};

const preferenceReducer = (state = initialState, action) => {
    switch (action.type) {
        case TYPES.SET_PREFERENCES:
            return {
                ...state,
                results: {
                    favoriteGenres: Array.isArray(action.payload.favoriteGenres) ? action.payload.favoriteGenres : [],
                    dislikedGenres: Array.isArray(action.payload.dislikedGenres) ? action.payload.dislikedGenres : [],
                    favoriteActors: Array.isArray(action.payload.favoriteActors) ? action.payload.favoriteActors : [],
                    dislikedActors: Array.isArray(action.payload.dislikedActors) ? action.payload.dislikedActors : [],
                    favoriteDirectors: Array.isArray(action.payload.favoriteDirectors) ? action.payload.favoriteDirectors : [],
                    dislikedDirectors: Array.isArray(action.payload.dislikedDirectors) ? action.payload.dislikedDirectors : [],
                    preferenceType: action.payload.preferenceType || ''
                }
            };

        case TYPES.FETCH_ACTORS_REQUEST:
        case TYPES.FETCH_DIRECTORS_REQUEST:
        case TYPES.FETCH_GENREPREFERENCE_REQUEST:
        case TYPES.ADD_PREFERENCES_REQUEST:
            return {
                ...state,
                loading: true
            };

        case TYPES.FETCH_ACTORS_SUCCESS:
            return {
                ...state,
                loading: false,
                actors: action.payload
            };

        case TYPES.FETCH_DIRECTORS_SUCCESS:
            return {
                ...state,
                loading: false,
                directors: action.payload
            };

        case TYPES.FETCH_GENREPREFERENCE_SUCCESS:
            return {
                ...state,
                loading: false,
                genres: action.payload
            };

        case TYPES.ADD_PREFERENCES_SUCCESS:
            return {
                ...state,
                loading: false,
                results: {
                    ...state.results,
                    favoriteGenres: [
                        ...state.results.favoriteGenres,
                        ...action.payload.favoriteGenres
                    ],
                    dislikedGenres: [
                        ...state.results.dislikedGenres,
                        ...action.payload.dislikedGenres
                    ],
                    favoriteActors: [
                        ...state.results.favoriteActors,
                        ...action.payload.favoriteActors
                    ],
                    dislikedActors: [
                        ...state.results.dislikedActors,
                        ...action.payload.dislikedActors
                    ],
                    favoriteDirectors: [
                        ...state.results.favoriteDirectors,
                        ...action.payload.favoriteDirectors
                    ],
                    dislikedDirectors: [
                        ...state.results.dislikedDirectors,
                        ...action.payload.dislikedDirectors
                    ]
                }
            };

        case TYPES.FETCH_ACTORS_FAILURE:
        case TYPES.FETCH_DIRECTORS_FAILURE:
        case TYPES.FETCH_GENREPREFERENCE_FAILURE:
        case TYPES.ADD_PREFERENCES_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.error
            };

        default:
            return state;
    }
};

export default preferenceReducer;
