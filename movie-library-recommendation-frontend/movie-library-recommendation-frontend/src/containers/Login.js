import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import axios from 'axios';
import {loginSuccess} from '../actions';
import {
    MDBBtn,
    MDBContainer,
    MDBCard,
    MDBCardBody,
    MDBCardImage,
    MDBRow,
    MDBCol,
    MDBInput
} from 'mdb-react-ui-kit';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import "@fortawesome/fontawesome-free/css/all.min.css";
import logo from '../svg/logo.svg';
import { GiPopcorn } from 'react-icons/gi';
import styled from "styled-components";


const LogInCard = styled(MDBCard)`
    border-radius: 25px;
    background-color: ${({ theme }) => theme.colors.background};
`;

const Input = styled(MDBInput)`
    color: ${({ theme }) => theme.colors.text};
    margin-top: 0.5rem;
    margin-bottom: 2.5rem;
`;

const Button = styled(MDBBtn)`
    box-shadow: 0 4px 12px ${({ theme }) => theme.colors.dark}80;  
`;

const Container = styled(MDBContainer)`
    box-shadow: 0 4px 12px ${({ theme }) => theme.colors.dark}80;  
`;


function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);
    const dispatch = useDispatch();

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage('');

        try {
            const response = await axios.post('http://localhost:8082/api/login', {
                email,
                password
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            console.log('Login response:', response);
            const { success, message, user } = response.data;
            console.log('User object:', user);

            if (success && user) {
                const { id: userId, username, email, firstName, lastName, dateOfBirth, token, watchlists = [] } = user;
                if (!userId) {
                    console.error('User ID is undefined:', userId);
                    setMessage('Login failed. User ID is missing.');
                    return;
                }
                const userWatchlists = Array.isArray(watchlists) ? watchlists.map(watchlist => ({
                    watchlistId: watchlist.watchlistId,
                    movies: watchlist.movies.map(movie => ({
                        movieId: movie.movieId
                        // Add other movie fields if needed
                    }))
                })) : [];
                localStorage.setItem('user', JSON.stringify({ userId, username, email, firstName, lastName, dateOfBirth }));
                localStorage.setItem('token', token);
                localStorage.setItem('watchlists', JSON.stringify(userWatchlists));
                dispatch(loginSuccess(userId));

                console.log('Login success');
                setMessage('Login successful!');
                window.location.href = '/';
            } else {
                setMessage(message || 'Login failed. Please try again.');
            }
        } catch (error) {
            console.error('Error during login:', error);
            setMessage('Login failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };



    return (
        <Container className="my-5">
            <LogInCard>
                <MDBRow className='g-0'>
                    <MDBCol md='6'>
                        <MDBCardImage
                            src={logo}
                            alt="login form"
                            className='rounded-start'
                            style={{ width: '100%', height: 'auto', marginTop: '1rem', marginBottom: '1rem' }}
                        />
                    </MDBCol>
                    <MDBCol md='6'>
                        <MDBCardBody className='d-flex flex-column'>
                            <div className='d-flex flex-row mt-2'>
                                <GiPopcorn style={{ color: '#b29fa8', fontSize: '2.9rem', marginTop: '9rem', marginRight: '0.7rem' }} />
                                <span className="h1 fw-bold mb-0" style={{ fontSize: '2.9rem', marginTop: '8.75rem'}}>Movie Library</span>
                            </div>
                            <h5 className="fw-normal my-4 pb-3" style={{ letterSpacing: '1px'}}>Sign into your
                                account</h5>
                            <form onSubmit={handleLogin}>
                                <Input
                                    wrapperClass='mb-4'
                                    label='Email address'
                                    id='emailInput'
                                    type='email'
                                    size="lg"
                                    value={email}
                                    style={{ fontSize: '11px'}}
                                    onChange={(e) => setEmail(e.target.value)}
                                    autoComplete="email"
                                />
                                <Input
                                    wrapperClass='mb-4'
                                    label='Password'
                                    id='passwordInput'
                                    type='password'
                                    size="lg"
                                    value={password}
                                    style={{ fontSize: '11px'}}
                                    onChange={(e) => setPassword(e.target.value)}
                                    autoComplete="current-password"
                                />
                                <div className="d-flex justify-content-between" style={{ gap: '10px' }}>
                                    <Button
                                        className="px-5"
                                        color='dark'
                                        size='lg'
                                        type="submit"
                                        disabled={loading}
                                        style={{ borderRadius: '50px', width: '48%', fontSize: '10px', backgroundColor: '#4F4E63'}}
                                    >
                                        {loading ? 'Logging in...' : 'Login'}
                                    </Button>
                                    <Button
                                        className="px-5"
                                        color='primary'
                                        size='lg'
                                        onClick={() => window.location.href = '/'}
                                        style={{ borderRadius: '50px', width: '48%' , fontSize: '10px', backgroundColor: '#576E7A'}}
                                    >
                                        Home
                                    </Button>
                                </div>
                            </form>
                            <p className="mb-5 pb-lg-2" style={{ color: '#A8AAB4', fontSize: '10px', marginTop: '20px'}}>Don't have an account?
                                <a href="/signup" style={{ color: '#A8AAB4', fontSize: '10px' }}> Register here</a></p>
                            {message && <div
                                className={`mt-3 alert ${message.includes('failed') ? 'alert-danger' : 'alert-info'}`}>{message}</div>}
                        </MDBCardBody>
                    </MDBCol>
                </MDBRow>
            </LogInCard>
        </Container>
    );
}

export default Login;
