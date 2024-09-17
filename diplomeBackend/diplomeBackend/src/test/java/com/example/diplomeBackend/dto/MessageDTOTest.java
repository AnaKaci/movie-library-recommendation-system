package com.example.diplomeBackend.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageDTOTest {

    @Test
    void testMessageDTOConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        Long senderId = 2L;
        Long receiverId = 3L;
        String content = "Hello, World!";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        MessageDTO messageDTO = new MessageDTO(id, senderId, receiverId, content, timestamp);

        // Assert
        assertEquals(id, messageDTO.getId());
        assertEquals(senderId, messageDTO.getSenderId());
        assertEquals(receiverId, messageDTO.getReceiverId());
        assertEquals(content, messageDTO.getContent());
        assertEquals(timestamp, messageDTO.getTimestamp());
    }

    @Test
    void testMessageDTOSetters() {
        // Arrange
        MessageDTO messageDTO = new MessageDTO();
        Long id = 1L;
        Long senderId = 2L;
        Long receiverId = 3L;
        String content = "Test message";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        messageDTO.setId(id);
        messageDTO.setSenderId(senderId);
        messageDTO.setReceiverId(receiverId);
        messageDTO.setContent(content);
        messageDTO.setTimestamp(timestamp);

        // Assert
        assertEquals(id, messageDTO.getId());
        assertEquals(senderId, messageDTO.getSenderId());
        assertEquals(receiverId, messageDTO.getReceiverId());
        assertEquals(content, messageDTO.getContent());
        assertEquals(timestamp, messageDTO.getTimestamp());
    }
}
