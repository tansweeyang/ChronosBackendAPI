package dev.eislyn.chronos.utils;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

public class AuthUtil {
    public static String generateJwtForUser(JwtEncoder encoder, String username) {
        // Use the JwtEncoder or manually create a token for testing purposes
        // For example, use your JwtEncoder bean to encode a token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)
                .claim("role", "USER")
                .build();
        Jwt jwt = encoder.encode(JwtEncoderParameters.from(claims));
        return jwt.getTokenValue();

    }
}
