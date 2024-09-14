package dev.eislyn.AutoTodo.services.impl;

import dev.eislyn.AutoTodo.repositories.PasswordResetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordResetTokenCleanupService {

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void removeExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        passwordResetRepository.deleteAllByExpiryDateBefore(now);
    }
}