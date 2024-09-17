import React, { Suspense, useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { init, logoutSuccess, setUserLoading, loginSuccess } from '../actions';
import Sidebar from './Sidebar';
import styled from 'styled-components';
import Loader from '../components/Loader';
import Discover from './Discover';
import Genre from './Genre';
import Search from './Search';
import Movie from './Movie';
import User from './User';
import ShowError from './ShowError';
import NotFound from '../components/NotFound';
import SignUp from './SignUp';
import Menu from './Menu';
import Login from "./Login";
import Watchlist from "./Watchlist";
import ChatClient from './ChatClient';
import { ThemeProvider } from 'styled-components';
import { lightTheme, darkTheme } from '../utils/theme';
import GlobalStyle from '../utils/globals';

const LayoutWrapper = styled.div`
    display: flex;
    margin-top: 4rem; 
`;

const ContentWrapper = styled.div`
    padding: 20px;
    display: flex;
    flex-direction: column;
    width: 100%;
`;

const App = () => {
    const dispatch = useDispatch();
    const staticCategories = ['Popular', 'Top Rated', 'Upcoming'];
    const [selectedCategory, setSelectedCategory] = useState('');
    const [sidebarOpen, setSidebarOpen] = useState(false);
    const [isDarkTheme, setIsDarkTheme] = useState(false);

    const user = useSelector((state) => state.user.loggedInUser.user);
    const userId = user?.userId;
    const isLoggedIn = useSelector((state) => state.user.loggedInUser.isLoggedIn);
    const isLoading = useSelector((state) => state.geral?.loading);
    const watchlists = useSelector((state) => state.moviesUser.watchlists);

    useEffect(() => {
        dispatch(setUserLoading());
        dispatch(init());

        const userData = JSON.parse(localStorage.getItem('user'));
        if (userData) {
            dispatch(loginSuccess(userData));
        }
    }, [dispatch]);

    const handleCategorySelect = (category) => {
        setSelectedCategory(category);
    };

    const toggleSidebar = () => {
        setSidebarOpen(!sidebarOpen);
    };

    const handleLogout = () => {
        dispatch(logoutSuccess());
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        window.location.href = '/';
    };

    const toggleTheme = () => {
        setIsDarkTheme(prevTheme => !prevTheme);
    };

    const theme = isDarkTheme ? darkTheme : lightTheme;

    return (
        <ThemeProvider theme={theme}>
            <GlobalStyle />
            {isLoading ? (
                <ContentWrapper>
                    <Loader />
                </ContentWrapper>
            ) : (
                <Router>
                    <Menu
                        toggleSidebar={toggleSidebar}
                        isLoggedIn={isLoggedIn}
                        userId={userId}
                        onLogout={handleLogout}
                        toggleTheme={toggleTheme}
                    />
                    <LayoutWrapper>
                        <Sidebar
                            open={sidebarOpen}
                            staticCategories={staticCategories}
                            selectedCategory={selectedCategory}
                            onCategorySelect={handleCategorySelect}
                            isCategorySelected={selectedCategory}
                            isLoggedIn={isLoggedIn}
                            userId={userId}
                        />
                        <ContentWrapper>
                            <Suspense fallback={<Loader />}>
                                <Routes>
                                    <Route path="/" element={<Navigate to="/discover" />} />
                                    <Route path="/login" element={<Login />} />
                                    <Route path="/genres/:genreName" element={<Genre />} />
                                    <Route path="/discover" element={<Discover />} />
                                    <Route path="/discover/:name" element={<Discover />} />
                                    <Route path="/signup" element={<SignUp />} />
                                    <Route path="/search/:query" element={<Search userId={userId} isLoggedIn={isLoggedIn}/>} />
                                    <Route path="/watchlist/:userId" element={<Watchlist />} />
                                    <Route path="/movie/:id" element={<Movie isLoggedIn={isLoggedIn} userId={userId} userWatchlist={watchlists} />} />
                                    <Route path="/user/:id" element={<User />} />
                                    <Route path="/404" element={<NotFound title="Upps!" subtitle="This doesn't exist..." />} />
                                    <Route path="/error" element={<ShowError />} />
                                    <Route path="*" element={<NotFound title="Upps!" subtitle="This doesn't exist..." />} />
                                </Routes>
                            </Suspense>
                            {isLoggedIn && <ChatClient senderId={userId} />}
                        </ContentWrapper>
                    </LayoutWrapper>
                </Router>
            )}
        </ThemeProvider>
    );
};

export default App;
