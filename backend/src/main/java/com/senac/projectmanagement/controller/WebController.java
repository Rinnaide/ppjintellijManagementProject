package com.senac.projectmanagement.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@CrossOrigin("*")
@RestController
public class WebController {
    @GetMapping(value = "/home", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> home() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/Web.html");
        String htmlContent = new String(Files.readAllBytes(resource.getFile().toPath()));
        return ResponseEntity.ok(htmlContent);
    }
}
