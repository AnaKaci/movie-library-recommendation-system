package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Message;
import com.example.diplomeBackend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    private User sender;
    private User receiver;
    private Message message1;
    private Message message2;

    @BeforeEach
    void setUp() {
        // Set up test data
        sender = new User();
        sender.setFirstName("John");
        sender.setLastName("Doe");
        sender.setEmail("john.doe@example.com");
        userRepository.save(sender);

        receiver = new User();
        receiver.setFirstName("Jane");
        receiver.setLastName("Doe");
        receiver.setEmail("jane.doe@example.com");
        userRepository.save(receiver);

        message1 = new Message();
        message1.setSender(sender);
        message1.setReceiver(receiver);
        message1.setContent("Hello, Jane!");
        message1.setTimestamp(LocalDateTime.now());
        messageRepository.save(message1);

        message2 = new Message();
        message2.setSender(sender);
        message2.setReceiver(receiver);
        message2.setContent("How are you?");
        message2.setTimestamp(LocalDateTime.now());
        messageRepository.save(message2);
    }

    @Test
    void testFindBySenderAndReceiver() {
        // Find messages by sender and receiver
        List<Message> messages = messageRepository.findBySenderAndReceiver(sender, receiver);

        assertNotNull(messages);
        assertEquals(2, messages.size()); // We added 2 messages in setUp

        // Verify the content of the messages
        assertTrue(messages.stream().anyMatch(m -> "Hello, Jane!".equals(m.getContent())));
        assertTrue(messages.stream().anyMatch(m -> "How are you?".equals(m.getContent())));
    }

    @Test
    void testFindBySenderAndReceiver_NoMessages() {
        // Create new users who have not exchanged messages
        User newUser = new User();
        newUser.setFirstName("Alice");
        newUser.setLastName("Smith");
        newUser.setEmail("alice.smith@example.com");
        userRepository.save(newUser);

        // Find messages by sender and receiver who have no messages
        List<Message> messages = messageRepository.findBySenderAndReceiver(newUser, receiver);

        assertNotNull(messages);
        assertTrue(messages.isEmpty()); // There should be no messages
    }
}
