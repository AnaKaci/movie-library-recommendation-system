import React, { useState, useEffect } from 'react';
import ReactDOM from 'react-dom';
import styled from 'styled-components';
import PropTypes from 'prop-types';
import { useDispatch, useSelector } from 'react-redux';
import { getActor } from '../actions';
import actorLogo from '../svg/user.svg';

const getTextColor = (theme) => theme.colors.text === '#000000' ? '#ffffff' : '#000000';

const ActorCard = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center; 
    justify-content: center;
    text-align: center;
    cursor: pointer;
`;

const ActorImg = styled.img`
    width: 5rem;
    height: 5rem;
    border-radius: 50%;
    object-fit: cover;
    background-color: ${({ theme }) => theme.colors.light};
    transition: transform 200ms ease, box-shadow 200ms ease;
    box-shadow: 0 2px 8px ${({ theme }) => theme.colors.dark}20;

    &:hover {
        transform: scale(1.05);
        box-shadow: 0 4px 16px ${({ theme }) => theme.colors.dark}30;
    }
`;

const ActorName = styled.div`
    margin-top: 0.5rem;
    font-size: 1rem;
    color: ${({ theme }) => theme.colors.text};
`;

const ActorModalWrapper = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgba(0, 0, 0, 0.7);
    z-index: 1000;
`;

const ActorModalContent = styled.div`
    background: ${({ theme }) => theme.colors.background};
    border-radius: 12px;
    box-shadow: 0 8px 20px ${({ theme }) => getTextColor(theme)}30;
    max-width: 600px;
    width: 90%;
    padding: 2rem;
    position: relative;
    overflow-y: auto;
    max-height: 80vh;
`;

const CloseButton = styled.button`
    position: absolute;
    top: 15px;
    right: 15px;
    background: transparent;
    border: none;
    font-size: 1.5rem;
    color: #333;
    cursor: pointer;
`;

const ActorWrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    max-width: 100%;
    background: ${({ theme }) => theme.colors.background};
    border-radius: 12px;
    color: ${({ theme }) => theme.colors.text};
    z-index: 9999;

    @media (min-width: 768px) {
        flex-direction: row;
    }
`;

const ActorDetails = styled.div`
    flex: 1;
    padding: 2rem;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    background-color: ${({ theme }) => getTextColor(theme)};
    border-radius: 8px;
    box-shadow: 0 4px 12px ${({ theme }) => theme.colors.dark}20;
    margin: 1rem 0;

    h2 {
        font-size: 1.8rem;
        font-weight: 600;
        color: ${({ theme }) => theme.colors.text};
        margin-bottom: 1rem;
    }

    p {
        font-size: 1.1rem;
        color: ${({ theme }) => theme.colors.text};
        line-height: 1.6;
        margin-bottom: 1rem;
    }

    span {
        font-weight: 500;
        color: ${({ theme }) => theme.colors.link};
    }

    @media (min-width: 768px) {
        padding: 2rem 3rem;
    }
`;

const ActorPicture = styled.img`
    border-radius: 50%;
    width: 150px;
    height: 150px;
    object-fit: cover;
    margin: 1rem 0;
    border: 4px solid #0056b3;
    transition: transform 0.3s ease-in-out;

    &:hover {
        transform: scale(1.1);
    }

    @media (min-width: 768px) {
        margin-left: 2rem;
    }
`;


const BackButton = styled.button`
    background: #0056b3;
    color: white;
    border: none;
    border-radius: 8px;
    padding: 0.75rem 1.5rem;
    font-size: 1.1rem;
    cursor: pointer;
    margin-top: 1rem;
    transition: background-color 0.2s, transform 0.2s;

    &:hover {
        background-color: #007bff;
        transform: translateY(-2px);
    }
`;

const CastItem = ({ actor }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const dispatch = useDispatch();
    const actorData = useSelector((state) => state.movie?.actor);
    const isLoading = useSelector((state) => state.movie?.loading);
    const error = useSelector((state) => state.movie?.error);

    const handleOpenModal = () => {
        setIsModalOpen(true);
        dispatch(getActor(actor.name));
        //console.log('MovieModal is opened for: ', actor.name);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    useEffect(() => {
        //console.log('MovieModal is now: ', isModalOpen);
    }, [isModalOpen]);

    const modal = (
        <ActorModalWrapper>
            <ActorModalContent>
                <CloseButton onClick={handleCloseModal}>&times;</CloseButton>
                {isLoading ? (
                    <p>Loading...</p>
                ) : error || !actorData ? (
                    <p>Actor details could not be fetched. Please try again later.</p>
                ) : (
                    <ActorWrapper>
                        <ActorDetails>
                            <h2>{actor.name}</h2>
                            <p>Birthday: {actorData.dateOfBirth || 'N/A'}</p>
                            <p>Biography: {actorData.bio || 'Biography not available.'}</p>
                            <BackButton onClick={handleCloseModal}>Close</BackButton>
                        </ActorDetails>
                        <ActorPicture src={actorLogo} alt="Actor Profile" />
                    </ActorWrapper>
                )}
            </ActorModalContent>
        </ActorModalWrapper>
    );

    return (
        <>
            <ActorCard onClick={handleOpenModal}>
                <ActorImg
                    src={actorLogo}
                    alt={actor.name}
                    onError={(e) => {
                        e.target.src = actorLogo;
                    }}
                />
                <ActorName>{actor.name}</ActorName>
            </ActorCard>

            {isModalOpen && ReactDOM.createPortal(modal, document.getElementById('modal-root'))}
        </>
    );
};

// PropTypes validation for props
CastItem.propTypes = {
    actor: PropTypes.shape({
        name: PropTypes.string.isRequired,
    }).isRequired,
};

export default CastItem;
