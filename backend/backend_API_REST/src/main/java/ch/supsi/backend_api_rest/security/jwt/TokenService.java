package ch.supsi.backend_api_rest.security.jwt;

import ch.supsi.backend_api_rest.exceptions.UnauthorizedOperation;
import ch.supsi.backend_api_rest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {


    private final TokenRefreshRepository tokenRefreshRepository;
    private final int TOKEN_EXPIRATION = 2;
    private final int REFRESH_EXPIRATION = 10;
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TokenService(TokenRefreshRepository tokenRefreshRepository, JwtEncoder encoder, JwtDecoder decoder,
                        EmployeeRepository employeeRepository) {
        this.tokenRefreshRepository = tokenRefreshRepository;
        this.encoder = encoder;
        this.decoder = decoder;
        this.employeeRepository = employeeRepository;
    }

    public AuthResponse generateToken(Authentication authentication) {

        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(TOKEN_EXPIRATION, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        var token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        now = Instant.now();
        JwtClaimsSet refreshClaims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(REFRESH_EXPIRATION, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        String refreshToken = this.encoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();

        //store on redis
        tokenRefreshRepository.save(refreshToken, "jwt-refresh-token:" + authentication.getName(), REFRESH_EXPIRATION, TimeUnit.MINUTES);
        tokenRefreshRepository.loginUser(authentication.getName());
        String role = employeeRepository.findByUsername(authentication.getName()).get().isMenager() ? "ROLE_MENAGER" : "ROLE_EMPLOYEE";
        return new AuthResponse(token, authentication.getName(), role, refreshToken, "Bearer");
    }

    public boolean revokeUser(String username) {
        if (tokenRefreshRepository.checkIfLogged(username)) {
            tokenRefreshRepository.logoutUser(username);
            return true;
        } else {
            return false;
        }

    }

    public boolean revokeToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new InvalidBearerTokenException("Invalid refresh token");
        }

        String username = getUsernameFromToken(refreshToken);
        String storedRefreshToken = tokenRefreshRepository.find("jwt-refresh-token:" + username);
        if (!storedRefreshToken.equals(refreshToken)) {
            throw new InvalidBearerTokenException("Invalid refresh token");
        }
        tokenRefreshRepository.revokeToken(username);
        tokenRefreshRepository.logoutUser(username);
        return true;
    }

    public boolean isLogged(String username) {
        return tokenRefreshRepository.checkIfLogged(username);

    }

    public boolean isMenager(String token) {

        String username = getUsernameFromToken(token);
        if (username == null)
            return false;
        return employeeRepository.findByUsername(username).get().isMenager();
    }

    public AuthResponse refreshToken(String refreshToken) throws UnauthorizedOperation {
        if (!validateToken(refreshToken)) {
            throw new InvalidBearerTokenException("Invalid refresh token");
        }

        String username = getUsernameFromToken(refreshToken);
        if (!isMenager(refreshToken)) {
            throw new UnauthorizedOperation("You are not a menager and you can't refresh your token");
        }
        String storedRefreshToken = tokenRefreshRepository.find("jwt-refresh-token:" + username);
        if (!storedRefreshToken.equals(refreshToken)) {
            throw new InvalidBearerTokenException("Invalid refresh token");
        }
        // generate new access token
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(TOKEN_EXPIRATION, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope", "")
                .build();
        var token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        String role = employeeRepository.findByUsername(username).get().isMenager() ? "ROLE_MENAGER" : "ROLE_EMPLOYEE";
        return new AuthResponse(token, username, role, refreshToken, "Bearer");
    }

    public String getUsernameFromToken(String token) {

        return decoder.decode(token).getSubject();
    }

    public void updateSession(String username) {
        tokenRefreshRepository.updateSession(username);
    }

    public boolean validateToken(String token) {
        try {

            if (Objects.requireNonNull(decoder.decode(token).getExpiresAt()).isBefore(Instant.now())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getRefreshExpiration() {
        return REFRESH_EXPIRATION;
    }

    public int getTokenExpiration() {
        return TOKEN_EXPIRATION;
    }


}