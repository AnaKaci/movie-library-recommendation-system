import * as TYPES from '../actions/types';

const initialState = {
  results: [],
  loading: false,
  error: null,
};

const moviesReducer = (state = initialState, action) => {
  switch (action.type) {
    case TYPES.FETCH_MOVIES_REQUEST:
    case TYPES.FETCH_ALL_MOVIES_REQUEST:
    case TYPES.FETCH_MOVIES_SEARCH_REQUEST:
    case TYPES.FETCH_MOVIES_BY_GENRE_REQUEST:
      return { ...state, loading: true, error: null };

    case TYPES.FETCH_MOVIES_SUCCESS:
    case TYPES.FETCH_POPULAR_MOVIES:
    case TYPES.FETCH_TOP_RATED_MOVIES:
    case TYPES.FETCH_UPCOMING_MOVIES:
    case TYPES.FETCH_ALL_MOVIES_SUCCESS:
    case TYPES.FETCH_MOVIES_SEARCH_SUCCESS:
    case TYPES.FETCH_MOVIES_BY_GENRE_SUCCESS:
      return { ...state, loading: false, results: action.payload };


    case TYPES.FETCH_MOVIES_FAILURE:
    case TYPES.FETCH_ALL_MOVIES_FAILURE:
    case TYPES.FETCH_MOVIES_SEARCH_FAILURE:
    case TYPES.FETCH_MOVIES_BY_GENRE_FAILURE:
    case TYPES.INSERT_ERROR:
      return { ...state, loading: false, error: action.payload };

    default:
      return state;
  }
};

export default moviesReducer;
