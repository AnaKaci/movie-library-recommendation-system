import * as TYPES from '../actions/types';

const INITIAL_STATE = {
  base: {},
  genres: [],
  selected: '',
  staticCategories: ['Popular', 'Top Rated', 'Upcoming'],
  loading: true,
  genresLoading: false,
  genresError: null,
  Watchlist: 'Watchlist',
};

const geralReducer = (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case TYPES.GET_CONFIG:
      return { ...state, base: action.payload };

    case TYPES.GET_GENRES_REQUEST:
      return { ...state, genresLoading: true, genresError: null };

    case TYPES.GET_GENRES_SUCCESS:
      return { ...state, genres: action.payload, genresLoading: false };

    case TYPES.GET_GENRES_FAILURE:
      return { ...state, genresError: action.payload, genresLoading: false };

    case TYPES.SELECTED_STATIC_CATEGORY:
      return { ...state, selected: action.payload };

    case TYPES.SELECTED_GENRE:
      return { ...state, selected: action.payload };

    case TYPES.REMOVE_SELECTED_MENU:
      return { ...state, selected: '' };

    case TYPES.SELECTED_WATCHLIST:
      return { ...state, selectedMenu: action.payload };

    case TYPES.INVALID_MENU_SELECTION:
      return { ...state };

    case TYPES.RESET_SELECTED_MENU:
      return { ...state, selected: '' };

    case TYPES.SET_LOADING:
      return { ...state, loading: true };

    case TYPES.REMOVE_LOADING:
      return { ...state, loading: false };

    default:
      return state;
  }
};

export default geralReducer;
