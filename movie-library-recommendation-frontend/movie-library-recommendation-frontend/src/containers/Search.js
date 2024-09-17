import React, { useEffect } from 'react';
import { Helmet } from 'react-helmet';
import { connect } from 'react-redux';
import { useParams } from 'react-router-dom';
import Header from '../components/Header';
import NotFound from '../components/NotFound';
import styled from 'styled-components';
import { animateScroll as scroll } from 'react-scroll';
import { getMoviesSearch, clearMovies, fetchUsersSearch, clearUsers } from '../actions';
import MoviesList from '../components/MoviesList';
import UsersList from '../components/UsersList';
import Loader from '../components/Loader';

const Wrapper = styled.div`
    display: flex;
    width: 100%;
    flex-direction: column;
`;

const Search = ({
                    geral,
                    getMoviesSearch,
                    fetchUsersSearch,
                    clearMovies,
                    clearUsers,
                    movies,
                    users,
                    userId,
                    isLoggedIn
                }) => {
    const { query } = useParams();

    const secure_base_url = geral.base?.images?.secure_base_url || '';

    function useFetchSearch(query, getMoviesSearch, fetchUsersSearch, clearMovies, clearUsers) {
        useEffect(() => {
            scroll.scrollToTop({
                smooth: true,
            });
            if (query) {
                getMoviesSearch(query);
                fetchUsersSearch(query);
            }
            return () => {
                clearMovies();
                clearUsers();
            };
        }, [query, getMoviesSearch, fetchUsersSearch, clearMovies, clearUsers]);
    }

    useFetchSearch(query, getMoviesSearch, fetchUsersSearch, clearMovies, clearUsers);

    if (movies.loading || users.loading) {
        return <Loader />;
    }

    else if (movies.results.length === 0 && users.users.length === 0) {
        return (
            <NotFound
                title="Sorry!"
                subtitle={`There were no results for "${query}"...`}
            />
        );
    }

    else {
        return (
            <Wrapper>
                <Helmet>
                    <title>{`${query} - search results`}</title>
                </Helmet>
                <Header title={query} subtitle="search results movies" />
                {movies.results.length > 0 && (
                    <MoviesList movies={movies.results} baseUrl={secure_base_url} />
                )}
                <Header title={query} subtitle="search results users" />
                {users.users.length > 0 && (
                    <UsersList users={users.users} baseUrl={secure_base_url} userId={userId} isLoggedIn={isLoggedIn}/>
                )}
            </Wrapper>
        );
    }
};

// Map State to Component Props
const mapStateToProps = ({ geral, movies, users }) => {
    return { geral, movies, users };
};

export default connect(
    mapStateToProps,
    { getMoviesSearch, fetchUsersSearch, clearMovies, clearUsers }
)(Search);
