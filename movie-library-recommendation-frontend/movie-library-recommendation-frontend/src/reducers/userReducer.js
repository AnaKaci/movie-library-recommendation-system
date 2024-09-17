import * as TYPES from '../actions/types';

const initialState = {
  loggedInUser: {
    loading: false,
    user: null,
    isLoggedIn: false,
    error: null
  }
};

const userReducer = (state = initialState, action) => {
  switch (action.type) {
    case TYPES.LOGIN_SUCCESS:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          user: action.payload,
          isLoggedIn: true,
          loading: false,
          error: null
        }
      };
    case TYPES.LOGIN_FAILURE:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          error: action.payload,
          isLoggedIn: false,
          loading: false
        }
      };
    case TYPES.LOGOUT_REQUEST:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          loading: true
        }
      };
    case TYPES.LOGOUT_SUCCESS:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          isLoggedIn: false,
          user: null,
          loading: false,
          error: null
        }
      };
    case TYPES.LOGOUT_FAILURE:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          loading: false,
          error: action.payload
        }
      };
    case TYPES.SET_USER_LOADING:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          loading: true
        }
      };
    case TYPES.UPDATE_PROFILE_REQUEST:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          loading: true,
          error: null
        }
      };
    case TYPES.UPDATE_PROFILE_SUCCESS:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          loading: false,
          user: action.payload
        }
      };
    case TYPES.UPDATE_PROFILE_FAILURE:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          loading: false,
          error: action.payload
        }
      };
    case TYPES.FETCH_USER_LOADING:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          loading: true,
          error: null
        }
      };

    case TYPES.FETCH_USER:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          user: action.payload,
          loading: false,
          error: null
        }
      };

    case TYPES.FETCH_USER_ERROR:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          loading: false,
          error: action.payload
        }
      };

    case TYPES.CLEAR_USER_SUCCESS:
      return {
        ...state,
        loggedInUser: {
          ...state.loggedInUser,
          user: null,
          isLoggedIn: false,
          loading: false,
          error: null
        }
      };

    default:
      return state;
  }
};

export default userReducer;
