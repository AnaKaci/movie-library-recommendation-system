import React, { useEffect } from 'react';
import { Helmet } from 'react-helmet';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import Header from '../components/Header';
import { setSelectedMenu, getMoviesForUser, clearMovies, getRecommendations } from '../actions';
import MoviesList from '../components/MoviesList';
import Loader from '../components/Loader';

const Wrapper = styled.div`
    display: flex;
    width: 100%;
    flex-direction: column;
`;

const Section = styled.div`
    margin: 20px 0;
`;

const Watchlist = () => {
    const dispatch = useDispatch();
    const { userId } = useParams();

    const geral = useSelector((state) => state.geral);
    const baseUrl = typeof geral.base === 'string' ? geral.base : '';

    const movies = useSelector((state) => state.moviesUser.watchlists) || [];
    const recommendations = useSelector((state) => state.recommendation.movies) || [];
    const isLoading = useSelector((state) => state.movies.loading);
    const hasError = useSelector((state) => state.movies.error);

    useEffect(() => {
        if (userId) {
            dispatch(setSelectedMenu('Watchlist'));
            dispatch(getMoviesForUser(userId));
            dispatch(getRecommendations(userId));
        }

        return () => {
            dispatch(setSelectedMenu(''));
            dispatch(clearMovies());
        };
    }, [dispatch, userId]);

    useEffect(() => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }, [movies]);

    if (isLoading) {
        return <Loader />;
    }

    if (hasError) {
        return <div>There was an error fetching the watchlist!</div>;
    }

    return (
        <Wrapper>
            <Helmet>
                <title>My Watchlist</title>
            </Helmet>
            <Header title="My Watchlist" subtitle="movies" />
            <MoviesList baseUrl={baseUrl} movies={movies} />

            <Section>
                <Header title="Recommended for You"  subtitle={'Movies for you'}/>
                <MoviesList baseUrl={baseUrl} movies={recommendations} />
            </Section>
        </Wrapper>
    );
};

export default Watchlist;
