import React from 'react';
import styled from 'styled-components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useDispatch } from 'react-redux';
import { getMoviesByGenreName } from '../actions';
import { faHeart, faPoll, faCalendar, faDotCircle } from '@fortawesome/free-solid-svg-icons';

const StyledItem = styled.div`
    width: 100%;
    display: flex;
    align-items: center;
    padding: 1rem 2rem;
    font-size: 1.2rem;
    font-weight: 600;
    line-height: 1;
    opacity: ${props => (props.selected ? '1' : '.6')};
    color: ${props => (props.selected ? 'var(--color-primary-dark)' : 'var(--color-primary-light)')};
    border-color: ${props =>
    props.selected
        ? 'var(--color-primary-dark)'
        : 'var(--color-primary-light)'};
    border: ${props => (props.selected ? '1px solid' : '1px solid transparent')};
    border-radius: 2rem;
    text-decoration: none;
    cursor: pointer;
    transition: all 100ms cubic-bezier(0.075, 0.82, 0.165, 1);

    &:not(:last-child) {
        margin-bottom: 3rem;
    }

    &:hover {
        border: 1px solid;
    }
`;

const iconMap = {
    Popular: faHeart,
    'Top Rated': faPoll,
    Upcoming: faCalendar,
    default: faDotCircle,
};

const MenuItem = ({ title, selected }) => {
    const dispatch = useDispatch();

    const handleClick = () => {
        dispatch(getMoviesByGenreName(title));
    };

    return (
        <StyledItem selected={selected} onClick={handleClick}>
            <FontAwesomeIcon
                icon={iconMap[title] || iconMap.default}
                size="1x"
                style={{ marginRight: '10px' }}
            />
            {title}
        </StyledItem>
    );
};

export default MenuItem;
