import React from 'react';
import styled from 'styled-components';
import Button from "./Button";
import {faClose} from "@fortawesome/free-solid-svg-icons";

const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: ${({ theme }) => `${theme.colors.dark}80`};
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
`;

const ModalContent = styled.div`
  background: ${({ theme }) => theme.colors.background};
  padding: 1rem;
  border-radius: 0.5rem;
  max-width: 705px;
  max-height: 485px;
  height: 100%;  
  width: 100%;
  position: relative;  
`;

const ButtonWrapper = styled.div`
  position: absolute;
  bottom: 1rem;
  right: 1rem;
`;


const MovieModal = ({ children, onClose }) => (
    <ModalOverlay onClick={onClose}>
        <ModalContent onClick={(e) => e.stopPropagation()}>
            {children}
            <ButtonWrapper>
            <Button
                title={'Close'}
                icon={faClose}
                solid={true}
                onClick={onClose}
            /></ButtonWrapper>
        </ModalContent>
    </ModalOverlay>
);

export default MovieModal;
