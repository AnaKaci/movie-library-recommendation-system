import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import LazyLoad from 'react-lazyload';

import NothingSvg from '../svg/nothing.svg';
import Rating from '../components/Rating';
import Loading from '../components/Loading';

const getTextColor = (theme) => theme.colors.text === '#000000' ? '#ffffff' : '#000000';

const MovieWrapper = styled(Link)`
  display: flex;
  flex-direction: column;
  text-decoration: none;
  background-color: transparent;
  border-radius: 0.8rem;
  transition: all 300ms cubic-bezier(0.215, 0.61, 0.355, 1);
  position: relative;

  &:hover {
    transform: scale(1.03);

    ::after {
      transform: scaleY(1);
      opacity: 1;
    }
  }
  &::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    border-radius: 0.8rem;
    transform: scaleY(0);
    transform-origin: top;
    opacity: 0;
    background-color: var(--color-primary);
    z-index: -99;
    box-shadow: 0rem 2rem 5rem var(--shadow-color-dark);
    transition: all 100ms cubic-bezier(0.215, 0.61, 0.355, 1);
  }
`;

const MovieImg = styled.img`
  width: 100%;
  height: 38rem;
  object-fit: ${(props) => (props.$error ? 'contain' : 'cover')};
  border-radius: 0.8rem;
  padding: ${(props) => (props.$error ? '2rem' : '0')};
  box-shadow: 0rem 2rem 5rem var(--shadow-color);
  transition: all 100ms cubic-bezier(0.645, 0.045, 0.355, 1);

  ${MovieWrapper}:hover & {
    border-radius: 0.8rem 0.8rem 0 0;
    box-shadow: none;
  }

  @media ${(props) => props.theme.mediaQueries.smaller} {
    height: 28rem;
  }
`;

const ImgLoading = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  min-height: 300px;
  border-radius: 0.8rem;
  box-shadow: 0rem 2rem 5rem var(--shadow-color);
  transition: all 100ms cubic-bezier(0.645, 0.045, 0.355, 1);
`;

const Title = styled.h2`
  text-align: center;
  font-size: 1.3rem;
  font-weight: 400;
  color: var(--color-primary-light);
  margin-bottom: 1rem;
  line-height: 1.4;
  transition: color 300ms cubic-bezier(0.645, 0.045, 0.355, 1);

  ${MovieWrapper}:hover & {
    color: var(--text-color);
  }
`;

const DetailsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 3rem;

  @media ${(props) => props.theme.mediaQueries.smaller} {
    padding: 1.5rem 1.5rem;
  }
`;

const RatingsWrapper = styled.div`
  display: flex;
  position: relative;
  align-items: center;
  margin-bottom: 0.5rem;
  color: var(--color-primary);

  ${MovieWrapper}:hover & {
    color: var(--color-primary-lighter);
  }
`;

const Tooltip = styled.span`
  visibility: hidden;
  opacity: 0;
  width: 120px;
  font-weight: 500;
  font-size: 1.1rem;
  background-color: var(--color-primary-light);
  color: ${({ theme }) => getTextColor(theme)};
  text-align: center;
  border-radius: 6px;
  padding: 1rem;
  position: absolute;
  z-index: 999;
  bottom: 150%;
  left: 50%;
  margin-left: -60px;
  transition: all 200ms cubic-bezier(0.645, 0.045, 0.355, 1);

  &::after {
    content: '';
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: var(--color-primary-light) transparent transparent transparent;
    transition: all 200ms cubic-bezier(0.645, 0.045, 0.355, 1);
  }

  ${RatingsWrapper}:hover & {
    visibility: visible;
    opacity: 1;
  }
`;

const MovieItem = ({ movie, baseUrl }) => {
  const [loaded, setLoaded] = useState(false);
  const [error, setError] = useState(false);
  const formattedRating = (movie.averageRating).toFixed(2);

  useEffect(() => {
    return () => setLoaded(false);
  }, []);

  return (
      <LazyLoad height={200} offset={200}>
        <MovieWrapper to={`/movie/${movie.movieId}`}>
          {!loaded && (
              <ImgLoading>
                <Loading />
              </ImgLoading>
          )}
          <MovieImg
              style={{ display: loaded ? 'block' : 'none' }}
              src={`${baseUrl}${movie.poster}`}
              alt={movie.title}
              onLoad={() => setLoaded(true)}
              onError={(e) => {
                setError(true);
                if (e.target.src !== NothingSvg) {
                  e.target.src = NothingSvg;
                }
              }}
              $error={error}
          />
          <DetailsWrapper>
            <Title>{movie.title}</Title>
            <RatingsWrapper>
              <Rating number={formattedRating / 2} />
              <Tooltip>{movie.averageRating} average rating</Tooltip>
            </RatingsWrapper>
          </DetailsWrapper>
        </MovieWrapper>
      </LazyLoad>
  );
};

MovieItem.propTypes = {
  movie: PropTypes.shape({
    movieId: PropTypes.number.isRequired,
    title: PropTypes.string.isRequired,
    poster: PropTypes.string.isRequired,
    averageRating: PropTypes.number.isRequired,
  }).isRequired,
  baseUrl: PropTypes.string.isRequired,
};

export default MovieItem;
