import React, { useEffect, useRef } from 'react';
import styled from 'styled-components';

const MessageListContainer = styled.ul`
    list-style: none;
    padding: 10px;
    margin: 0;
    flex-grow: 1;
    overflow-y: auto;
    display: flex;
    flex-direction: column; 
`;

const MessageItem = styled.li`
    padding: 10px;
    margin-bottom: 15px;
    background-color: ${({ $isSender, theme }) => ($isSender ? theme.colors.senderMessage : theme.colors.receiverMessage)};
    border-radius: 15px;
    align-self: ${({ $isSender }) => ($isSender ? 'flex-end' : 'flex-start')};
    max-width: 70%;
    font-size: 10px;
    word-wrap: break-word;
    display: flex;
    flex-direction: column;
`;


const MessageList = ({ messagesFromSender, messagesFromReceiver, senderId, isMutual }) => {
    const messageListEndRef = useRef(null);

    // Scroll to bottom when messages change
    useEffect(() => {
        messageListEndRef.current?.scrollIntoView({ behavior: 'instant' });
    }, [messagesFromSender, messagesFromReceiver]);

    const sortedMessages = [...messagesFromSender, ...messagesFromReceiver]
        .sort((a, b) => new Date(a.timestamp) - new Date(b.timestamp));

    if (!isMutual) {
        return <MessageItem>You are not mutual friends. Wait for your friend to add you back!</MessageItem>;
    }

    if (!sortedMessages || sortedMessages.length === 0) {
        return <MessageItem>No messages yet.</MessageItem>;
    }

    return (
        <MessageListContainer>
            {sortedMessages.map((msg, index) => (
                <MessageItem key={index} $isSender={msg.senderId === senderId}>
                    {msg.content}
                </MessageItem>
            ))}
            <div ref={messageListEndRef} />
        </MessageListContainer>
    );
};

export default MessageList;
