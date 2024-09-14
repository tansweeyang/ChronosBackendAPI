package dev.eislyn.AutoTodo.repositories;

import dev.eislyn.AutoTodo.domain.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {
    String findByToken(String token);
    void deleteAllByExpiryDateBefore(LocalDateTime now);
}
