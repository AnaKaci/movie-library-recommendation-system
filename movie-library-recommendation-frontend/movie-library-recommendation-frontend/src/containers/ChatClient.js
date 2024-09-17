import React, {useState, useEffect, useCallback} from 'react';
import styled, {useTheme} from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faComments, faTimes } from '@fortawesome/free-solid-svg-icons';
import { Client as StompClient } from '@stomp/stompjs';
import {
    addMessage,
    fetchAllFriends,
    fetchMessagesFromReceiver,
    fetchMessagesFromSender,
    isMutualFriends, setMessages
} from "../actions";
import MessageList from "../components/MessageList";

const getTextColor = (theme) => theme.colors.text === '#000000' ? '#ffffff' : '#000000';

const ChatContainer = styled.div`
    display: ${(props) => (props.$isVisible ? 'flex' : 'none')};  
    flex-direction: column;
    width: 350px;  
    height: 500px;  
    border: none;
    border-radius: 5px;  
    overflow: hidden;
    position: fixed;
    bottom: 20px;  
    right: 20px;
    background-color: ${({ theme }) => theme.colors.background};
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1); 
    transition: all 0.3s ease-in-out;  
`;


const ChatContent = styled.div`
    flex: 1;  
    overflow-y: auto;  
    padding: 20px 15px;
    background-color: ${({ theme }) => theme.colors.background};
    box-shadow: inset 0px 0px 5px rgba(0, 0, 0, 0.05);
    border-bottom: 1px solid ${({ theme }) => theme.colors.border};
    border-radius: 15px 15px 0 0;  
`;


const MessageInput = styled.input`
    border: none;
    padding: 10px;  
    border-radius: 0 0 0 10px;  
    width: calc(100% - 60px);  
    box-sizing: border-box;  
    font-size: 10px;
    background-color: ${({ theme }) => getTextColor(theme)};
    color: ${({ theme }) => theme.colors.text};  
    outline: none;  
    transition: all 0.3s ease-in-out;

    &:focus {
        box-shadow: 0 0 5px rgba(0, 123, 255, 0.2);  
    }
`;


const SendButton = styled.button`
    border: none;
    border-radius: 10px;
    background-color: #0b643c;
    color: white;
    padding: 10px;
    cursor: pointer;
    width: 60px;  
    box-sizing: border-box;  
    font-size: 10px;
    transition: all 0.3s ease-in-out;

    &:hover {
        background-color: #0f7f4c;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    }

    &:active {
        background-color: #095231;
    }
`;


const FriendList = styled.select`
    border: none;
    border-radius: 5px;
    padding: 10px;
    margin-bottom: 10px;
    width: 100%;
    background-color: #f7f9fc;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    font-size: 10px;
    color: #333;
    outline: none;
    transition: all 0.3s ease-in-out;

    &:focus {
        background-color: white;
        box-shadow: 0 0 5px rgba(0, 123, 255, 0.2);
    }
`;


const OpenChatButton = styled.button`
    position: fixed;
    bottom: 20px;
    right: 20px;
    background-color: #0b643c;
    color: white;
    width: 55px; 
    height: 55px;
    border: none;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    font-size: 24px; // Adjust size for the icon
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);r;
`;

const CloseButton = styled.button`
    background-color: transparent;  
    border: none;
    color: black;  
    font-size: 20px;  
    position: absolute;
    top: 0.5px;  
    right: 10px;  
    cursor: pointer;
    z-index: 1000;  

    &:hover {
        color: red;  
    }
`;


const ChatClient = ({ senderId }) => {
    const dispatch = useDispatch();

    const [isChatVisible, setIsChatVisible] = useState(false);
    const [receiverId, setReceiverId] = useState('');
    const [message, setMessage] = useState('');
    const [stompClient, setStompClient] = useState(null);
    const [isConnected, setIsConnected] = useState(false);

    const friends = useSelector((state) => state.friend.friends || []);
    const messagesFromSender = useSelector((state) => state.usermessage.messagesFromSender || []);
    const messagesFromReceiver = useSelector((state) => state.usermessage.messagesFromReceiver || []);
    const isMutualFriend = useSelector((state) => state.friend.mutualFriends);
    const isMutualFriendLoading = useSelector((state) => state.friend.loading);

    useEffect(() => {
        dispatch(fetchAllFriends(senderId));
    }, [dispatch, senderId]);

    useEffect(() => {
        const fetchMessages = () => {
        if (receiverId) {
            dispatch(isMutualFriends(senderId, receiverId));
            dispatch(fetchMessagesFromSender(senderId, receiverId));
            dispatch(fetchMessagesFromReceiver(receiverId, senderId));
        }
        };
        fetchMessages();
        const interval = setInterval(fetchMessages, 5000);
        return () => clearInterval(interval);
    }, [dispatch, senderId, receiverId]);


    useEffect(() => {
        const socket = new SockJS('http://localhost:8082/ws');
        const client = new StompClient({
            webSocketFactory: () => socket,
            onConnect: () => {
                setIsConnected(true);
                client.subscribe(`/user/${senderId}/queue/messages`, (message) => {
                    const receivedMessage = JSON.parse(message.body);
                    dispatch(addMessage(receivedMessage, senderId, receiverId));
                });
            },
            onDisconnect: () => setIsConnected(false),
        });
        client.activate();
        setStompClient(client);

        return () => {
            client.deactivate();
        };
    }, [dispatch, senderId, receiverId]);

    // Function to toggle chat window
    const toggleChat = () => {
        setIsChatVisible((prev) => !prev);
    };

    const handleSendMessage = () => {
        if (message.trim() && isConnected && receiverId && isMutualFriend) {
            const newMessage = { senderId, receiverId, content: message };

            // Send message via WebSocket
            stompClient.publish({
                destination: '/app/message',
                body: JSON.stringify(newMessage),
            });

            dispatch(setMessages(newMessage, senderId, receiverId));
            setMessage('');
        }
    };

    const theme = useTheme();

    return (
        <>
            <OpenChatButton onClick={toggleChat}>
                <FontAwesomeIcon icon={isChatVisible ? faTimes : faComments} />
            </OpenChatButton>

            {isChatVisible && (
                <ChatContainer $isVisible={isChatVisible}>
                    <CloseButton onClick={toggleChat}>
                        <FontAwesomeIcon icon={faTimes}/>
                    </CloseButton>

                    <div style={{
                        padding: '10px',
                        fontSize: '10px',
                        color: theme.colors.text,
                        textAlign: 'center',
                        fontWeight: 'bold',
                        backgroundColor: theme.colors.background
                    }}>
                        Select a friend
                    </div>

                    <FriendList onChange={(e) => setReceiverId(e.target.value)} value={receiverId}>
                        <option value="" disabled>Select a friend</option>
                        {friends.map((friend) => (
                            <option key={friend.id} value={friend.id}>
                            {friend.username}
                            </option>
                        ))}
                    </FriendList>

                    {isMutualFriendLoading ? (
                        <p>Checking mutual friends...</p>
                    ) : (
                        <>
                            <ChatContent>
                                <MessageList
                                    messagesFromSender={messagesFromSender}
                                    messagesFromReceiver={messagesFromReceiver}
                                    isMutual={isMutualFriend}
                                    senderId={senderId}
                                />
                            </ChatContent>
                            <div style={{display: 'flex'}}>
                                <MessageInput
                                    type="text"
                                    value={message}
                                    onChange={(e) => setMessage(e.target.value)}
                                    onKeyDown={(e) => e.key === 'Enter' && isMutualFriend && handleSendMessage()}
                                    disabled={!isMutualFriend}
                                />
                                <SendButton
                                    onClick={handleSendMessage}
                                    disabled={!isMutualFriend}
                                >
                                    Send
                                </SendButton>
                            </div>
                        </>
                    )}
                </ChatContainer>
            )}
        </>
    );
};

export default ChatClient;
