import React, { useEffect } from 'react';
import { Helmet } from 'react-helmet';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import Header from '../components/Header';
import { setSelectedMenu, getMoviesDiscover, clearMovies, fetchAllMovies } from '../actions';
import MoviesList from '../components/MoviesList';
import Loader from '../components/Loader';

const Wrapper = styled.div`
    display: flex;
    width: 100%;
    flex-direction: column;
`;

const Discover = () => {
    const dispatch = useDispatch();
    const geral = useSelector((state) => state.geral);

    const baseUrl = typeof geral.base === 'string' ? geral.base : '';
    const categoryName = useParams().name || '';

    const movies = useSelector((state) => state.movies.results) || [];
    const isLoading = useSelector((state) => state.movies.loading);
    const hasError = useSelector((state) => state.movies.error);

    useEffect(() => {
        if (categoryName) {
            if (geral.staticCategories.includes(categoryName)) {
                dispatch(setSelectedMenu(categoryName));
                dispatch(getMoviesDiscover(categoryName));
            } else {
                dispatch(setSelectedMenu('All'));
                dispatch(fetchAllMovies());
            }
        } else {
            dispatch(setSelectedMenu('All'));
            dispatch(fetchAllMovies());
        }
        return () => {
            dispatch(setSelectedMenu(''));
            dispatch(clearMovies());
        };
    }, [dispatch, categoryName, geral.staticCategories]);

    useEffect(() => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }, [movies]);

    if (isLoading) {
        return <Loader />;
    }

    if (hasError) {
        return <div>There was an error fetching the movies!</div>;
    }

    return (
        <Wrapper>
            <Helmet>
                <title>{`${geral.selected || 'All'} Movies`}</title>
            </Helmet>
            <Header title={geral.selected || 'All Movies'} subtitle="movies" />
            <MoviesList baseUrl={baseUrl} movies={movies} />
        </Wrapper>
    );
};

export default Discover;
