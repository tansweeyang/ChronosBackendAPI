package dev.eislyn.chronos.repository;

import dev.eislyn.chronos.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    void deleteAllByExpiryDateBefore(LocalDateTime now);
}
