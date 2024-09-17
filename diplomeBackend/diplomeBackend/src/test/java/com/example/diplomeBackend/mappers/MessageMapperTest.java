package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MessageDTO;
import com.example.diplomeBackend.models.Message;
import com.example.diplomeBackend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessageMapperTest {

    private MessageMapper messageMapper;

    @BeforeEach
    void setUp() {
        messageMapper = Mappers.getMapper(MessageMapper.class);
    }

    @Test
    void testToDTO() {
        // Arrange
        User sender = new User();
        sender.setUserId(1L);
        User receiver = new User();
        receiver.setUserId(2L);

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent("Hello, world!");
        message.setTimestamp(LocalDateTime.now());

        // Act
        MessageDTO messageDTO = messageMapper.toDTO(message);

        // Assert
        assertNotNull(messageDTO);
        assertEquals(1L, messageDTO.getSenderId());
        assertEquals(2L, messageDTO.getReceiverId());
        assertEquals("Hello, world!", messageDTO.getContent());
        assertEquals(message.getTimestamp(), messageDTO.getTimestamp());
    }

    @Test
    void testToEntity() {
        // Arrange
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSenderId(1L);
        messageDTO.setReceiverId(2L);
        messageDTO.setContent("Hello, world!");
        messageDTO.setTimestamp(LocalDateTime.now());

        // Act
        Message message = messageMapper.toEntity(messageDTO);

        // Assert
        assertNotNull(message);
        assertEquals(1L, message.getSender().getUserId());
        assertEquals(2L, message.getReceiver().getUserId());
        assertEquals("Hello, world!", message.getContent());
        assertEquals(messageDTO.getTimestamp(), message.getTimestamp());
    }

    @Test
    void testMapUserId() {
        // Arrange
        Long userId = 1L;

        // Act
        User user = messageMapper.mapUserId(userId);

        // Assert
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
    }
}
