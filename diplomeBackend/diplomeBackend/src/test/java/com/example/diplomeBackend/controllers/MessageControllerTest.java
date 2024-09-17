package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.MessageDTO;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.services.FriendsService;
import com.example.diplomeBackend.services.MessageService;
import com.example.diplomeBackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageService messageService;

    @Mock
    private FriendsService friendsService;

    @Mock
    private UserService userService;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage_Success() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSenderId(1L);
        messageDTO.setReceiverId(2L);
        messageDTO.setContent("Hello");

        when(friendsService.areMutualFriends(anyLong(), anyLong())).thenReturn(true);
        when(messageService.sendMessage(anyLong(), anyLong(), anyString())).thenReturn(messageDTO);

        MessageDTO response = messageController.sendMessage(messageDTO);

        verify(simpMessagingTemplate).convertAndSendToUser(
                messageDTO.getReceiverId().toString(),
                "/queue/messages",
                messageDTO
        );
        assertEquals(messageDTO, response);
    }

    @Test
    public void testSendMessage_NotMutualFriends() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSenderId(1L);
        messageDTO.setReceiverId(2L);
        messageDTO.setContent("Hello");

        when(friendsService.areMutualFriends(anyLong(), anyLong())).thenReturn(false);

        try {
            messageController.sendMessage(messageDTO);
        } catch (RuntimeException e) {
            assertEquals("Users are not mutual friends.", e.getMessage());
        }
    }

    @Test
    public void testGetMessages_Success() {
        User sender = new User();
        User receiver = new User();
        MessageDTO messageDTO = new MessageDTO();
        List<MessageDTO> messages = Collections.singletonList(messageDTO);

        when(userService.findUserById(anyLong())).thenReturn(sender, receiver);
        when(messageService.getMessages(any(User.class), any(User.class))).thenReturn(messages);

        ResponseEntity<List<MessageDTO>> response = messageController.getMessages(1L, 2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
    }

    @Test
    public void testGetMessages_SenderNotFound() {
        when(userService.findUserById(anyLong())).thenReturn(null);

        ResponseEntity<List<MessageDTO>> response = messageController.getMessages(1L, 2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetMessages_ReceiverNotFound() {
        User sender = new User();
        when(userService.findUserById(anyLong())).thenReturn(sender, null);

        ResponseEntity<List<MessageDTO>> response = messageController.getMessages(1L, 2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetMessages_NoContent() {
        User sender = new User();
        User receiver = new User();
        when(userService.findUserById(anyLong())).thenReturn(sender, receiver);
        when(messageService.getMessages(any(User.class), any(User.class))).thenReturn(Collections.emptyList());

        ResponseEntity<List<MessageDTO>> response = messageController.getMessages(1L, 2L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }
}
