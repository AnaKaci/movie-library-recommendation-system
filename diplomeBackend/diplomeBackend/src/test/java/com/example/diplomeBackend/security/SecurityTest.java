package com.example.diplomeBackend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class SecurityTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void testSecurityConfig() throws Exception {
        // Setup MockMvc with the WebApplicationContext
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Test if unauthenticated requests to /api/** are allowed
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test-endpoint"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Test if unauthenticated requests to other endpoints are denied
        mockMvc.perform(MockMvcRequestBuilders.get("/secure-endpoint"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        // Test PasswordEncoder
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), "Password should match the encoded password");

        // Test UserDetailsService
        assertNotNull(userDetailsService, "UserDetailsService bean should be initialized");
    }
}
