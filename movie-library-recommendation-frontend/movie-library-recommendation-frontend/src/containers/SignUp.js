import React, { useState } from 'react';
import axios from 'axios';
import {
    MDBBtn,
    MDBContainer,
    MDBCard,
    MDBCardBody,
    MDBCardImage,
    MDBRow,
    MDBCol,
    MDBIcon,
    MDBInput
} from 'mdb-react-ui-kit';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import "@fortawesome/fontawesome-free/css/all.min.css";
import movieSvg from '../svg/movie.svg';
import styled from "styled-components";


const SignUpCard = styled(MDBCard)`
    border-radius: 25px;
    background-color: ${({ theme }) => theme.colors.background};
`;

const Icon = styled(MDBIcon)`
    color: ${({ theme }) => theme.colors.text};
`;

const Input = styled(MDBInput)`
    color: ${({ theme }) => theme.colors.text};
`;

const Heading = styled.p`
    color: ${({ theme }) => theme.colors.text}; 
`;

const Container = styled(MDBContainer)`
    box-shadow: 0 4px 12px ${({ theme }) => theme.colors.dark}80;  
`;

const Button = styled(MDBBtn)`
    box-shadow: 0 4px 12px ${({ theme }) => theme.colors.dark}80;  
`;


function SignUp() {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSignUp = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage('');

        try {
            if (password !== confirmPassword) {
                setMessage('Passwords do not match.');
                setLoading(false);
                return;
            }

            const response = await axios.post(`http://localhost:8082/api/register`, {
                username,
                email,
                password,
                firstName,
                lastName,
                dateOfBirth,
            });

            if (response.data.success) {
                setMessage('Signup successful! Please login.');
                window.location.href = '/login';
            } else {
                setMessage('Signup failed. Please try again.');
            }
        } catch (error) {
            console.error("There was an error signing up!", error);
            setMessage('Signup failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container fluid className="my-5">
            <SignUpCard className='text-black m-5' style={{ borderRadius: '25px' }}>
                <MDBCardBody>
                    <MDBRow className="d-flex align-items-center">
                        <MDBCol md='7' lg='6' className='order-2 order-lg-1' style={{ marginLeft: '10px'}}>
                            <Heading className="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign Up</Heading>
                            <form onSubmit={handleSignUp}>
                                <div className="d-flex flex-row align-items-center mb-4">
                                    <Icon fas icon="user me-3" size='2x'/>
                                    <Input
                                        label='Username'
                                        id='form1'
                                        type='text'
                                        className='w-100'
                                        value={username}
                                        style={{ fontSize: '10px'}}
                                        onChange={(e) => setUsername(e.target.value)}
                                        autoComplete="username"
                                        required
                                    />
                                </div>

                                <div className="d-flex flex-row align-items-center mb-4">
                                    <Icon fas icon="envelope me-3" size='2x'/>
                                    <Input
                                        label='Your Email'
                                        id='form2'
                                        type='email'
                                        className='w-100'
                                        value={email}
                                        style={{ fontSize: '10px'}}
                                        onChange={(e) => setEmail(e.target.value)}
                                        autoComplete="email"
                                        required
                                    />
                                </div>

                                <div className="d-flex flex-row align-items-center mb-4">
                                    <Icon fas icon="lock me-3" size='2x'/>
                                    <Input
                                        label='Password'
                                        id='form3'
                                        type='password'
                                        className='w-100'
                                        value={password}
                                        style={{ fontSize: '10px'}}
                                        onChange={(e) => setPassword(e.target.value)}
                                        autoComplete="new-password"
                                        required
                                    />
                                </div>

                                <div className="d-flex flex-row align-items-center mb-4">
                                    <Icon fas icon="key me-3" size='2x'/>
                                    <Input
                                        label='Repeat your password'
                                        id='form4'
                                        type='password'
                                        className='w-100'
                                        value={confirmPassword}
                                        style={{ fontSize: '10px'}}
                                        onChange={(e) => setConfirmPassword(e.target.value)}
                                        autoComplete="new-password"
                                        required
                                    />
                                </div>

                                <div className="d-flex flex-row align-items-center mb-4">
                                    <Icon fas icon="user-tie me-3" size='2x'/>
                                    <Input
                                        label='First Name'
                                        id='form5'
                                        type='text'
                                        className='w-100'
                                        value={firstName}
                                        style={{ fontSize: '10px'}}
                                        onChange={(e) => setFirstName(e.target.value)}
                                        autoComplete="given-name"
                                        required
                                    />
                                </div>

                                <div className="d-flex flex-row align-items-center mb-4">
                                    <Icon fas icon="user me-3" size='2x'/>
                                    <Input
                                        label='Last Name'
                                        id='form6'
                                        type='text'
                                        className='w-100'
                                        value={lastName}
                                        style={{ fontSize: '10px'}}
                                        onChange={(e) => setLastName(e.target.value)}
                                        autoComplete="family-name"
                                        required
                                    />
                                </div>

                                <div className="d-flex flex-row align-items-center mb-4">
                                    <Icon fas icon="calendar-alt me-3" size='2x'/>
                                    <Input
                                        label='Date of Birth'
                                        id='form7'
                                        type='date'
                                        className='w-100'
                                        value={dateOfBirth}
                                        style={{ fontSize: '10px'}}
                                        onChange={(e) => setDateOfBirth(e.target.value)}
                                        autoComplete="bday"
                                        required
                                    />
                                </div>

                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'center',
                                    alignItems: 'center',
                                }}>
                                    <Button
                                        className='mb-4 px-5'
                                        size='lg'
                                        type='submit'
                                        disabled={loading}
                                        style={{ borderRadius: '50px', background: '#16a085', width: '30%', fontSize: '10px', marginTop: '10px'}}
                                    >
                                {loading ? 'Signing up...' : 'Sign Up'}
                            </Button>
                            </div>

                            {message && <div
                                className={`mt-3 alert ${message.includes('failed') ? 'alert-danger' : 'alert-info'}`}>{message}</div>}
                        </form>
                    </MDBCol>

                    <MDBCol md='5' lg='6' className='order-1 order-lg-2 d-flex justify-content-end align-items-center' style={{ marginLeft: '-35px'}}>
                        <MDBCardImage src={movieSvg} alt="signup form" fluid style={{ width: '80%', maxWidth: '400px', marginRight: '20px' }} />
                        </MDBCol>
                    </MDBRow>
                </MDBCardBody>
            </SignUpCard>
        </Container>
    );
}

export default SignUp;
