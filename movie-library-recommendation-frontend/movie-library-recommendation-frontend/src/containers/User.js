import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import {
    getUser,
    updateUserPreferences,
    updateUserProfile,
    fetchActors,
    fetchDirectors,
    getGenresForPreference,
    setPreferences,
    fetchAllFriends,
    removeFriendofUser, deleteActualUser
} from '../actions';
import {useNavigate, useParams} from 'react-router-dom';
import userLogo from '../svg/user.svg';
import { createSelector } from 'reselect';

const PreferencesWrapper = styled.div`
    margin-top: 2rem;
    margin-bottom: 2rem;
    width: 100%;
    padding: 1rem 0;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 2rem;
`;

const PreferenceSection = styled.div`
    display: flex;
    flex-direction: column;
`;

const SaveButtonWrapper = styled.div`
    grid-column: 1 / -1;
    display: flex;
    justify-content: center;
`;

const SaveButton = styled.button`
    background: #0b643c;
    color: white;
    border: none;
    border-radius: 8px;
    padding: 0.75rem 1.5rem;
    font-size: 1.1rem;
    cursor: pointer;
    margin: 0.75rem;
    margin-top: 1rem;
    transition: background-color 0.2s, transform 0.2s;

    &:hover {
        transform: translateY(-2px);
    }
`;

const ProfileWrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 2rem;
    max-width: 100%;
    margin: 15px;
    background: ${({ theme }) => theme.background};
    border-radius: 8px;
    box-shadow: 0 4px 12px ${({ theme }) => theme.colors.dark}80;
    color: ${({ theme }) => theme.textColor};
`;

const ProfilePictureWrapper = styled.div`
    position: relative;
    margin-bottom: 1rem;
    width: 150px;
    height: 150px;
`;

const ProfilePicture = styled.img`
    border-radius: 50%;
    width: 100%;
    height: 100%;
    object-fit: cover;
    margin-bottom: 1rem;
    border: 4px solid #0056b3; 
    transition: transform 0.3s ease-in-out;

    &:hover {
        transform: scale(1.05);
    }
`;


const UserName = styled.h1`
    font-size: 2.4rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
    color: ${({ theme }) => theme.textColor};
`;

const UserDetail = styled.p`
    font-size: 1.2rem;
    margin: 0.2rem 0;
    color: ${({ theme }) => theme.secondaryText}; 
`;

const EditInput = styled.input`
    font-size: 1rem;
    padding: 0.5rem;
    margin: 0.4rem 0;
    border: 1px solid ${({ theme }) => theme.inputBorder};
    border-radius: 8px;
    width: 100%;
    background-color: ${({ theme }) => theme.inputBackground};
    color: ${({ theme }) => theme.textColor};
`;

const PreferenceLabel = styled.label`
    display: block;
    font-size: 1.5rem;
    margin: 0.4rem 0;
    color: ${({ theme }) => theme.primary};
    justify-content: center;
`;

const Select = styled.select`
    font-size: 1rem;
    padding: 0.5rem;
    margin: 0.4rem 0;
    border: 1px solid ${({ theme }) => theme.inputBorder};
    border-radius: 8px;
    width: 100%;
    background-color: ${({ theme }) => theme.inputBackground};
    color: ${({ theme }) => theme.textColor};
`;

const EditButtonIcon = styled.button`
    position: absolute;
    bottom: 0;
    right: 0;
    background: #007bff;
    color: white;
    border: none;
    border-radius: 50%;
    padding: 0.5rem;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: background-color 0.3s ease-in-out;

    &:hover {
        background-color: #0056b3;
    }
    
    &::before {
        content: '✎'; 
        font-size: 1.2rem;
    }
`;

const EditButton = styled(SaveButton)`
    background: #007bff;
    margin-top: 0.75rem;

    &:hover {
        background-color: #0056b3;
}
`;

const FriendsModal = styled.div`
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: ${({ theme }) => theme.colors.background};
    padding: 2rem;
    border-radius: 12px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
    width: 80%;
    max-width: 600px;
    z-index: 1000;
    overflow-y: auto;
    max-height: 80%; // Ensures that the modal does not exceed screen height
    transition: all 0.3s ease-in-out;
    opacity: 1;
    animation: fadeIn 0.3s ease-in-out;

    @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }
`;

const FriendsList = styled.ul`
    list-style: none;
    padding: 0;
    margin: 0;
    max-height: 60vh;
    overflow-y: auto; 
    padding-right: 1rem;
`;

const FriendItem = styled.li`
    padding: 0.8rem 1rem;
    margin: 0.5rem 0;
    border-bottom: 1px solid ${({ theme }) => theme.borderColor};
    font-size: 1.2rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-radius: 8px;
    background-color: ${({ theme }) => theme.colors.secondaryBackground};
    transition: background-color 0.3s ease, box-shadow 0.3s ease;

    &:last-child {
        border-bottom: none;
    }

    &:hover {
        background-color: ${({ theme }) => theme.colors.hoverBackground};
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    }

    span {
        font-weight: 500;
        color: ${({ theme }) => theme.colors.textPrimary};
    }
    
    }
`;


const RemoveButton = styled.button`
    background: #ba0f17;
    color: white;
    border: none;
    border-radius: 8px;
    padding: 0.3rem 0.75rem;
    cursor: pointer;
    font-size: 1rem;

    &:hover {
        background: #c82333;
    }
`;

const CloseButton = styled.button`
    background: #ba0f17;
    color: white;
    border: none;
    border-radius: 8px;
    padding: 0.5rem 1rem;
    cursor: pointer;
    margin-top: 1rem;

    &:hover {
        background: #c82333;
    }
`;

const DeleteButton = styled.button`
    background: #ba0f17;
    color: white;
    border: none;
    margin: 0.75rem;
    border-radius: 8px;
    padding: 0.75rem 1.5rem;
    font-size: 1.1rem;
    cursor: pointer;
    margin-top: 1rem;
    transition: background-color 0.2s, transform 0.2s;

    &:hover {
        background-color: #c82333;
        transform: translateY(-2px);
    }
`;


const selectPreference = (state) => state.preference || {};
createSelector(
    [selectPreference],
    (preference) => preference.genres || []
);
createSelector(
    [selectPreference],
    (preference) => preference.actors || []
);
createSelector(
    [selectPreference],
    (preference) => preference.directors || []
);
const User = () => {
    const dispatch = useDispatch();
    const { id: userId } = useParams();
    const user = useSelector((state) => state.user?.loggedInUser.user);
    const preference = useSelector((state) => state.preference.results);
    const isLoading = useSelector((state) => state.user?.loading);
    const genres = useSelector((state) => state.preference?.genres || []);
    const actors = useSelector((state) => state.preference?.actors || []);
    const directors = useSelector((state) => state.preference?.directors || []);
    const friends = useSelector((state) => state.friend.friends || []);
    const navigate = useNavigate();


    const [selectedFavoriteGenre, setSelectedFavoriteGenre] = useState('');
    const [selectedDislikedGenre, setSelectedDislikedGenre] = useState('');
    const [selectedFavoriteActor, setSelectedFavoriteActor] = useState('');
    const [selectedDislikedActor, setSelectedDislikedActor] = useState('');
    const [selectedFavoriteDirector, setSelectedFavoriteDirector] = useState('');
    const [selectedDislikedDirector, setSelectedDislikedDirector] = useState('');
    const [isEditing, setIsEditing] = useState(false);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');
    const [email, setEmail] = useState('');
    const [profilePicture, setProfilePicture] = useState('');
    const [password, setPassword] = useState('');
    const [showFriendsModal, setShowFriendsModal] = useState(false);

    useEffect(() => {
        dispatch(fetchActors());
        dispatch(fetchDirectors());
        dispatch(getGenresForPreference());
        dispatch(getUser(userId));
        dispatch(fetchAllFriends(userId));
    }, [dispatch, userId]);

    useEffect(() => {
        if (user) {
            dispatch(setPreferences({
                favoriteGenres: preference.favoriteGenres || '',
                dislikedGenres: preference.dislikedGenres || '',
                favoriteActors: preference.favoriteActors || '',
                dislikedActors: preference.dislikedActors || '',
                favoriteDirectors: preference.favoriteDirectors || '',
                dislikedDirectors: preference.dislikedDirectors || ''
            }));

            setSelectedFavoriteGenre(preference.favoriteGenres || '');
            setSelectedDislikedGenre(preference.dislikedGenres || '');
            setSelectedFavoriteActor(preference.favoriteActors || '');
            setSelectedDislikedActor(preference.dislikedActors || '');
            setSelectedFavoriteDirector(preference.favoriteDirectors || '');
            setSelectedDislikedDirector(preference.dislikedDirectors || '');
            setFirstName(user.firstName || '');
            setLastName(user.lastName || '');
            setDateOfBirth(user.dateOfBirth || '');
            setEmail(user.email || '');
            setProfilePicture(user.profilePicture || '');
        }
    }, [user, dispatch]);

    const handleSingleChoiceChange = (type, e) => {
        const value = e.target.value;

        const setFunction = {
            favoriteGenre: setSelectedFavoriteGenre,
            dislikedGenre: setSelectedDislikedGenre,
            favoriteActor: setSelectedFavoriteActor,
            dislikedActor: setSelectedDislikedActor,
            favoriteDirector: setSelectedFavoriteDirector,
            dislikedDirector: setSelectedDislikedDirector
        }[type];

        if (setFunction) {
            setFunction(value);
        }
    };

    const handleDeleteUser = () => {
        dispatch(deleteActualUser(userId))
            .then(() => {
                navigate('/login');
            })
            .catch(error => {
                console.error('Error deleting account:', error);
            });
    };


    const handleSavePreferences = () => {
        const preferenceDTOFavorite = {
            genreName: selectedFavoriteGenre,
            actorName: selectedFavoriteActor,
            directorName: selectedFavoriteDirector,
            preferenceType: 'FAVORITE'
        };

        const preferenceDTODisliked = {
            genreName: selectedDislikedGenre,
            actorName: selectedDislikedActor,
            directorName: selectedDislikedDirector,
            preferenceType: 'DISLIKED'
        };

        dispatch(updateUserPreferences(userId, preferenceDTOFavorite));
        dispatch(updateUserPreferences(userId, preferenceDTODisliked));
    };

    const handleEditToggle = () => {
        setIsEditing(!isEditing);
    };

    const handleSaveProfile = () => {
        const updatedUser = {
            firstName,
            lastName,
            dateOfBirth,
            email,
            password
        };
        dispatch(updateUserProfile(userId, updatedUser))
            .then(() => {
                navigate('/login'); // Redirect to login page
            })
            .catch(error => {
                console.error('Error updating profile:', error);
            });
        handleEditToggle();
    };

    const handleShowFriends = () => {
        setShowFriendsModal(true);
    };

    const handleCloseFriendsModal = () => {
        setShowFriendsModal(false);
    };

    const handleRemoveFriend = (friendId) => {
        dispatch(removeFriendofUser(userId, friendId));
    };

    if (isLoading) return <p>Loading...</p>;

    return (
        <>
            <ProfileWrapper>
                <ProfilePictureWrapper>
                    <ProfilePicture src={profilePicture || userLogo} alt="User Profile" />
                    <EditButtonIcon onClick={handleEditToggle} />
                </ProfilePictureWrapper>
                <UserName>{`${user?.firstName} ${user?.lastName}`}</UserName>
                <UserDetail>Email: {user?.email}</UserDetail>
                <UserDetail>Date of Birth: {user?.dateOfBirth}</UserDetail>

                {isEditing ? (
                    <>
                        <EditInput
                            type="text"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            placeholder="First Name"
                        />
                        <EditInput
                            type="text"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            placeholder="Last Name"
                        />
                        <EditInput
                            type="date"
                            value={dateOfBirth}
                            onChange={(e) => setDateOfBirth(e.target.value)}
                            placeholder="Date of Birth"
                        />
                        <EditInput
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Email"
                        />
                        <EditInput
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Password"
                        />
                        <EditButton onClick={handleSaveProfile}>Save Profile</EditButton>
                    </>
                ) : (
                    <>
                        <p> </p>
                    </>
                )}


                <SaveButtonWrapper>
                    <SaveButton onClick={handleShowFriends}>Show My Friends</SaveButton>
                    <DeleteButton onClick={handleDeleteUser}>Delete Account</DeleteButton>
                </SaveButtonWrapper>

                <PreferencesWrapper>
                    <PreferenceSection>
                        <PreferenceLabel htmlFor="favoriteGenre">Favorite Genre:</PreferenceLabel>
                        <Select
                            id="favoriteGenre"
                            value={selectedFavoriteGenre}
                            onChange={(e) => handleSingleChoiceChange('favoriteGenre', e)}
                        >
                            {genres.map((genre) => (
                                <option key={genre.genreName} value={genre.genreName}>
                                    {genre.genreName}
                                </option>
                            ))}
                        </Select>
                    </PreferenceSection>

                    <PreferenceSection>
                        <PreferenceLabel htmlFor="dislikedGenre">Disliked Genre:</PreferenceLabel>
                        <Select
                            id="dislikedGenre"
                            value={selectedDislikedGenre}
                            onChange={(e) => handleSingleChoiceChange('dislikedGenre', e)}
                        >
                            {genres.map((genre) => (
                                <option key={genre.genreName} value={genre.genreName}>
                                    {genre.genreName}
                                </option>
                            ))}
                        </Select>
                    </PreferenceSection>

                    <PreferenceSection>
                        <PreferenceLabel htmlFor="favoriteActor">Favorite Actor:</PreferenceLabel>
                        <Select
                            id="favoriteActor"
                            value={selectedFavoriteActor}
                            onChange={(e) => handleSingleChoiceChange('favoriteActor', e)}
                        >
                            {actors.map((actor) => (
                                <option key={actor.name} value={actor.name}>
                                    {actor.name}
                                </option>
                            ))}
                        </Select>
                    </PreferenceSection>

                    <PreferenceSection>
                        <PreferenceLabel htmlFor="dislikedActor">Disliked Actor:</PreferenceLabel>
                        <Select
                            id="dislikedActor"
                            value={selectedDislikedActor}
                            onChange={(e) => handleSingleChoiceChange('dislikedActor', e)}
                        >
                            {actors.map((actor) => (
                                <option key={actor.name} value={actor.name}>
                                    {actor.name}
                                </option>
                            ))}
                        </Select>
                    </PreferenceSection>

                    <PreferenceSection>
                        <PreferenceLabel htmlFor="favoriteDirector">Favorite Director:</PreferenceLabel>
                        <Select
                            id="favoriteDirector"
                            value={selectedFavoriteDirector}
                            onChange={(e) => handleSingleChoiceChange('favoriteDirector', e)}
                        >
                            {directors.map((director) => (
                                <option key={director.name} value={director.name}>
                                    {director.name}
                                </option>
                            ))}
                        </Select>
                    </PreferenceSection>

                    <PreferenceSection>
                        <PreferenceLabel htmlFor="dislikedDirector">Disliked Director:</PreferenceLabel>
                        <Select
                            id="dislikedDirector"
                            value={selectedDislikedDirector}
                            onChange={(e) => handleSingleChoiceChange('dislikedDirector', e)}
                        >
                            {directors.map((director) => (
                                <option key={director.name} value={director.name}>
                                    {director.name}
                                </option>
                            ))}
                        </Select>
                    </PreferenceSection>

                    <SaveButtonWrapper>
                        <SaveButton onClick={handleSavePreferences}>Save Preferences</SaveButton>
                    </SaveButtonWrapper>
                </PreferencesWrapper>
            </ProfileWrapper>

            {showFriendsModal && (
                <FriendsModal>
                    <h2>My Friends</h2>
                    <FriendsList>
                        {friends.length > 0 ? (
                            friends.map(friend => (
                                <FriendItem key={friend.id}>
                                    {friend.firstName} {friend.lastName}
                                    <RemoveButton onClick={() => handleRemoveFriend(friend.id)}>Remove</RemoveButton>
                                </FriendItem>
                            ))
                        ) : (
                            <p>No friends found.</p>
                        )}
                    </FriendsList>
                    <CloseButton onClick={handleCloseFriendsModal}>Close</CloseButton>
                </FriendsModal>
            )}
        </>
    );
};

export default User;
