import React, { useState, useRef, useEffect } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

library.add(faSearch);

const greyColor = '#cbcbcb';
const blackColor = '#000';

const Form = styled.form`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 8px var(--shadow-color);
  background-color: ${greyColor};
  border: 1px solid ${blackColor};
  width: ${props => (props.open ? '30rem' : '2rem')};
  cursor: ${props => (props.open ? 'auto' : 'pointer')};
  padding: 2rem;
  height: 2rem;
  outline: none;
  border-radius: 10rem;
  transition: all 300ms cubic-bezier(0.645, 0.045, 0.355, 1);

  @media ${props => props.theme.mediaQueries.large} {
    padding: 1.5rem;
  }

  @media ${props => props.theme.mediaQueries.smallest} {
    max-width: 25rem;
  }
`;

const Input = styled.input.attrs({
  type: 'text',
})`
  font-size: 14px;
  line-height: 1;
  font-weight: 300;
  background-color: transparent;
  width: 100%;
  margin-left: ${props => (props.open ? '1rem' : '0rem')};
  color: ${blackColor};
  border: none;
  transition: all 300ms cubic-bezier(0.645, 0.045, 0.355, 1);

  @media ${props => props.theme.mediaQueries.large} {
    font-size: 13px;
  }

  @media ${props => props.theme.mediaQueries.medium} {
    font-size: 12px;
  }

  @media ${props => props.theme.mediaQueries.small} {
    font-size: 11px;
  }

  &:focus,
  &:active {
    outline: none;
  }

  &::placeholder {
    color: ${blackColor};
  }
`;

const Button = styled.button`
  line-height: 1;
  pointer-events: ${props => (props.open ? 'auto' : 'none')};
  cursor: ${props => (props.open ? 'pointer' : 'none')};
  background-color: transparent;
  border: none;
  outline: none;
  color: ${blackColor};

  @media ${props => props.theme.mediaQueries.large} {
    font-size: 10px;
  }

  @media ${props => props.theme.mediaQueries.small} {
    font-size: 8px;
  }
`;

const SearchBar = () => {
  const [input, setInput] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const node = useRef();
  const inputFocus = useRef();
  const navigate = useNavigate();

  useEffect(() => {
    document.addEventListener('mousedown', handleClick);
    return () => {
      document.removeEventListener('mousedown', handleClick);
    };
  }, []);

  const handleClick = e => {
    if (node.current.contains(e.target)) {
      return;
    }
    setIsOpen(false);
  };

  // Handle form submission
  const onFormSubmit = e => {
    e.preventDefault();
    if (input.length === 0) {
      return;
    }
    setInput('');
    setIsOpen(false);
    navigate(`/search/${encodeURIComponent(input)}`);
  };

  return (
      <Form
          open={isOpen}
          onClick={() => {
            setIsOpen(true);
            inputFocus.current.focus();
          }}
          onSubmit={onFormSubmit}
          ref={node}
      >
        <Button open={isOpen}>
          <FontAwesomeIcon icon="search" size="1x" />
        </Button>
        <Input
            onChange={e => setInput(e.target.value)}
            ref={inputFocus}
            value={input}
            open={isOpen}
            placeholder="Search for a movie..."
        />
      </Form>
  );
};

export default SearchBar;
