import * as TYPES from '../actions/types';

const initialState = {
  movies: [],
  loading: false,
  error: null,
};

const recommendationsReducer = (state = initialState, action) => {
  switch (action.type) {
    case TYPES.FETCH_RECOMMENDATIONS:
      return {
        ...state,
        movies: action.payload,
        loading: false,
        error: null,
      };
    case TYPES.FETCH_RECOMMENDATIONS_LOADING:
      return {
        ...state,
        loading: true,
      };
    case TYPES.FETCH_RECOMMENDATIONS_FINISHED:
      return {
        ...state,
        loading: false,
      };
    case TYPES.INSERT_ERROR:
      return {
        ...state,
        error: action.payload,
        loading: false,
      };
    default:
      return state;
  }
};

export default recommendationsReducer;
