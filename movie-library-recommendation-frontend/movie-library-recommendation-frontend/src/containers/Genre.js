import React, { useEffect, useCallback } from 'react';
import { Helmet } from 'react-helmet';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { animateScroll as scroll } from 'react-scroll';
import { useParams } from 'react-router-dom';

import { setSelectedMenu, getMoviesByGenreName, clearMovies } from '../actions';
import Header from '../components/Header';
import MoviesList from '../components/MoviesList';
import Loader from '../components/Loader';

const Wrapper = styled.div`
    display: flex;
    width: 100%;
    flex-direction: column;
`;

const Genre = ({
                   geral,
                   setSelectedMenu,
                   getMoviesByGenreName,
                   clearMovies,
                   movies = {},
               }) => {
    const { genreName } = useParams();
    const secure_base_url = typeof geral.base === 'string' ? geral.base : '';

    const memoizedSetSelectedMenu = useCallback(
        (menu) => {
            const isGenre = geral.genres.some(genre => genre.genreName === menu);
            const isStaticCategory = geral.staticCategories.includes(menu);

            if (isGenre && !isStaticCategory) {
                setSelectedMenu(menu);
            }
        },
        [setSelectedMenu, geral.genres, geral.staticCategories]
    );

    useEffect(() => {
        memoizedSetSelectedMenu(genreName || '');
        return () => {
            memoizedSetSelectedMenu('');
        };
    }, [genreName, memoizedSetSelectedMenu]);

    useEffect(() => {
        const isGenre = geral.genres.some(genre => genre.genreName === genreName);
        const isStaticCategory = geral.staticCategories.includes(genreName);

        if (isGenre && !isStaticCategory) {
            scroll.scrollToTop({ smooth: true });
            getMoviesByGenreName(genreName);
        }
        return () => {
            clearMovies();
        };
    }, [genreName, geral.selected, getMoviesByGenreName, clearMovies, geral.genres, geral.staticCategories]);

    if (movies.loading) {
        return <Loader />;
    }

    if (movies.error) {
        return <div>There was an error fetching the movies!</div>;
    }

    return (
        <Wrapper>
            <Helmet>
                <title>{`${geral.selected} Movies`}</title>
            </Helmet>
            <Header title={geral.selected} subtitle="movies" />
            <MoviesList
                movies={movies.results || []}
                baseUrl={secure_base_url}
            />
        </Wrapper>
    );
};

const mapStateToProps = (state) => ({
    geral: state.geral,
    movies: state.movies || {},
});

export default connect(
    mapStateToProps,
    { setSelectedMenu, getMoviesByGenreName, clearMovies }
)(Genre);
