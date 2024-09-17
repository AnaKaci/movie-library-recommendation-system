import * as TYPES from './types';
import {
  fetchConfiguration, fetchGenres, fetchMoviesByGenre,
  fetchUserById, deleteUser, logoutUser,
  updateProfile, fetchAllActors, fetchAllDirectors,
  fetchGenresForPreference, addFeedback, getFeedbacks,
  addFriend, getAllFriends, removeFriend,
  getMutualFriends, getMessages, updateFeedback, deleteFeedback, getFeedbacksUser
} from '../api/apiService';
import {
  getActorsByMovieId, fetchUserMovies,
  fetchMoviesSearch, recommendedMovies,
  getAllMovies, getMoviesByCategory,
  watchlistMovies, deleteFromWatchlist,
  fetchUserSearch, getActorByName
} from '../api/movieService';
import history from '../history';


// When app initializes
export const init = () => async dispatch => {
  dispatch({ type: TYPES.SET_LOADING });
  try {
    await dispatch(getConfig());
    await dispatch(getGenres());
  } catch (err) {
    dispatch({ type: TYPES.INSERT_ERROR, payload: err.response });
    history.push(process.env.PUBLIC_URL + '/error');
  } finally {
    dispatch({ type: TYPES.REMOVE_LOADING });
  }
};


// Action Creator to get the config object from the API
export const getConfig = () => async dispatch => {
  try {
    const res = await fetchConfiguration('/configuration');
    dispatch({
      type: TYPES.GET_CONFIG,
      payload: res.data,
    });
  } catch (err) {
    dispatch({ type: TYPES.INSERT_ERROR, payload: err.response });
    history.push(process.env.PUBLIC_URL + '/error');
  }
};


// Movies related action
export const fetchAllMovies = () => {
  return async (dispatch) => {
    dispatch({ type: TYPES.FETCH_ALL_MOVIES_REQUEST });
    try {
      const response = await getAllMovies();
      const moviesArray = Array.isArray(response.data) ? response.data : [];
      dispatch({
        type: TYPES.FETCH_ALL_MOVIES_SUCCESS,
        payload: moviesArray,
      });
    } catch (error) {
      console.error('Error fetching movies:', error);
      dispatch({
        type: TYPES.FETCH_ALL_MOVIES_FAILURE,
        error: error.message || 'Something went wrong while fetching movies',
      });
    }
  };
};


export const fetchActors = () => {
  return async (dispatch) => {
    dispatch({ type: TYPES.FETCH_ACTORS_REQUEST });

    try {
      const response = await fetchAllActors();
      dispatch({
        type: TYPES.FETCH_ACTORS_SUCCESS,
        payload: response.data
      });
    } catch (error) {
      dispatch({
        type: TYPES.FETCH_ACTORS_FAILURE,
        payload: error.message
      });
    }
  };
};

export const fetchDirectors = () => {
  return async (dispatch) => {
    dispatch({ type: TYPES.FETCH_DIRECTORS_REQUEST });

    try {
      const response = await fetchAllDirectors();
      dispatch({
        type: TYPES.FETCH_DIRECTORS_SUCCESS,
        payload: response.data
      });
    } catch (error) {
      dispatch({
        type: TYPES.FETCH_DIRECTORS_FAILURE,
        payload: error.message
      });
    }
  };
};

export const setPreferences = (preference) => ({
  type: TYPES.SET_PREFERENCES,
  payload: preference,
});

export const getGenresForPreference = () => {
  return async (dispatch) => {
    dispatch({ type: TYPES.FETCH_GENREPREFERENCE_REQUEST });

    try {
      const response = await fetchGenresForPreference();
      dispatch({
        type: TYPES.FETCH_GENREPREFERENCE_SUCCESS,
        payload: response.data
      });
    } catch (error) {
      dispatch({
        type: TYPES.FETCH_GENREPREFERENCE_FAILURE,
        payload: error.message
      });
    }
  };
};

// Get genres from API
export const getGenres = () => async dispatch => {
  dispatch({ type: TYPES.GET_GENRES_REQUEST });
  try {
    const res = await fetchGenres();
    if (res && res.data) {
      dispatch({
        type: TYPES.GET_GENRES_SUCCESS,
        payload: res.data,
      });
    } else {
      throw new Error('Unexpected response structure');
    }
  } catch (err) {
    console.error('Error fetching genres:', err);
    dispatch({
      type: TYPES.GET_GENRES_FAILURE,
      payload: err.response?.data || 'An error occurred'
    });
  } finally {
    dispatch({ type: TYPES.GET_GENRES_FINISHED });
  }
};

// Set the current selected menu (discover or genre), if is valid
export const setSelectedMenu = (name) => (dispatch, getState) => {
  const { staticCategories, genres, Watchlist } = getState().geral;
  if (!name) {
    dispatch({ type: TYPES.REMOVE_SELECTED_MENU });
    return;
  }
  const isStaticCategory = staticCategories.includes(name);
  if (isStaticCategory) {
    dispatch({
      type: TYPES.SELECTED_STATIC_CATEGORY,
      payload: name,
    });
  } else if (name === Watchlist) {
    dispatch({
        type: TYPES.SELECTED_WATCHLIST,
        payload: name,
  });}
  else {
    const isGenre = genres.some(genre => genre.genreName === name);
    if (isGenre) {
      dispatch({
        type: TYPES.SELECTED_GENRE,
        payload: name,
      });
    } else {
      dispatch({
        type: TYPES.INVALID_MENU_SELECTION,
        payload: name,
      });
    }
  }
};


// Get movies by genre name
export const getMoviesByGenreName = (genreName) => async (dispatch, getState) => {
  const { genres } = getState().geral;
  const isGenre = genres.some(genre => genre.genreName === genreName);
  if (!isGenre) {
    return;
  }
  dispatch({ type: TYPES.FETCH_MOVIES_BY_GENRE_REQUEST });
  try {
    dispatch({ type: TYPES.FETCH_MOVIES_LOADING });
    const res = await fetchMoviesByGenre(genreName);
    dispatch({
      type: TYPES.FETCH_MOVIES_BY_GENRE_SUCCESS,
      payload: res.data,
    });
  } catch (err) {
    const errorMessage = err.response?.data || 'An unexpected error occurred.';
    console.error('Error fetching movies by genre:', errorMessage);
    dispatch({
      type: TYPES.FETCH_MOVIES_BY_GENRE_FAILURE,
      payload: errorMessage,
    });
  } finally {
    dispatch({ type: TYPES.FETCH_MOVIES_FINISHED });
  }
};


// Get movies discover
export const getMoviesDiscover = (category) => async (dispatch, getState) => {
  const { staticCategories } = getState().geral;
  if (!staticCategories.includes(category)) {
    return;
  }
  let actionType;
  switch (category) {
    case 'Popular':
      actionType = TYPES.FETCH_POPULAR_MOVIES;
      break;
    case 'Top Rated':
      actionType = TYPES.FETCH_TOP_RATED_MOVIES;
      break;
    case 'Upcoming':
      actionType = TYPES.FETCH_UPCOMING_MOVIES;
      break;
    default:
      actionType = TYPES.FETCH_MOVIES_SUCCESS;
  }
  try {
    dispatch({ type: TYPES.FETCH_MOVIE_LOADING });

    const response = await getMoviesByCategory(category);

    dispatch({
      type: actionType,
      payload: response.data,
    });
  } catch (error) {
    console.error('Error fetching movies by category:', error);

    dispatch({
      type: TYPES.FETCH_MOVIES_FAILURE,
      payload: 'An error occurred while fetching movies.',
    });
  } finally {
    dispatch({ type: TYPES.FETCH_MOVIE_FINISHED });
  }
};


export const updateUserPreferences = (id, preferenceDTO) => async (dispatch) => {
  try {
    console.log('Sending preferences update request:', JSON.stringify(preferenceDTO, null, 2));

    const response = await fetch(`http://localhost:8082/api/user/${id}/preferences`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(preferenceDTO),
    });
    if (!response.ok) {
      let errorText = 'Unknown error occurred';
      try {
        const errorResponse = await response.text();
        try {
          const parsedErrorResponse = JSON.parse(errorResponse);
          errorText = parsedErrorResponse.message || 'Unknown error occurred';
        } catch (jsonParseError) {
          errorText = errorResponse;
        }
      } catch (error) {
        errorText = 'Failed to read error response';
      }
      console.error('Error response body:', errorText);
      throw new Error(`Failed to update preferences: ${errorText}`);
    }
    const result = await response.json();
    console.log('Response result:', JSON.stringify(result, null, 2));
    dispatch({
      type: TYPES.ADD_PREFERENCES_SUCCESS,
      payload: result,
    });
  } catch (error) {
    //console.error('Update preferences error:', error.message);
    dispatch({
      type: TYPES.ADD_PREFERENCES_FAILURE,
      payload: error.message,
    });
  }
};

// Get movies search
export const getMoviesSearch = (query) => async (dispatch) => {
  dispatch({ type: TYPES.FETCH_MOVIES_SEARCH_REQUEST });
  try {
    const data = await fetchMoviesSearch(query);
    dispatch({ type: TYPES.FETCH_MOVIES_SEARCH_SUCCESS, payload: data });
  } catch (error) {
    console.error('Error fetching movies:', error);
    dispatch({ type: TYPES.FETCH_MOVIES_SEARCH_FAILURE, payload: error.message });
  }
};


export const fetchUsersSearch = (query) => async (dispatch) => {
  dispatch({ type: TYPES.FETCH_USERS_SEARCH_REQUEST });
  try {
    const data = await fetchUserSearch(query);
    dispatch({ type: TYPES.FETCH_USERS_SEARCH_SUCCESS, payload: data });
  } catch (error) {
    console.error('Error fetching users search:', error.message);
    dispatch({ type: TYPES.FETCH_USERS_SEARCH_FAILURE, payload: error.message });
  }
};

export const clearMovies = () => ({
  type: TYPES.CLEAR_ERROR,
});

export const clearUsers = () => ({
  type: TYPES.CLEAR_ERROR,
});


// Get credits of single movie
export const getActorsForMovie = id => async dispatch => {
  try {
    const res = await getActorsByMovieId(id);
    dispatch({
      type: TYPES.FETCH_CAST,
      payload: res.data,
    });
  } catch (err) {
    dispatch({ type: TYPES.INSERT_ERROR, payload: err.response });
    history.push(process.env.PUBLIC_URL + '/error');
  }
};


export const getActor = (actorName) => async (dispatch) => {
  dispatch({ type: TYPES.FETCH_ACTOR_REQUEST });
  try {
    const response = await getActorByName(actorName);
    //console.log('Actor', response.data);
    dispatch({
      type: TYPES.FETCH_ACTOR_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: TYPES.FETCH_ACTOR_FAILURE,
      payload: error.message,
    });
  }
};

// Get recommended movies based on another
export const getRecommendations = (id) => async dispatch => {
  try {
    dispatch({ type: TYPES.FETCH_RECOMMENDATIONS_LOADING });
    const res = await recommendedMovies(id);
    console.log("API Response:", JSON.stringify(res, null, 2));
    dispatch({
      type: TYPES.FETCH_RECOMMENDATIONS,
      payload: res.data,
    });
  } catch (err) {
    dispatch({ type: TYPES.INSERT_ERROR, payload: err.response });
    history.push(process.env.PUBLIC_URL + '/error');
  } finally {
    dispatch({ type: TYPES.FETCH_RECOMMENDATIONS_FINISHED });
  }
};


export const loginSuccess = (user) => ({
  type: TYPES.LOGIN_SUCCESS,
  payload: user
});

export const logoutSuccess = () => ({
  type: TYPES.LOGOUT_SUCCESS
});

export const setUserLoading = () => ({
  type: TYPES.SET_USER_LOADING
});

export const logout = () => async (dispatch) => {
  dispatch({ type: TYPES.LOGOUT_REQUEST });

  try {
    await logoutUser();
    dispatch({ type: TYPES.LOGOUT_SUCCESS });
  } catch (error) {
    dispatch({ type: TYPES.LOGOUT_FAILURE, payload: error.message });
  }
};


//All feedback actions
export const addFeedbackForMovie = (userId, movieId, feedbackText, feedbackRating) => {
  return async (dispatch) => {
    dispatch({ type: TYPES.ADD_FEEDBACK_REQUEST });

    try {
      const response = await addFeedback(userId, movieId, feedbackText, feedbackRating);
      dispatch({ type: TYPES.ADD_FEEDBACK_SUCCESS,
        payload: response.data});
    } catch (error) {
      dispatch({ type: TYPES.ADD_FEEDBACK_FAILURE,
        payload: error.response });
    }
  };
};

export const getFeedbacksForMovie = (movieId) => {
  return async (dispatch) => {
    dispatch({ type: TYPES.GET_FEEDBACKS_REQUEST });

    try {
      const response = await getFeedbacks(movieId);
      dispatch({ type: TYPES.GET_FEEDBACKS_SUCCESS,
                payload: response.data});
    } catch (error) {
      dispatch({ type: TYPES.GET_FEEDBACKS_FAILURE,
        payload: error.response });
    }
  };
};

export const getFeedbacksForUser = (userId, movieId) => {
  return async (dispatch) => {
    dispatch({ type: TYPES.GET_USER_FEEDBACKS_REQUEST });

    try {
      const response = await getFeedbacksUser(userId, movieId);
      //console.log('Feedback', response.data);
      dispatch({ type: TYPES.GET_USER_FEEDBACKS_SUCCESS,
        payload: response.data});
    } catch (error) {
      dispatch({ type: TYPES.GET_USER_FEEDBACKS_FAILURE,
        payload: error.response });
    }
  };
};

export const updateFeedbackAction = (userId, movieId, feedbackText, feedbackRating) => async (dispatch) => {
  dispatch({ type: TYPES.UPDATE_FEEDBACK_REQUEST });
  try {
    const response = await updateFeedback(userId, movieId, feedbackText, feedbackRating);
    dispatch({ type: TYPES.UPDATE_FEEDBACK_SUCCESS,
      payload: response.data});
  } catch (error) {
    dispatch({ type: TYPES.UPDATE_FEEDBACK_FAILURE,
      payload: error.response });
  }
};


export const deleteFeedbackAction = (userId, movieId) => async (dispatch) => {
  dispatch({ type: TYPES.DELETE_FEEDBACK_REQUEST });
  try {
    const response = await deleteFeedback(userId, movieId);
    dispatch({ type: TYPES.DELETE_FEEDBACK_SUCCESS,
      payload: response.data});
  } catch (error) {
    dispatch({ type: TYPES.DELETE_FEEDBACK_FAILURE,
      payload: error.response });
  }
};

//All friends actions
export const addFriends = (userId, friendId) => async (dispatch) => {
  dispatch({ type: TYPES.ADD_FRIEND_REQUEST });
  try {
    const response = await addFriend(userId, friendId);
    dispatch({
      type: TYPES.ADD_FRIEND_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: TYPES.ADD_FRIEND_FAILURE,
      payload: error.message || 'Failed to add friend',
    });
  }
};

export const fetchAllFriends = (userId) => async (dispatch) => {
  dispatch({ type: TYPES.GET_ALL_FRIENDS_REQUEST });

  try {
    const response = await getAllFriends(userId);
    dispatch({
      type: TYPES.GET_ALL_FRIENDS_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: TYPES.GET_ALL_FRIENDS_FAILURE,
      payload: error.message || 'Failed to fetch friends list',
    });
  }
};

export const removeFriendofUser = (userId, friendId) => async (dispatch) => {
  dispatch({ type: TYPES.REMOVE_FRIEND_REQUEST });

  try {
    await removeFriend(userId, friendId);
    dispatch({
      type: TYPES.REMOVE_FRIEND_SUCCESS,
      payload: { userId, friendId },
    });
  } catch (error) {
    dispatch({
      type: TYPES.REMOVE_FRIEND_FAILURE,
      payload: error.message || 'Failed to remove friend',
    });
  }
};


export const isMutualFriends = (senderId, receiverId) => async (dispatch) => {
  dispatch({ type: TYPES.GET_MUTUAL_FRIENDS_REQUEST });

  try {
    const response = await getMutualFriends(senderId, receiverId);
    const areFriends = response.data;

    dispatch({
      type: TYPES.GET_MUTUAL_FRIENDS_SUCCESS,
      payload: areFriends,
    });
  } catch (error) {
    dispatch({
      type: TYPES.GET_MUTUAL_FRIENDS_FAILURE,
      payload: error.message,
    });
  }
};

// Get User details
export const getUser = id => async dispatch => {
  try {
    dispatch({ type: TYPES.FETCH_USER_LOADING });
    const res = await fetchUserById(id);
    dispatch({
      type: TYPES.FETCH_USER_SUCCESS,
      payload: res.data,
    });
  } catch (err) {
    dispatch({ type: TYPES.FETCH_USER_ERROR, payload: err.response });
    history.push(process.env.PUBLIC_URL + '/error');
  } finally {
    dispatch({ type: TYPES.FETCH_USER_FINISHED });
  }
};

export const updateUserProfile = (id, updatedUser) => {
  return async (dispatch) => {
    dispatch({ type: TYPES.UPDATE_PROFILE_REQUEST });
    try {
      const response = await updateProfile(id, updatedUser);
      dispatch({
        type: TYPES.UPDATE_PROFILE_SUCCESS,
        payload: response.data
      });
    } catch (error) {
      dispatch({
        type: TYPES.UPDATE_PROFILE_FAILURE,
        payload: error.message
      });
    }
  };
};


// Get movies from an actor
export const getMoviesForUser = (id) => async dispatch => {
  try {
    dispatch({ type: TYPES.FETCH_MOVIESPERSON_LOADING });
    const res = await fetchUserMovies(id);
    dispatch({
      type: TYPES.FETCH_MOVIESPERSON,
      payload: res.data,
    });
  } catch (err) {
    dispatch({ type: TYPES.INSERT_ERROR, payload: err.response });
    history.push(process.env.PUBLIC_URL + '/error');
  } finally {
    dispatch({ type: TYPES.FETCH_MOVIESPERSON_FINISHED });
  }
};


export const deleteActualUser = (id) => async dispatch => {
  try {
    dispatch({ type: TYPES.FETCH_USER_LOADING });
    await deleteUser(id);
    dispatch({ type: TYPES.CLEAR_USER_SUCCESS });
  } catch (err) {
    dispatch({ type: TYPES.INSERT_ERROR, payload: err.response });
    history.push(process.env.PUBLIC_URL + '/error');
  } finally {
    dispatch({ type: TYPES.FETCH_USER_FINISHED });
  }
};

export const addToWatchlist = (userId, movieId) => async (dispatch) => {
  dispatch({ type: TYPES.ADD_TO_WATCHLIST_REQUEST });

  try {
    const response = await watchlistMovies(userId, movieId);
    dispatch({
      type: TYPES.ADD_TO_WATCHLIST_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: TYPES.ADD_TO_WATCHLIST_FAILURE,
      payload: error.message || 'Failed to add movie to watchlist',
    });
  }
};

export const removeFromWatchlist = (userId, movieId) => async (dispatch) => {
  dispatch({ type: TYPES.DELETE_FROM_WATCHLIST_REQUEST });

  try {
    await deleteFromWatchlist(userId, movieId);
    dispatch({
      type: TYPES.DELETE_FROM_WATCHLIST_SUCCESS,
      payload: { userId, movieId },
    });
  } catch (error) {
    dispatch({
      type: TYPES.DELETE_FROM_WATCHLIST_FAILURE,
      payload: error.message || 'Failed to remove movie from watchlist',
    });
  }
};


// Action for fetching messages
export const fetchMessagesFromSender = (senderId, receiverId) => async (dispatch) => {
  dispatch({ type: TYPES.FETCH_MESSAGES_FROM_SENDER_REQUEST });
  try {
    const response = await getMessages(senderId, receiverId);
    //console.log('Full response:', response);
    dispatch({ type: TYPES.FETCH_MESSAGES_FROM_SENDER_SUCCESS, payload: response });
    return Promise.resolve(response);
  } catch (error) {
    console.error('Error fetching messages:', error.message);
    dispatch({ type: TYPES.FETCH_MESSAGES_FROM_SENDER_FAILURE, payload: error.message });
    return Promise.reject(error);
  }
};


export const fetchMessagesFromReceiver = (receiverId, senderId) => async (dispatch) => {
  dispatch({ type: TYPES.FETCH_MESSAGES_FROM_RECEIVER_REQUEST });
  try {
    const response = await getMessages(receiverId, senderId);
    dispatch({ type: TYPES.FETCH_MESSAGES_FROM_RECEIVER_SUCCESS, payload: response });
    return Promise.resolve(response);
  } catch (error) {
    console.error('Error fetching messages:', error.message);
    dispatch({ type: TYPES.FETCH_MESSAGES_FROM_RECEIVER_FAILURE, payload: error.message });
    return Promise.reject(error);
  }
};


export const addMessage = (message) => ({
  type: TYPES.ADD_MESSAGE,
  payload: message,
});

export const setMessages = (message, senderId, receiverId) => (dispatch, getState) => {
  const { usermessage } = getState();
  const updatedMessagesFromSender = [...usermessage.messagesFromSender];
  const updatedMessagesFromReceiver = [...usermessage.messagesFromReceiver];

  if (message.senderId === senderId) {
    updatedMessagesFromSender.push(message);
  } else {
    updatedMessagesFromReceiver.push(message);
  }

  dispatch({
    type: 'SET_MESSAGES',
    payload: { updatedMessagesFromSender, updatedMessagesFromReceiver }
  });
};

export const clearError = () => ({ type: TYPES.CLEAR_ERROR });
