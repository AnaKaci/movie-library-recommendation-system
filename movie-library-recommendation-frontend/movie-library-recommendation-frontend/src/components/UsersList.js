import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';
import UserItem from './UserItem';

const UsersGrid = styled.div`
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
    padding: 20px;
`;

const UsersList = ({ users, baseUrl, userId, isLoggedIn}) => {
    if (!Array.isArray(users)) {
        return <div>Error: Users data is not an array.</div>;
    }

    if (users.length === 0) {
        return <div>No users available</div>;
    }

    return (
        <UsersGrid>
            {users.map((user) => (
                <UserItem
                    key={user.id}
                    user={user}
                    baseUrl={baseUrl}
                    userId={userId}
                    isLoggedIn={isLoggedIn}
                />
            ))}
        </UsersGrid>
    );
};

UsersList.propTypes = {
    baseUrl: PropTypes.string.isRequired,
    users: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.number.isRequired,
        username: PropTypes.string.isRequired,
        firstName: PropTypes.string.isRequired,
        lastName: PropTypes.string.isRequired,
        profilePicture: PropTypes.string,
    })).isRequired,
};

export default UsersList;
