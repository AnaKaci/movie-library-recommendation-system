import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';
import MovieItem from './MovieItem';

const MoviesGrid = styled.div`
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
    padding: 20px;
`;


const MoviesList = ({ baseUrl, movies }) => {

    if (!Array.isArray(movies)) {
        return <div>Error: Movies data is not an array.</div>;
    }

    if (!movies || movies.length === 0) {
        return <div>No movies available</div>;
    }

    return (
        <div>
                <MoviesGrid>
                    {movies.map((movie) => (
                        <MovieItem
                            key={movie.movieId}
                            movie={movie}
                            baseUrl={baseUrl}
                        />
                    ))}
                </MoviesGrid>
        </div>
);
};

MoviesList.propTypes = {
    baseUrl: PropTypes.string.isRequired,
    movies: PropTypes.arrayOf(PropTypes.shape({
        movieId: PropTypes.number.isRequired,
        title: PropTypes.string.isRequired,
        poster: PropTypes.string.isRequired,
        averageRating: PropTypes.number,
    })).isRequired,
};

export default MoviesList;
