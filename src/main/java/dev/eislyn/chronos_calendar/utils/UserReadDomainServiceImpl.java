package dev.eislyn.chronos_calendar.utils;

import dev.eislyn.chronos_calendar.dto.response.UserResponseDto;
import dev.eislyn.chronos_calendar.model.GenericResponse;
import dev.eislyn.chronos_calendar.service.IUserReadDomainService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserReadDomainServiceImpl implements IUserReadDomainService {
    private final RestTemplate restTemplate;

    public UserResponseDto me(HttpServletRequest httpServletRequest) {
        String USER_INFO_URL = "http://chronos-auth/api/auth/me";

        // Get token from httpServletRequest
        String token = extractBearerToken(httpServletRequest);

        // Set up headers with the Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Make the GET request to fetch user details
        ResponseEntity<GenericResponse<UserResponseDto>> response = restTemplate.exchange(
                USER_INFO_URL,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        // If the response is successful, extract the user data
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            GenericResponse<UserResponseDto> body = response.getBody();
            if ("success".equalsIgnoreCase(body.getStatus()) && body.getData() != null) {
                return body.getData(); // Return the actual user data
            }
        }

        // Return null or throw an exception if the user data is not available
        throw new IllegalStateException("Failed to fetch user information or invalid token");
    }

    private static String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove "Bearer " prefix
        }
        throw new IllegalStateException("Authorization header is missing or invalid");
    }
}
