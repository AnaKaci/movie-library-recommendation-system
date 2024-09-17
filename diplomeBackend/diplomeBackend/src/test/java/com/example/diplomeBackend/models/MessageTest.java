package com.example.diplomeBackend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MessageTest {

    @Test
    public void testMessageDefaultConstructor() {
        Message message = new Message();
        assertNotNull(message);
        assertNull(message.getId());
        assertNull(message.getSender());
        assertNull(message.getReceiver());
        assertNull(message.getContent());
        assertNull(message.getTimestamp());
    }

    @Test
    public void testMessageParameterizedConstructor() {
        User sender = new User();
        User receiver = new User();
        String content = "Hello!";
        LocalDateTime timestamp = LocalDateTime.now();

        Message message = new Message(sender, receiver, content, timestamp);

        assertNotNull(message);
        assertEquals(sender, message.getSender());
        assertEquals(receiver, message.getReceiver());
        assertEquals(content, message.getContent());
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    public void testMessageSettersAndGetters() {
        Message message = new Message();
        User sender = new User();
        User receiver = new User();
        String content = "Test content";
        LocalDateTime timestamp = LocalDateTime.now();

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(timestamp);

        assertEquals(sender, message.getSender());
        assertEquals(receiver, message.getReceiver());
        assertEquals(content, message.getContent());
        assertEquals(timestamp, message.getTimestamp());
    }
}
