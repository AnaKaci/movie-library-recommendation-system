import React, { useState, useRef, useEffect } from 'react';
import styled from 'styled-components';
import Loader from './Loader';
import CastItem from './CastItem';
import PropTypes from 'prop-types';


const getTextColor = (theme) => theme.colors.text === '#000000' ? '#ffffff' : '#000000';

const Wrapper = styled.div`
    margin-bottom: 5rem;
`;

const CarouselContainer = styled.div`
    position: relative;
    width: 100%;
    overflow: hidden;
    background-color: ${({ theme }) => theme.colors.background}; // Background color based on theme
`;

const CarouselSlider = styled.div`
    display: flex;
    transition: transform 0.5s ease-in-out;
`;

const CarouselItem = styled.div`
    width: 100px;
    height: 100px;
    margin: 0 10px;
    box-shadow: 0 4px 12px ${({ theme }) => theme.colors.dark}80; // Adjust shadow color based on theme
    border-radius: 50%;
    border: 1px solid ${({ theme }) => getTextColor(theme)}; // Border color based on theme
    background: ${({ theme }) => getTextColor(theme)}; // Background color based on theme
    display: flex;
    align-items: center;
    justify-content: center;
    transition: transform 200ms ease, box-shadow 200ms ease;

    &:hover {
        transform: scale(1.05);
        box-shadow: 0 6px 16px ${({ theme }) => theme.colors.dark}80; // Adjust shadow color on hover based on theme
    }
`;

const Arrow = styled.div`
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background: ${({ theme }) => theme.colors.dark}80; // Background color based on theme
    color: ${({ theme }) => theme.colors.light}; // Text color based on theme
    padding: 10px;
    cursor: pointer;
    z-index: 1000;
    user-select: none;

    &:hover {
        background: ${({ theme }) => theme.colors.dark}c0; // Darker background color on hover based on theme
    }
`;

const ArrowLeft = styled(Arrow)`
    left: 10px;
`;

const ArrowRight = styled(Arrow)`
    right: 10px;
`;

const Cast = ({ castData }) => {
    const cast = Array.isArray(castData) ? castData : castData?.castData || [];
    const castCount = cast.length;
    const [currentIndex, setCurrentIndex] = useState(0);
    const containerRef = useRef();

    const nextSlide = () => {
        setCurrentIndex(prevIndex => (prevIndex + 1) % castCount);
    };

    const prevSlide = () => {
        setCurrentIndex(prevIndex => (prevIndex - 1 + castCount) % castCount);
    };

    useEffect(() => {
        const handleResize = () => {
            setCurrentIndex(0); // Reset the slider on resize
        };
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    if (!cast.length) {
        return <Loader />;
    }

    const itemWidth = 160;
    const totalWidth = itemWidth * castCount;

    return (
        <Wrapper>
            <CarouselContainer ref={containerRef}>
                <CarouselSlider
                    style={{
                        width: `${totalWidth}px`,
                        transform: `translateX(-${currentIndex * itemWidth}px)`,
                    }}
                >
                    {cast.map(actor => (
                        <CarouselItem key={actor.name}>
                            <CastItem actor={actor}/>
                        </CarouselItem>
                    ))}
                </CarouselSlider>

                <ArrowLeft onClick={prevSlide}>
                    &#10094;
                </ArrowLeft>

                <ArrowRight onClick={nextSlide}>
                    &#10095;
                </ArrowRight>
            </CarouselContainer>
        </Wrapper>
    );
};

// PropTypes validation for props
Cast.propTypes = {
    castData: PropTypes.oneOfType([
        PropTypes.arrayOf(PropTypes.object),
        PropTypes.shape({
            actors: PropTypes.arrayOf(PropTypes.object),
        }),
    ]).isRequired,
};

export default Cast;
