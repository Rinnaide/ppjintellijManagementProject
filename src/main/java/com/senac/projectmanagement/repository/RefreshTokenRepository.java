package com.senac.projectmanagement.repository;

import com.senac.projectmanagement.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser_UserIdAndRefreshTokenRevokedFalseAndRefreshTokenExpiresAtAfter(Long userId, LocalDateTime now);

    Optional<RefreshToken> findByRefreshTokenTokenAndRefreshTokenRevokedFalseAndRefreshTokenExpiresAtAfter(String token, LocalDateTime now);

    List<RefreshToken> findByUser_UserId(Long userId);
}
