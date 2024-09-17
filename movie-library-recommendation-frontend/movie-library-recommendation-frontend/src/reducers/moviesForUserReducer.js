import * as TYPES from '../actions/types';

const initialState = {
  watchlists: [],
  loading: false,
  error: null,
};

const moviesForUserReducer = (state = { loading: true }, action) => {
  switch (action.type) {
    case TYPES.FETCH_MOVIESPERSON_LOADING:
      return {
        ...state,
        loading: true,
      };
    case TYPES.FETCH_MOVIESPERSON:
      return {
        ...state,
        watchlists: action.payload,
        loading: false,
        error: null,
      };
    case TYPES.INSERT_ERROR:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };
    case TYPES.FETCH_MOVIESPERSON_FINISHED:
      return {
        ...state,
        loading: false,
      };
    default:
      return state;
  }
};

export default moviesForUserReducer;
