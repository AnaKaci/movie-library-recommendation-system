package com.example.diplomeBackend.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class WebSocketConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testWebSocketConfiguration() {
        // Verify that the WebSocketConfig is loaded
        assertTrue(applicationContext.getBean(WebSocketMessageBrokerConfigurer.class) instanceof WebSocketConfig,
                "WebSocketConfig bean should be loaded");

        WebSocketClient client = new StandardWebSocketClient();
        try {
            WebSocketSession session = client.doHandshake(new WebSocketHandler() {
                @Override
                public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                }

                @Override
                public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                }

                @Override
                public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                }

                @Override
                public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                }

                @Override
                public boolean supportsPartialMessages() {
                    return false;
                }
            }, "ws://localhost:8080/ws").get();

            assertTrue(true, "WebSocket connection should be successful");

        } catch (Exception e) {
            fail("WebSocket connection failed: " + e.getMessage());
        }
    }
}
