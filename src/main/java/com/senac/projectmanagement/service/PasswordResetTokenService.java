package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.PasswordResetTokenRequestDTO;
import com.senac.projectmanagement.dto.PasswordResetTokenResponseDTO;
import com.senac.projectmanagement.entity.PasswordResetToken;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.repository.PasswordResetTokenRepository;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public PasswordResetToken createPasswordResetToken(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            LocalDateTime expiresAt = LocalDateTime.now().plusHours(24); // Expires in 24 hours
            PasswordResetToken resetToken = new PasswordResetToken(user.get(), token, expiresAt);
            return passwordResetTokenRepository.save(resetToken);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public Optional<PasswordResetToken> getValidToken(String token) {
        return passwordResetTokenRepository.findByPasswordResetTokenTokenAndPasswordResetTokenUsedFalseAndPasswordResetTokenExpiresAtAfter(token, LocalDateTime.now());
    }

    public void markTokenAsUsed(String token) {
        Optional<PasswordResetToken> optionalToken = passwordResetTokenRepository.findByPasswordResetTokenTokenAndPasswordResetTokenUsedFalseAndPasswordResetTokenExpiresAtAfter(token, LocalDateTime.now());
        if (optionalToken.isPresent()) {
            PasswordResetToken resetToken = optionalToken.get();
            resetToken.setPasswordResetTokenUsed(true);
            passwordResetTokenRepository.save(resetToken);
        }
    }

    public void deleteExpiredTokens() {
        // Note: No delete method in repository, so this would need to be implemented differently or added to repository
        // For now, this is a placeholder
    }

    public PasswordResetTokenResponseDTO createPasswordResetToken(PasswordResetTokenRequestDTO requestDTO) {
        Optional<User> user = userRepository.findById(requestDTO.getUserId());
        if (user.isPresent()) {
            String token = requestDTO.getToken() != null ? requestDTO.getToken() : UUID.randomUUID().toString();
            LocalDateTime expiresAt = requestDTO.getExpiresAt() != null ? requestDTO.getExpiresAt() : LocalDateTime.now().plusHours(24);
            PasswordResetToken resetToken = new PasswordResetToken(user.get(), token, expiresAt);
            PasswordResetToken savedToken = passwordResetTokenRepository.save(resetToken);
            return convertToResponseDTO(savedToken);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public List<PasswordResetTokenResponseDTO> getAllPasswordResetTokens() {
        List<PasswordResetToken> tokens = passwordResetTokenRepository.findAll();
        return tokens.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<PasswordResetTokenResponseDTO> getPasswordResetTokenById(Long id) {
        return passwordResetTokenRepository.findById(id).map(this::convertToResponseDTO);
    }

    public List<PasswordResetTokenResponseDTO> getPasswordResetTokensByUser(Long userId) {
        List<PasswordResetToken> tokens = passwordResetTokenRepository.findByUser_UserId(userId);
        return tokens.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public PasswordResetTokenResponseDTO markTokenAsUsedDTO(String token) {
        Optional<PasswordResetToken> optionalToken = passwordResetTokenRepository.findByPasswordResetTokenTokenAndPasswordResetTokenUsedFalseAndPasswordResetTokenExpiresAtAfter(token, LocalDateTime.now());
        if (optionalToken.isPresent()) {
            PasswordResetToken resetToken = optionalToken.get();
            resetToken.setPasswordResetTokenUsed(true);
            PasswordResetToken savedToken = passwordResetTokenRepository.save(resetToken);
            return convertToResponseDTO(savedToken);
        }
        throw new RuntimeException("Token inválido ou expirado");
    }

    public void deletePasswordResetToken(Long id) {
        passwordResetTokenRepository.deleteById(id);
    }

    private PasswordResetTokenResponseDTO convertToResponseDTO(PasswordResetToken token) {
        PasswordResetTokenResponseDTO dto = new PasswordResetTokenResponseDTO();
        dto.setPasswordResetTokenId(token.getPasswordResetTokenId());
        dto.setUserId(token.getUser().getUserId());
        dto.setToken(token.getPasswordResetTokenToken());
        dto.setExpiresAt(token.getPasswordResetTokenExpiresAt());
        dto.setUsed(token.getPasswordResetTokenUsed());
        dto.setCreatedAt(token.getPasswordResetTokenCreatedAt());
        return dto;
    }
}
