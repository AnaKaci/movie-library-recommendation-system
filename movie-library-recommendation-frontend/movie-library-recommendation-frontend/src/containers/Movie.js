import React, { useState, useEffect, useCallback } from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import { Helmet } from 'react-helmet';
import styled from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import { fetchMovieById } from '../api/movieService';
import {
  addToWatchlist,
  getActorsForMovie,
  getFeedbacksForMovie,
  getFeedbacksForUser,
  removeFromWatchlist
} from '../actions';
import Loader from '../components/Loader';
import NotFound from '../components/NotFound';
import Cast from '../components/Cast';
import Button from '../components/Button';
import MovieModal from '../components/MovieModal';
import Rating from '../components/Rating';
import errorPhoto from '../svg/error.svg';
import FeedbackList from "../components/FeedbackList";
import {faChevronLeft, faDotCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import Header from "../components/Header";
import play from '../svg/play.svg';
import {MDBCardImage} from "mdb-react-ui-kit";
import Feedback from "./Feedback";


const Wrapper = styled.div`
  display: flex;
  width: 100%;
  flex-direction: column;
`;

const MovieWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  max-width: 120rem;
  margin: 0 auto;
  margin-bottom: 7rem;
  transition: all 600ms cubic-bezier(0.215, 0.61, 0.355, 1);

  @media ${(props) => props.theme.mediaQueries.medium} {
    flex-direction: column;
    margin-bottom: 5rem;
  }
`;

const LinksWrapper = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 3rem;
  flex-wrap: wrap;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
  display: flex;
  align-items: center;
  font-size: 1.1rem;
  font-weight: 700;
  color: var(--color-primary-light);
  text-transform: uppercase;
  padding: 0.5rem 0;
  transition: all 300ms cubic-bezier(0.075, 0.82, 0.165, 1);

  &:hover {
    transform: translateY(-3px);
  }

  &:active {
    transform: translateY(2px);
  }
`;

const MovieDetails = styled.div`
  width: 100%;
  max-width: 60%;
  padding: 4rem;

  @media ${(props) => props.theme.mediaQueries.medium} {
    max-width: 100%;
  }
`;

const ImageWrapper = styled.div`
  width: 100%;
  max-width: 40%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 4rem;

  @media ${(props) => props.theme.mediaQueries.medium} {
    max-width: 60%;
  }
`;

const MovieImg = styled.img.attrs(props => ({
  src: props.src || errorPhoto,
}))`
  max-height: 100%;
  height: ${(props) => (props.hasError ? '25rem' : 'auto')};
  object-fit: ${(props) => (props.hasError ? 'contain' : 'cover')};
  max-width: 100%;
  border-radius: 0.8rem;
`;

const HeaderWrapper = styled.div`
  margin-bottom: 2rem;
`;

const Heading = styled.h3`
  color: var(--color-primary-dark);
  font-weight: 700;
  font-size: 12.5px;
  text-transform: uppercase;
  margin-bottom: 1rem;
`;

const DetailsWrapper = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 3rem;
`;

const RatingsWrapper = styled.div`
  display: flex;
  align-items: center;
  margin-right: auto;
`;

const RatingNumber = styled.p`
  font-size: 1.3rem;
  font-weight: 700;
  color: var(--color-primary);
`;

const Info = styled.div`
  font-weight: 700;
  text-transform: uppercase;
  color: var(--color-primary-lighter);
`;

const Text = styled.p`
  font-size: 1.4rem;
  color: var(--link-color);
  font-weight: 500;
  margin-bottom: 3rem;
`;

const ButtonsWrapper = styled.div`
  display: flex;
  align-items: center;

  @media ${(props) => props.theme.mediaQueries.small} {
    flex-direction: column;
    align-items: flex-start;
  }
`;

const LeftButtons = styled.div`
  margin-right: auto;
  display: flex;

  & > *:not(:last-child) {
    margin-right: 2rem;
  }
`;

// Movie Component
const Movie = ({ isLoggedIn, userId, userWatchlist}) => {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const [modalOpened, setModalOpened] = useState(false);
  const [isAddingToWatchlist, setAddingToWatchlist] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isInWatchlist, setIsInWatchlist] = useState(false);


  const dispatch = useDispatch();
  const cast = useSelector(state => state.movie.cast || []);
  const feedbacks = useSelector(state => state.feedbacks.feedback || []);
  const userFeedback = useSelector(state => state.feedbacks.userFeedback);
  const navigate = useNavigate();


  const handleBackNavigation = () => {
    navigate(`/discover`);
  };

  const fetchMovie = useCallback(async () => {
    try {
      setLoading(true);
      const response = await fetchMovieById(id);
      //console.log(response.data);
      setMovie(response.data);
      dispatch(getActorsForMovie(id));
      dispatch(getFeedbacksForMovie(id));
    } catch (error) {
      setError(true);
    } finally {
      setLoading(false);
    }
  }, [id, dispatch]);


  useEffect(() => {
    if (userWatchlist && userWatchlist.length > 0) {
      const movieInWatchlist = userWatchlist.some(movie => movie.movieId === parseInt(id, 10));
      setIsInWatchlist(movieInWatchlist);
    }
  }, [userWatchlist, id]);

  function renderGenres(genres) {
    return genres.map((genre) => (
        <StyledLink
            to={`${process.env.PUBLIC_URL}/genres/${genre}`}
            key={genre}
            style={{ marginRight: '10px' }}
        >
          <FontAwesomeIcon
              icon={faDotCircle}
              size="1x"
              style={{ marginRight: '5px' }}
          />
          {genre}
        </StyledLink>
    ));
  }


  useEffect(() => {
    fetchMovie();
  }, [fetchMovie]);

  const handleWatchlistToggle = async () => {
    if (isLoggedIn) {
      try {
        setAddingToWatchlist(true);

        if (isInWatchlist) {
          await dispatch(removeFromWatchlist(userId, id));
          setIsInWatchlist(false);
          alert('Movie removed from your watchlist!');
        } else {
          await dispatch(addToWatchlist(userId, id));
          setIsInWatchlist(true);
          alert('Movie added to your watchlist!');
        }
      } catch (error) {
        alert('Failed to update watchlist. Please try again later.');
      } finally {
        setAddingToWatchlist(false);
      }
    } else {
      alert('Please log in to update your watchlist.');
    }
  };


  const fetchUserFeedback = useCallback(async () => {
    if (userId) {
      try {
        await dispatch(getFeedbacksForUser(userId, id));
      } catch (error) {
        console.error('Error fetching user feedback:', error);
      }
    }
  }, [id, userId, dispatch]);

  useEffect(() => {
    fetchUserFeedback();
  }, [fetchUserFeedback]);



  if (loading) {
    return <Loader />;
  }

  if (error) {
    return <NotFound />;
  }

  if (!movie) {
    return <NotFound />;
  }
  const formattedRating = (movie.averageRating).toFixed(2);
  //console.log('Feedback of user', userFeedback);

  return (
      <>
        <Helmet>
          <title>{movie.title}</title>
        </Helmet>
        <Wrapper>
          <MovieWrapper>
            <ImageWrapper>
              <MovieImg
                  src={errorPhoto}
                  alt={movie.title}
              />
            </ImageWrapper>
            <MovieDetails>
              <HeaderWrapper>
                <Header subtitle={''} title={movie.title}></Header>
              </HeaderWrapper>
              <DetailsWrapper>
                <RatingsWrapper>
                  <Rating number={formattedRating / 2} />
                  <RatingNumber>{formattedRating}</RatingNumber>
                </RatingsWrapper>
                <Info>{new Date(movie.releaseDate).getFullYear()}</Info>
              </DetailsWrapper>
              <Heading>{'Genres'}</Heading>
              <LinksWrapper> {renderGenres(movie.genreNames)} </LinksWrapper>
              <Heading>{'Description'}</Heading>
              <Text>{movie.description}</Text>
              <Heading>{'Cast'}</Heading>
              <Cast castData={cast}/>
              <ButtonsWrapper>
                <LeftButtons>
                  <Button
                      title="Watch Trailer"
                      solid={false}
                      icon={['fas', 'play']}
                      left={true}
                      onClick={() => window.open(movie.trailer, '_blank')}
                  />
                  <Button
                      title="Play"
                      solid={true}
                      icon={['fas', 'play']}
                      left={false}
                      onClick={() => setModalOpened(true)}
                  />
                  <Button
                      title={isInWatchlist ? "Remove from Watchlist" : "Add to Watchlist"}
                      solid={true}
                      icon={['fas', isInWatchlist ? 'bookmark' : 'bookmark']}
                      left={false}
                      onClick={handleWatchlistToggle}
                      disabled={isAddingToWatchlist}
                  />
                  {isLoggedIn && (!userFeedback || userFeedback.length === 0) && (
                      <Button
                          title="Leave a Feedback"
                          solid={true}
                          icon={['fas', 'comment']}
                          left={false}
                          onClick={() => setIsModalOpen(true)}
                      />
                  )}
                  <Button
                      title="Go Back"
                      solid={false}
                      icon={faChevronLeft}
                      left={false}
                      onClick={handleBackNavigation}
                  />

                </LeftButtons>
              </ButtonsWrapper>
              <Feedback userId={userId} isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} movieId={id} />
            </MovieDetails>
          </MovieWrapper>
          <div>
            <h2 style={{marginBottom: '1rem'}}>
              <strong>Reviews</strong></h2>
            <FeedbackList feedbacks={feedbacks} isLoggedIn={isLoggedIn} userId={userId} movieId={movie.movieId}/>
          </div>
          {modalOpened && (
              <MovieModal onClose={() => setModalOpened(false)}>
                <MDBCardImage
                    src={play}
                    alt="play video"
                    className='rounded-start'
                    style={{ width: '100%', height: 'auto', margin: '1rem 0.5rem 1rem 0.1rem', textAlign: 'center'}} // Adjusted styles
                />
              </MovieModal>
          )}
        </Wrapper>
      </>
  );
};

export default Movie;
