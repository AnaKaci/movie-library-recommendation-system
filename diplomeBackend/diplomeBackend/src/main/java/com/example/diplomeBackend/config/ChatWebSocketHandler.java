package com.example.diplomeBackend.config;

import com.example.diplomeBackend.models.Message;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Autowired
    private MessageRepository messageRepository;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Save the message to the database
        Message newMessage = new Message();
        newMessage.setContent(message.getPayload());
        newMessage.setSender(getSenderFromSession(session));
        newMessage.setTimestamp(LocalDateTime.now());

        messageRepository.save(newMessage);

        // Broadcast the received message to all connected clients
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(message);
            }
        }
    }

    private User getSenderFromSession(WebSocketSession session) {
        // Assuming you have the User stored as an attribute in the WebSocket session
        return (User) session.getAttributes().get("user");
    }
}
