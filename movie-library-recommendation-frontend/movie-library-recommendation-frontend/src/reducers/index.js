import { combineReducers } from 'redux';
import geralReducer from './geralReducer';
import moviesReducer from './moviesReducer';
import movieReducer from './movieReducer';
import userReducer from './userReducer';
import recommendationsReducer from './recommendationsReducer';
import moviesForUserReducer from './moviesForUserReducer';
import errorsReducer from './errorsReducer';
import watchlistReducer from './watchlistReducer';
import preferenceReducer from './preferenceReducer';
import feedbackReducer from './feedbackReducer';
import friendReducer from "./friendReducer";
import usersReducer from "./usersReducer";
import messageReducer from "./messageReducer";

export default combineReducers({
  geral: geralReducer,
  friend: friendReducer,
  usermessage: messageReducer,
  users: usersReducer,
  preference: preferenceReducer,
  watchlist: watchlistReducer,
  feedbacks: feedbackReducer,
  movies: moviesReducer,
  movie: movieReducer,
  user: userReducer,
  recommendation: recommendationsReducer,
  moviesUser: moviesForUserReducer,
  errors: errorsReducer,
});
