package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.RefreshTokenRequestDTO;
import com.senac.projectmanagement.dto.RefreshTokenResponseDTO;
import com.senac.projectmanagement.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/refresh-tokens")
@Tag(name = "Refresh Token Management", description = "APIs for managing refresh tokens")
public class RefreshTokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping
    @Operation(summary = "Create a new refresh token")
    public ResponseEntity<RefreshTokenResponseDTO> createRefreshToken(@Valid @RequestBody RefreshTokenRequestDTO requestDTO) {
        RefreshTokenResponseDTO responseDTO = refreshTokenService.createRefreshToken(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all refresh tokens")
    public ResponseEntity<List<RefreshTokenResponseDTO>> getAllRefreshTokens() {
        List<RefreshTokenResponseDTO> tokens = refreshTokenService.getAllRefreshTokens();
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get refresh token by ID")
    public ResponseEntity<RefreshTokenResponseDTO> getRefreshTokenById(@PathVariable Long id) {
        return refreshTokenService.getRefreshTokenById(id)
                .map(token -> new ResponseEntity<>(token, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get refresh tokens by user ID")
    public ResponseEntity<List<RefreshTokenResponseDTO>> getRefreshTokensByUser(@PathVariable Long userId) {
        List<RefreshTokenResponseDTO> tokens = refreshTokenService.getRefreshTokensByUser(userId);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @PutMapping("/revoke/{token}")
    @Operation(summary = "Revoke refresh token")
    public ResponseEntity<RefreshTokenResponseDTO> revokeToken(@PathVariable String token) {
        try {
            RefreshTokenResponseDTO responseDTO = refreshTokenService.revokeTokenDTO(token);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete refresh token by ID")
    public ResponseEntity<Void> deleteRefreshToken(@PathVariable Long id) {
        refreshTokenService.deleteRefreshToken(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
