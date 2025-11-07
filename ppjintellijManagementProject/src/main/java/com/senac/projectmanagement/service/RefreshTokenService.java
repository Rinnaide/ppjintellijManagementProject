package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.RefreshTokenRequestDTO;
import com.senac.projectmanagement.dto.RefreshTokenResponseDTO;
import com.senac.projectmanagement.entity.RefreshToken;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.repository.RefreshTokenRepository;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            LocalDateTime expiresAt = LocalDateTime.now().plusDays(30); // Expires in 30 days
            RefreshToken refreshToken = new RefreshToken(user.get(), token, expiresAt);
            return refreshTokenRepository.save(refreshToken);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public Optional<RefreshToken> getValidToken(String token) {
        return refreshTokenRepository.findByRefreshTokenTokenAndRefreshTokenRevokedFalseAndRefreshTokenExpiresAtAfter(token, LocalDateTime.now());
    }

    public void revokeToken(String token) {
        Optional<RefreshToken> optionalToken = refreshTokenRepository.findByRefreshTokenTokenAndRefreshTokenRevokedFalseAndRefreshTokenExpiresAtAfter(token, LocalDateTime.now());
        if (optionalToken.isPresent()) {
            RefreshToken refreshToken = optionalToken.get();
            refreshToken.setRefreshTokenRevoked(true);
            refreshTokenRepository.save(refreshToken);
        }
    }

    public void revokeAllUserTokens(Long userId) {
        // Note: No bulk update method in repository, so this would need to be implemented differently or added to repository
        // For now, this is a placeholder
    }

    public RefreshTokenResponseDTO createRefreshToken(RefreshTokenRequestDTO requestDTO) {
        Optional<User> user = userRepository.findById(requestDTO.getUserId());
        if (user.isPresent()) {
            String token = requestDTO.getToken() != null ? requestDTO.getToken() : UUID.randomUUID().toString();
            LocalDateTime expiresAt = requestDTO.getExpiresAt() != null ? requestDTO.getExpiresAt() : LocalDateTime.now().plusDays(30);
            RefreshToken refreshToken = new RefreshToken(user.get(), token, expiresAt);
            RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
            return convertToResponseDTO(savedToken);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public List<RefreshTokenResponseDTO> getAllRefreshTokens() {
        List<RefreshToken> tokens = refreshTokenRepository.findAll();
        return tokens.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<RefreshTokenResponseDTO> getRefreshTokenById(Long id) {
        return refreshTokenRepository.findById(id).map(this::convertToResponseDTO);
    }

    public List<RefreshTokenResponseDTO> getRefreshTokensByUser(Long userId) {
        List<RefreshToken> tokens = refreshTokenRepository.findByUser_UserId(userId);
        return tokens.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public RefreshTokenResponseDTO revokeTokenDTO(String token) {
        Optional<RefreshToken> optionalToken = refreshTokenRepository.findByRefreshTokenTokenAndRefreshTokenRevokedFalseAndRefreshTokenExpiresAtAfter(token, LocalDateTime.now());
        if (optionalToken.isPresent()) {
            RefreshToken refreshToken = optionalToken.get();
            refreshToken.setRefreshTokenRevoked(true);
            RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
            return convertToResponseDTO(savedToken);
        }
        throw new RuntimeException("Token inválido ou expirado");
    }

    public void deleteRefreshToken(Long id) {
        refreshTokenRepository.deleteById(id);
    }

    private RefreshTokenResponseDTO convertToResponseDTO(RefreshToken token) {
        RefreshTokenResponseDTO dto = new RefreshTokenResponseDTO();
        dto.setRefreshTokenId(token.getRefreshTokenId());
        dto.setUserId(token.getUser().getUserId());
        dto.setToken(token.getRefreshTokenToken());
        dto.setExpiresAt(token.getRefreshTokenExpiresAt());
        dto.setRevoked(token.getRefreshTokenRevoked());
        dto.setCreatedAt(token.getRefreshTokenCreatedAt());
        return dto;
    }
}
