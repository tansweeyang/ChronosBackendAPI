package dev.eislyn.chronos_calendar.service;

import dev.eislyn.chronos_calendar.dto.response.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface IUserReadDomainService {
    UserResponseDto me(HttpServletRequest httpServletRequest);
}