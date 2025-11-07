package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.PasswordResetTokenRequestDTO;
import com.senac.projectmanagement.dto.PasswordResetTokenResponseDTO;
import com.senac.projectmanagement.service.PasswordResetTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/password-reset-tokens")
@Tag(name = "Password Reset Token Management", description = "APIs for managing password reset tokens")
public class PasswordResetTokenController {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @PostMapping
    @Operation(summary = "Create a new password reset token")
    public ResponseEntity<PasswordResetTokenResponseDTO> createPasswordResetToken(@Valid @RequestBody PasswordResetTokenRequestDTO requestDTO) {
        PasswordResetTokenResponseDTO responseDTO = passwordResetTokenService.createPasswordResetToken(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all password reset tokens")
    public ResponseEntity<List<PasswordResetTokenResponseDTO>> getAllPasswordResetTokens() {
        List<PasswordResetTokenResponseDTO> tokens = passwordResetTokenService.getAllPasswordResetTokens();
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get password reset token by ID")
    public ResponseEntity<PasswordResetTokenResponseDTO> getPasswordResetTokenById(@PathVariable Long id) {
        return passwordResetTokenService.getPasswordResetTokenById(id)
                .map(token -> new ResponseEntity<>(token, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get password reset tokens by user ID")
    public ResponseEntity<List<PasswordResetTokenResponseDTO>> getPasswordResetTokensByUser(@PathVariable Long userId) {
        List<PasswordResetTokenResponseDTO> tokens = passwordResetTokenService.getPasswordResetTokensByUser(userId);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @PutMapping("/mark-used/{token}")
    @Operation(summary = "Mark password reset token as used")
    public ResponseEntity<PasswordResetTokenResponseDTO> markTokenAsUsed(@PathVariable String token) {
        try {
            PasswordResetTokenResponseDTO responseDTO = passwordResetTokenService.markTokenAsUsedDTO(token);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete password reset token by ID")
    public ResponseEntity<Void> deletePasswordResetToken(@PathVariable Long id) {
        passwordResetTokenService.deletePasswordResetToken(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
