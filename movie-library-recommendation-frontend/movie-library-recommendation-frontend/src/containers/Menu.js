import React from 'react';
import styled from 'styled-components';
import { FiMenu, FiSun, FiMoon } from 'react-icons/fi';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import SearchBar from '../components/SearchBar';
import { logout } from '../actions';

const crimsonRed = '#ba0f17';
const forestGreen = '#0b643c';
const moonColorLight = '#cbcbcb';
const sunColorLight = '#fff';
const sunColorDark = '#cbcbcb';

const HeaderWrapper = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 5px 10px;
    background-color: ${(props) => props.theme.background};
    color: ${(props) => props.theme.color};
    position: fixed;
    width: 100%;
    top: 0;
    left: 0;
    z-index: 1000;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const MenuButton = styled.button`
    background: none;
    border: none;
    color: #7e7e7e;
    font-size: 2rem;
    cursor: pointer;
    padding: 0;
    margin: 0;
    display: flex;
    align-items: center;

    svg {
        color: ${(props) => props.theme.color};
    }
`;

const ButtonWrapper = styled.div`
    display: flex;
    align-items: center;
`;

const ProfileButton = styled(Link)`
    background: ${forestGreen};
    color: #fff;
    font-size: 1.2rem;
    font-weight: 600;
    border: 1px solid ${forestGreen};
    border-radius: 0.7rem;
    padding: 6px 20px;
    text-decoration: none;
    display: inline-flex;
    align-items: center;
    transition: background-color 0.2s, color 0.2s, border-color 0.2s;
    margin: 0; /* Remove margin */

    &:hover, &:focus {
        background-color: ${forestGreen};
        color: #fff;
        border-color: ${forestGreen};
    }
`;

const LogoutButton = styled.button`
    background: ${crimsonRed};
    color: #fff;
    font-size: 1.2rem;
    font-weight: 600;
    border: 1px solid ${crimsonRed};
    border-radius: 0.7rem;
    padding: 6px 20px;
    cursor: pointer;
    text-decoration: none;
    display: inline-flex;
    align-items: center;
    transition: background-color 0.2s, color 0.2s, border-color 0.2s;
    margin: 0.5rem;

    &:hover, &:focus {
        background-color: ${crimsonRed};
        color: #fff;
        border-color: ${crimsonRed};
    }
`;

const ThemeToggleButton = styled.button`
    background: none;
    border: none;
    padding: 0;
    margin-left: 1rem;
    cursor: pointer;
    font-size: 1.5rem;
    display: flex;
    align-items: center;

    svg {
        color: ${props => (props.isDarkTheme ? sunColorDark : moonColorLight)};
        transition: color 0.3s;
    }

    &:hover, &:focus {
        svg {
            color: ${props => (props.isDarkTheme ? sunColorLight : moonColorLight)};
        }
    }
`;

const Menu = ({ toggleSidebar, isLoggedIn, userId, toggleTheme, isDarkTheme }) => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleLogout = async () => {
        try {
            await dispatch(logout());
            localStorage.removeItem('user');
            localStorage.removeItem('token');
            navigate('/');
        } catch (error) {
            console.error('Logout failed', error);
        }
    };

    return (
        <HeaderWrapper>
            <MenuButton onClick={toggleSidebar} aria-label="Toggle sidebar">
                <FiMenu />
            </MenuButton>
            <SearchBar />
            <ButtonWrapper>
                {isLoggedIn ? (
                    <>
                        <ProfileButton to={`/user/${userId}`}>
                            Profile
                        </ProfileButton>
                        <LogoutButton onClick={handleLogout}>
                            Log out
                        </LogoutButton>
                    </>
                ) : (
                    <ProfileButton to="/login">
                        Log in
                    </ProfileButton>
                )}
                <ThemeToggleButton isDarkTheme={isDarkTheme} onClick={toggleTheme}>
                    {isDarkTheme ? <FiSun /> : <FiMoon />}
                </ThemeToggleButton>
            </ButtonWrapper>
        </HeaderWrapper>
    );
};

export default Menu;
