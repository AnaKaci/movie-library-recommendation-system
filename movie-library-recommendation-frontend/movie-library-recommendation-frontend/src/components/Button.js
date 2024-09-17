import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlay, faBookmark, faComment, faChevronLeft } from '@fortawesome/free-solid-svg-icons';
import { library } from '@fortawesome/fontawesome-svg-core';

// Add the icons to the library
library.add(faBookmark, faPlay, faComment, faChevronLeft);

const getTextColor = (theme) => theme.colors.text === '#000000' ? '#ffffff' : '#000000';

const StyledButton = styled.button`
    display: flex;
    flex-direction: ${props => (props.$left ? 'row' : 'row-reverse')};
    align-items: center;
    text-decoration: none;
    outline: none;
    cursor: ${props => (props.disabled ? 'not-allowed' : 'pointer')};
    padding: 1.2rem 3rem;
    line-height: 1;
    font-weight: 500;
    font-size: 1.3rem;
    width: auto;
    flex-grow: 0;
    color: ${props => props.$solid ? getTextColor(props.theme) : 'inherit'};
    border: ${props => (props.$solid ? '1px solid transparent' : '1px solid var(--color-primary-dark)')};
    background-color: ${props => (props.$solid ? 'var(--color-primary-dark)' : 'transparent')};
    border-radius: 5rem;
    box-shadow: ${props => (props.$solid ? '0 1rem 5rem var(--shadow-color)' : 'none')};
    transition: all 300ms cubic-bezier(0.075, 0.82, 0.165, 1);

    &:hover {
        transform: ${props => (props.disabled ? 'none' : 'translateY(-3px)')};
        background-color: ${props => (props.$solid ? 'transparent' : 'var(--color-primary-dark)')};
        color: ${props => (props.$solid ? 'var(--color-primary-dark)' : 'white')};
        border: ${props => (props.$solid ? '1px solid var(--color-primary-dark)' : '1px solid transparent')};
        box-shadow: ${props => (props.$solid ? 'none' : '0 1rem 5rem var(--shadow-color)')};
    }

    @media ${props => props.theme.mediaQueries.large} {
        padding: 1.2rem 2rem;
    }

    @media ${props => props.theme.mediaQueries.small} {
        padding: 1.3rem 1.6rem;
    }

    @media ${props => props.theme.mediaQueries.smaller} {
        padding: 1rem 1.3rem;
    }

    &:active {
        transform: ${props => (props.disabled ? 'none' : 'translateY(2px)')};
    }
`;

const Button = ({ title, solid = false, icon, left = false, onClick, type = 'button', disabled = false }) => {
    return (
        <StyledButton $left={left} $solid={solid} onClick={onClick} type={type} disabled={disabled}>
            {icon && (
                <FontAwesomeIcon
                    icon={icon}
                    size="1x"
                    style={left ? { marginRight: '10px' } : { marginLeft: '10px' }}
                />
            )}
            {title}
        </StyledButton>
    );
};

Button.propTypes = {
    title: PropTypes.string.isRequired,
    solid: PropTypes.bool,
    icon: PropTypes.oneOfType([
        PropTypes.string,
        PropTypes.array,
        PropTypes.object,
    ]),
    left: PropTypes.bool,
    onClick: PropTypes.func,
    type: PropTypes.string,
    disabled: PropTypes.bool,
};

export default Button;
