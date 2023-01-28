package ch.supsi.backend_api_rest.security.jwt;

public record LoginResponse( String token,
                             String username, String role) {
}
