package com.senac.projectmanagement.repository;

import com.senac.projectmanagement.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByUser_UserIdAndPasswordResetTokenUsedFalseAndPasswordResetTokenExpiresAtAfter(Long userId, LocalDateTime now);

    Optional<PasswordResetToken> findByPasswordResetTokenTokenAndPasswordResetTokenUsedFalseAndPasswordResetTokenExpiresAtAfter(String token, LocalDateTime now);

    List<PasswordResetToken> findByUser_UserId(Long userId);
}
