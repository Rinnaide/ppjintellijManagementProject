package com.senac.projectmanagement.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class WebController {
    @GetMapping(value = "/home", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> home() {
        String htmlContent = "<!DOCTYPE html><html><head><title>Test</title></head><body><h1>Hello World from /home endpoint</h1></body></html>";
        return ResponseEntity.ok(htmlContent);
    }
}
