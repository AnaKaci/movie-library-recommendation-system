package com.example.diplomeBackend.controllers;

import com.example.diplomeBackend.config.WebConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConfigController {

    @GetMapping("/configuration")
    public ResponseEntity<WebConfig> getConfiguration() {
        WebConfig config = new WebConfig();
        return ResponseEntity.ok(config);
    }
}
