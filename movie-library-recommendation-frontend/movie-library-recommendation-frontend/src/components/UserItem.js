import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import LazyLoad from 'react-lazyload';
import { addFriends, fetchAllFriends } from '../actions';
import UserSvg from '../svg/user.svg';

const UserWrapper = styled(Link)`
    display: flex;
    flex-direction: column;
    align-items: center; // Center the content
    text-decoration: none;
    background-color: transparent;
    border-radius: 0.8rem;
    transition: all 300ms cubic-bezier(0.215, 0.61, 0.355, 1);
    position: relative;

    &:hover {
        transform: scale(1.03);

        ::after {
            transform: scaleY(1);
            opacity: 1;
        }
    }

    &::after {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        border-radius: 0.8rem;
        transform: scaleY(0);
        transform-origin: top;
        opacity: 0;
        background-color: var(--color-primary);
        z-index: -99;
        box-shadow: 0rem 2rem 5rem var(--shadow-color-dark);
        transition: all 100ms cubic-bezier(0.215, 0.61, 0.355, 1);
    }
`;

const UserImg = styled.img`
    width: 100%;
    height: 25rem;
    object-fit: ${(props) => (props.$error ? 'contain' : 'cover')};
    border-radius: 50%;
    padding: ${(props) => (props.$error ? '2rem' : '0')};
    box-shadow: 0rem 2rem 5rem var(--shadow-color);
    transition: all 100ms cubic-bezier(0.645, 0.045, 0.355, 1);

    ${UserWrapper}:hover & {
        box-shadow: none;
    }

    @media ${(props) => props.theme.mediaQueries.smaller} {
        height: 20rem;
    }
`;

const Name = styled.h2`
    text-align: center;
    font-size: 1.3rem;
    font-weight: 400;
    color: var(--color-primary-light);
    margin-top: 1rem;
    line-height: 1.4;
    transition: color 300ms cubic-bezier(0.645, 0.045, 0.355, 1);

    ${UserWrapper}:hover & {
        color: var(--text-color);
    }
`;

const AddFriendButton = styled.button`
    margin-top: 1rem;
    padding: 0.8rem 1.6rem;
    font-size: 1.2rem;
    color: #fff;
    background-color: var(--color-primary);
    border: none;
    border-radius: 0.8rem;
    cursor: pointer;
    transition: background-color 200ms;

    &:hover {
        background-color: var(--color-primary-dark);
    }
`;

const ContentWrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
`;

const UserItem = ({ user, isLoggedIn, userId, friends, addFriends, fetchAllFriends }) => {
    const [loaded, setLoaded] = useState(false);
    const [error, setError] = useState(false);
    const [isFriend, setIsFriend] = useState(friends && friends.some(friend => friend.id === user.id));
    const [addingFriend, setAddingFriend] = useState(false);

    const isCurrentUser = isLoggedIn && userId === user.id;

    useEffect(() => {
        if (userId !== null) {
            fetchAllFriends(userId);
        }
    }, [userId, fetchAllFriends]);

    const handleAddFriend = () => {
        if (userId !== null) {
            setAddingFriend(true);
            addFriends(userId, user.id).finally(() => {
                setIsFriend(true);
                setAddingFriend(false);
            });
        }
    };

    return (
        <LazyLoad height={200} offset={200}>
            <ContentWrapper>
                <UserImg
                    style={{ display: loaded ? 'block' : 'none' }}
                    src={user.profilePicture || UserSvg}
                    alt={user.username}
                    onLoad={() => setLoaded(true)}
                    onError={(e) => {
                        setError(true);
                        if (e.target.src !== UserSvg) {
                            e.target.src = UserSvg;
                        }
                    }}
                    $error={error}
                />
                <Name>{user.username}</Name>
                {isLoggedIn && !isCurrentUser && !isFriend && (
                    <AddFriendButton onClick={handleAddFriend} disabled={addingFriend}>
                        {addingFriend ? 'Adding...' : 'Add as Friend'}
                    </AddFriendButton>
                )}
            </ContentWrapper>
        </LazyLoad>
    );
};


UserItem.propTypes = {
    user: PropTypes.shape({
        id: PropTypes.number.isRequired,
        username: PropTypes.string.isRequired,
        firstName: PropTypes.string.isRequired,
        lastName: PropTypes.string.isRequired,
        profilePicture: PropTypes.string,
    }).isRequired,
    friends: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            username: PropTypes.string.isRequired,
        })
    ),
    addFriends: PropTypes.func.isRequired,
    fetchAllFriends: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => ({
    friends: state.friend.friends,
});

export default connect(mapStateToProps, { addFriends, fetchAllFriends })(UserItem);
