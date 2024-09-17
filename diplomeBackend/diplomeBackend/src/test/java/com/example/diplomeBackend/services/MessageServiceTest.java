package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.MessageDTO;
import com.example.diplomeBackend.mappers.MessageMapper;
import com.example.diplomeBackend.models.Message;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.repositories.MessageRepository;
import com.example.diplomeBackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageMapper messageMapper;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage() {
        Long senderId = 1L;
        Long receiverId = 2L;
        String content = "Hello";

        User sender = new User();
        sender.setUserId(senderId);

        User receiver = new User();
        receiver.setUserId(receiverId);

        Message savedMessage = new Message(sender, receiver, content, LocalDateTime.now());
        savedMessage.setId(1L);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(content);

        when(userRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
        when(messageRepository.save(any(Message.class))).thenReturn(savedMessage);
        when(messageMapper.toDTO(savedMessage)).thenReturn(messageDTO);

        MessageDTO result = messageService.sendMessage(senderId, receiverId, content);

        assertNotNull(result);
        assertEquals(content, result.getContent());

        verify(messageRepository, times(1)).save(any(Message.class));
        verify(messagingTemplate, times(1)).convertAndSendToUser(String.valueOf(receiverId), "/reply", messageDTO);
    }

    @Test
    void testSendMessageUserNotFound() {
        Long senderId = 1L;
        Long receiverId = 2L;
        String content = "Hello";

        when(userRepository.findById(senderId)).thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            messageService.sendMessage(senderId, receiverId, content);
        });

        assertEquals("User not found with id: " + senderId, thrown.getMessage());
    }

    @Test
    void testGetMessages() {
        User sender = new User();
        User receiver = new User();

        Message message1 = new Message(sender, receiver, "Hello", LocalDateTime.now());
        Message message2 = new Message(sender, receiver, "Hi", LocalDateTime.now().minusDays(1));

        MessageDTO messageDTO1 = new MessageDTO();
        messageDTO1.setContent("Hello");

        MessageDTO messageDTO2 = new MessageDTO();
        messageDTO2.setContent("Hi");

        when(messageRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(List.of(message1, message2));
        when(messageMapper.toDTO(message1)).thenReturn(messageDTO1);
        when(messageMapper.toDTO(message2)).thenReturn(messageDTO2);

        List<MessageDTO> result = messageService.getMessages(sender, receiver);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getContent().equals("Hello")));
        assertTrue(result.stream().anyMatch(dto -> dto.getContent().equals("Hi")));
    }
}
