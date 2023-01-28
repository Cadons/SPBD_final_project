package ch.supsi.backend_api_rest.security.jwt;

public record AuthResponse(
        String token,
        String username,
        String role,
        String refreshToken,

        String tokenType ){


}
