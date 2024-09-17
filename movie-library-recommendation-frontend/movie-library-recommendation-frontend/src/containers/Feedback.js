import React, { useCallback, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { addFeedbackForMovie, getFeedbacksForUser, updateFeedbackAction } from "../actions";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";
import RatingSelect from "../components/RatingSelect";

const ModalWrapper = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
`;

const ModalContent = styled.div`
    background: ${({ theme }) => theme.colors.background};
    border-radius: 12px;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
    max-width: 700px;
    width: 90%;
    padding: 2rem;
    position: relative;
    overflow-y: auto;
    max-height: 80vh;
`;

const FeedbackWrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    max-width: 700px;
    margin: 0 auto;
    padding: 3rem;
    background: var(--background-color);
    border-radius: 1rem;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
`;

const LeftButtons = styled.div`
    display: flex;
    justify-content: center;
    width: 100%;

    & > *:not(:last-child) {
        margin-right: 50rem;
    }
`;

const FeedbackTitle = styled.h2`
    font-size: 2.4rem;
    font-weight: 700;
    color: var(--color-primary-dark);
    margin-bottom: 2rem;
    text-align: center;
`;

const InputGroup = styled.div`
    width: 100%;
    margin-bottom: 2rem;

    input {
        width: 100%;
        padding: 1rem 1.2rem;
        font-size: 1.4rem;
        border-radius: 0.5rem;
        border: 1px solid var(--color-primary-light);
        margin-bottom: 0.5rem;
        transition: all 0.3s ease;

        &:focus {
            outline: none;
            border-color: var(--color-primary-dark);
            box-shadow: 0 0 8px var(--color-primary-light);
        }
    }
`;

const Message = styled.div`
    color: var(--color-danger);
    font-size: 1rem;
    margin-top: -0.5rem;
    margin-bottom: 1rem;
    text-align: left;
`;



function Feedback({ userId, isModalOpen, setIsModalOpen, movieId }) {
    const dispatch = useDispatch();
    const [text, setText] = useState("");
    const [rating, setRating] = useState(10);
    const [btnDisabled, setBtnDisabled] = useState(true);
    const [message, setMessage] = useState("");
    const feedback = useSelector((state) => state.feedback);
    const navigate = useNavigate();
    const userFeedback = useSelector(state => state.feedbacks.userFeedback);

    const fetchUserFeedback = useCallback(async () => {
        if (userId) {
            await dispatch(getFeedbacksForUser(userId, movieId));
        }
    }, [movieId, userId, dispatch]);

    useEffect(() => {
        fetchUserFeedback();
    }, [fetchUserFeedback]);

    useEffect(() => {
        if (userFeedback) {
            setText(userFeedback.text || "");
            setRating(userFeedback.feedbackRating || 10);
        }
    }, [feedback, userFeedback]);

    const handleTextChange = (e) => {
        const value = e.target.value;
        if (value === "") {
            setBtnDisabled(true);
            setMessage(null);
        } else {
            setMessage(null);
            setBtnDisabled(false);
        }
        setText(value);
    };

    const handleOnBlur = () => {
        if (text.trim().length <= 5) {
            setMessage("Text must be at least 5 characters");
            setBtnDisabled(true);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const handleSuccess = () => {
            setText("");
            setRating(0);
            navigate(`/movie/${movieId}`, { replace: true });
            window.location.reload();
        };

        if (userFeedback.userId) {
            dispatch(updateFeedbackAction(userId, movieId, text, rating))
                .then(handleSuccess)
                .catch((error) => {
                    console.error('Error updating feedback:', error);
                });
        } else {
            dispatch(addFeedbackForMovie(userId, movieId, text, rating))
                .then(handleSuccess)
                .catch((error) => {
                    console.error('Error adding feedback:', error);
                });
        }
    };


    return (
        <>
            {isModalOpen && (
                <ModalWrapper>
                    <ModalContent>
                        <FeedbackWrapper>
                            <form onSubmit={handleSubmit}>
                                <FeedbackTitle>{userFeedback.userId ? "Update your review" : "How would you rate this movie?"}</FeedbackTitle>
                                <RatingSelect select={(rating) => setRating(rating)} />
                                <InputGroup>
                                    <input
                                        onChange={handleTextChange}
                                        onBlur={handleOnBlur}
                                        type="text"
                                        placeholder="Write a review"
                                        value={text}
                                    />
                                    {message && <Message>{message}</Message>}
                                </InputGroup>
                                <LeftButtons>
                                    <Button
                                        type="submit"
                                        solid={true}
                                        disabled={btnDisabled}
                                        title={userFeedback.userId ? "Update" : "Send"}
                                    />
                                    <Button
                                        title="Go Back"
                                        solid={true}
                                        left={false}
                                        onClick={() => setIsModalOpen(false)}
                                    />
                                </LeftButtons>
                            </form>
                        </FeedbackWrapper>
                    </ModalContent>
                </ModalWrapper>
            )}
        </>
    );
}

export default Feedback;
