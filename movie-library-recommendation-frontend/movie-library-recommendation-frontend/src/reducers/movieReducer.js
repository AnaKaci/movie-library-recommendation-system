import * as TYPES from "../actions/types";

const initialState = {
  movie: null,
  cast: [],
  loading: true,
  error: null,
  actor: null,
};

const movieReducer = (state = initialState, action) => {
  switch (action.type) {
    case TYPES.FETCH_ACTOR_REQUEST:
      return { ...state, loading: true, error: null };
    case TYPES.FETCH_ACTOR_SUCCESS:
      return { ...state, loading: false, actor: action.payload };
    case TYPES.FETCH_ACTOR_FAILURE:
      return { ...state, loading: false, error: action.payload };
    case TYPES.FETCH_MOVIE:
      return { ...state, movie: action.payload };
    case TYPES.FETCH_CAST:
      return { ...state, cast: action.payload };
    case TYPES.FETCH_MOVIE_LOADING:
      return { ...state, loading: true };
    case TYPES.FETCH_MOVIE_FINISHED:
      return { ...state, loading: false };
    case TYPES.FETCH_MOVIE_ERROR:
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
};

export default movieReducer;
