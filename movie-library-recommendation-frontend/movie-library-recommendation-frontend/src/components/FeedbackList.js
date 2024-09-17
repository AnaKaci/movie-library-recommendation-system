import PropTypes from 'prop-types';
import styled from 'styled-components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar, faPen, faTrash } from '@fortawesome/free-solid-svg-icons';
import { useDispatch, useSelector } from "react-redux";
import React, {useCallback, useEffect, useState} from "react";
import {deleteFeedbackAction, getFeedbacksForUser} from "../actions";
import logo from "../svg/user.svg";
import Feedback from "../containers/Feedback";

const getTextColor = (theme) => theme.colors.text === '#000000' ? '#ffffff' : '#000000';

const List = styled.ul`
    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;
    flex-direction: column;
    gap: 2rem;
`;

const ListItem = styled.li`
    border: 1px solid ${({ theme }) => getTextColor(theme)};
    border-radius: 12px;
    padding: 1.5rem;
    background-color: ${({ theme }) => theme.colors.background};
    box-shadow: 0 4px 8px ${({ theme }) => theme.colors.dark}20;
    transition: transform 0.2s, box-shadow 0.2s;
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 0.5rem;

    &:hover {
        transform: translateY(-5px);
        box-shadow: 0 6px 12px ${({ theme }) => theme.colors.dark}30;
    }
`;

const ContentWrapper = styled.div`
    display: flex;
    flex-direction: column;
    flex-grow: 1;
`;

const RoundButton = styled.button`
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: ${(props) => (props.variant === 'update' ? '#1c98de' : '#b83515')}; /* Green for update, Red for delete */
    color: white;
    border: none;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    font-size: 1.5rem;
    cursor: pointer;
    transition: transform 0.3s ease;
    &:hover {
        transform: scale(1.05);
        background-color: ${(props) => (props.variant === 'update' ? '#1c98de' : '#b83515')};
    }
    &:focus {
        outline: none;
    }
`;

const Username = styled.p`
    font-weight: 600;
    font-size: 1.4rem;
    color: ${({ theme }) => theme.colors.text};
    margin: 0;
`;


const Rating = styled.div`
    display: flex;
    align-items: center;
    margin: 0.5rem 0;

    svg {
        color: #f1c40f;
        font-size: 1.3rem;
        margin-right: 0.25rem;
    }
`;

const Text = styled.p`
    margin: 0;
    font-size: 1.3rem;
    line-height: 1.5;
    color: ${({ theme }) => theme.colors.lighter};
`;

const ButtonWrapper = styled.div`
    display: flex;
    gap: 0.5rem;
`;

const Picture = styled.img`
    border-radius: 50%;
    width: 60px;
    height: 60px;
    object-fit: cover;
    margin: 0.5rem 1rem;
    border: 2px solid ${({ theme }) => theme.colors.text};
    transition: transform 0.3s ease-in-out;

    &:hover {
        transform: scale(1.1);
    }

    @media(min-width: 768px) {
        margin-left: 2rem;
    }
`;

const StyledRoundButton = ({ title, icon, onClick, variant }) => {
    return (
        <RoundButton title={title} variant={variant} onClick={onClick}>
            <FontAwesomeIcon icon={icon} />
        </RoundButton>
    );
};



const FeedbackList = ({ feedbacks, isLoggedIn, userId, movieId }) => {
    const dispatch = useDispatch();

    const fetchUserFeedback = useCallback(async () => {
        if (userId && movieId) {
            try {
                await getFeedbacksForUser(userId, movieId);
            } catch (error) {
                console.error('Error fetching user feedback:', error);
            }
        }
    }, [movieId, userId]);


    useEffect(() => {
        fetchUserFeedback();
    }, [fetchUserFeedback]);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const userFeedback = useSelector(state => state.feedbacks.userFeedback);

    const handleDeleteFeedback = async () => {
        if (window.confirm('Are you sure you want to delete your feedback?')) {
            try {
                await dispatch(deleteFeedbackAction(userId, movieId));
                alert('Feedback deleted successfully');
                window.location.reload();
            } catch (error) {
                alert('Failed to delete feedback. Please try again later.');
            }
        }
    };

    if (feedbacks.length === 0) {
        return <p>No reviews yet.</p>;
    }

    return (
        <List>
            {feedbacks.map((feedback) => (
                <ListItem key={feedback.userId}>
                    <Picture src={logo} alt="User Profile" />
                    <ContentWrapper>
                        <Username>{feedback.username ? feedback.username : 'Unknown User'}</Username>
                        <Rating>
                            {[...Array(5)].map((_, i) => (
                                <FontAwesomeIcon
                                    icon={faStar}
                                    key={i}
                                    style={{ opacity: i < Math.round(feedback.feedbackRating / 2) ? 1 : 0.3 }}
                                />
                            ))}
                            <span>{feedback.feedbackRating} / 10</span>
                        </Rating>
                        <Text>{feedback.feedbackText}</Text>
                    </ContentWrapper>
                    {isLoggedIn && userFeedback.userId === feedback.userId && (
                        <ButtonWrapper>
                            <StyledRoundButton
                                title="Update"
                                solid={true}
                                icon={faPen}
                                onClick={() => setIsModalOpen(true)}
                                variant={'update'}
                            />
                            {isModalOpen && (
                                <Feedback
                                    userId={userId}
                                    isModalOpen={isModalOpen}
                                    setIsModalOpen={setIsModalOpen}
                                    movieId={movieId}
                                />
                            )}
                            <StyledRoundButton
                                title="Delete"
                                solid={true}
                                icon={faTrash}
                                onClick={handleDeleteFeedback}
                                variant={'delete'}/>
                        </ButtonWrapper>
                    )}
                </ListItem>
            ))}
        </List>
    );
};

// Prop types
FeedbackList.propTypes = {
    feedbacks: PropTypes.arrayOf(
        PropTypes.shape({
            userId: PropTypes.number.isRequired,
            username: PropTypes.string.isRequired,
            feedbackText: PropTypes.string.isRequired,
            feedbackRating: PropTypes.number.isRequired,
        })
    ).isRequired,
    isLoggedIn: PropTypes.bool.isRequired,
    userId: PropTypes.number.isRequired,
    movieId: PropTypes.number.isRequired,
};

StyledRoundButton.propTypes = {
    title: PropTypes.string.isRequired,
    icon: PropTypes.object.isRequired,
    onClick: PropTypes.func.isRequired,
    variant: PropTypes.oneOf(['update', 'delete']).isRequired,
};

export default FeedbackList;
