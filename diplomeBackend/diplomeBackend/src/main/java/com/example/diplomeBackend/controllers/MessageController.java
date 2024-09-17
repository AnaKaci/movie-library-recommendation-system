package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.dto.MessageDTO;
import com.example.diplomeBackend.mappers.MessageMapper;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.services.FriendsService;
import com.example.diplomeBackend.services.MessageService;
import com.example.diplomeBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(MessageService messageService, FriendsService friendsService, UserService userService){
        this.messageService = messageService;
        this.friendsService = friendsService;
        this.userService = userService;
    }

    @MessageMapping("/message")
    //@SendTo("/user/{receiverId}/queue/reply")
    public MessageDTO sendMessage(MessageDTO messageDTO) {
        boolean areFriends = friendsService.areMutualFriends(messageDTO.getSenderId(), messageDTO.getReceiverId());
        if (!areFriends) {
            throw new RuntimeException("Users are not mutual friends.");
        }
        MessageDTO messageToSend = messageService.sendMessage(messageDTO.getSenderId(), messageDTO.getReceiverId(), messageDTO.getContent());
        simpMessagingTemplate.convertAndSendToUser(
                messageDTO.getReceiverId().toString(),
                "/queue/messages",
                messageToSend
        );
        return messageToSend;
    }


    @GetMapping("/conversation/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
        User sender = userService.findUserById(senderId);
        User receiver = userService.findUserById(receiverId);

        if (sender == null || receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<MessageDTO> messages = messageService.getMessages(sender, receiver);

        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(messages);
        }
        return ResponseEntity.ok(messages);
    }
}
