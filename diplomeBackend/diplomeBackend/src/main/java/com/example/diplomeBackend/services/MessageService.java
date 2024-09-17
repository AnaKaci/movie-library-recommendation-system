package com.example.diplomeBackend.services;

import com.example.diplomeBackend.dto.MessageDTO;
import com.example.diplomeBackend.mappers.MessageMapper;
import com.example.diplomeBackend.models.Message;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.repositories.MessageRepository;
import com.example.diplomeBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public MessageDTO sendMessage(Long senderId, Long receiverId, String content) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + receiverId));

        Message message = new Message(sender, receiver, content, LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);

        MessageDTO messageDTO = messageMapper.toDTO(savedMessage);

        messagingTemplate.convertAndSendToUser(String.valueOf(receiverId), "/reply", messageDTO);

        return messageDTO;
    }


    public List<MessageDTO> getMessages(User sender, User receiver) {

        List<Message> messages = messageRepository.findBySenderAndReceiver(sender, receiver);

        return messages.stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }
}
