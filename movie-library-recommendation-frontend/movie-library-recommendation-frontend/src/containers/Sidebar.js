import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import StickyBox from 'react-sticky-box';
import Logo from '../components/Logo';
import MenuItem from '../components/MenuItem';
import { fetchGenres } from '../api/apiService';

const useGenres = () => {
    const [genres, setGenres] = useState([]);

    useEffect(() => {
        const getGenres = async () => {
            try {
                const { data } = await fetchGenres();
                setGenres(data);
            } catch (error) {
                console.error('Failed to fetch genres:', error);
            }
        };

        getGenres();
    }, []);

    return genres;
};

const SidebarWrapper = styled.div`
    display: flex;
    flex-direction: column;
    width: ${(props) => (props.open ? '250px' : '0')};
    padding: ${(props) => (props.open ? '20px' : '0')};
    margin-top: 4rem;
    color: var(--color-primary-dark);
    border-right: ${(props) => (props.open ? '1px solid var(--border-color)' : 'none')};
    overflow: hidden;
    transition: width 0.3s ease-in-out, padding 0.3s ease-in-out;
`;

const Heading = styled.h2`
    font-weight: 700;
    font-size: 1.1rem;
    text-transform: uppercase;
    letter-spacing: -0.5px;
    margin: 0 0 1rem 1rem;
    &:not(:first-child) {
        margin-top: 4rem;
    }
`;

const LinkWrap = styled(Link)`
    text-decoration: none;
    display: block;
    outline: none;
    margin-bottom: 0.5rem;
`;

const Sidebar = ({ open, staticCategories = [], selectedCategory, onCategorySelect, isLoggedIn, userId }) => {
    const genres = useGenres();

    function renderStatic(categories, selectedCategory, onCategorySelect) {
        return categories.map((category, i) => (
            <LinkWrap
                to={`http://localhost:3000/discover/${category}`}
                key={i}
                onClick={() => onCategorySelect(category)}
            >
                <MenuItem
                    title={category}
                    selected={category === selectedCategory}
                />
            </LinkWrap>
        ));
    }

    function renderGenres(genres, selectedCategory, onCategorySelect) {
        return genres.map((genre) => (
            <LinkWrap
                to={`http://localhost:3000/genres/${genre.genreName}`}
                key={genre.genreName}
                onClick={() => onCategorySelect(genre.genreName)}
            >
                <MenuItem
                    title={genre.genreName}
                    selected={genre.genreName === selectedCategory}
                />
            </LinkWrap>
        ));
    }

    return (
        <SidebarWrapper open={open}>
            <StickyBox>
                <Logo />
                <Heading>Discover</Heading>
                {staticCategories.length > 0 && renderStatic(staticCategories, selectedCategory, onCategorySelect)}
                <Heading>Genres</Heading>
                {genres.length > 0 && renderGenres(genres, selectedCategory, onCategorySelect)}

                {isLoggedIn && (
                    <>
                        <Heading>My Watchlist</Heading>
                        <LinkWrap
                            to={`/watchlist/${userId}`}
                            onClick={() => onCategorySelect('Watchlist')}
                        >
                            <MenuItem
                                title="Watchlist"
                                selected={'Watchlist' === selectedCategory}
                            />
                        </LinkWrap>
                    </>
                )}
            </StickyBox>
        </SidebarWrapper>
    );
};

export default Sidebar;
