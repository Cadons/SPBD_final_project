package ch.supsi.backend_api_rest.security.jwt;

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
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Autowired
    public TokenService(TokenRefreshRepository tokenRefreshRepository, JwtEncoder encoder, JwtDecoder decoder) {
        this.tokenRefreshRepository = tokenRefreshRepository;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public AuthResponse generateToken(Authentication authentication) {

        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        var token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        now = Instant.now();
        JwtClaimsSet refreshClaims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        String refreshToken = this.encoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();

        //store on redis
        tokenRefreshRepository.save(refreshToken, "jwt-refresh-token:" + authentication.getName(), 30, TimeUnit.DAYS);
        tokenRefreshRepository.loginUser(authentication.getName());
        return new AuthResponse(token, authentication.getName(), refreshToken, "Bearer");
    }
    public boolean revokeUser(String username)
    {
        if(tokenRefreshRepository.find(username).equals(""))
        {
            tokenRefreshRepository.logoutUser(username);
            return true;
        }else{
            return false;
        }

    }
    public boolean isLogged(String username)
    {
        return tokenRefreshRepository.checkIfLogged(username);

    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new InvalidBearerTokenException("Invalid refresh token");
        }

        String username = getUsernameFromToken(refreshToken);
        String storedRefreshToken = tokenRefreshRepository.find("jwt-refresh-token:" + username);
        if (!storedRefreshToken.equals(refreshToken)) {
            throw new InvalidBearerTokenException("Invalid refresh token");
        }
        // generate new access token
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope", "")
                .build();
        var token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new AuthResponse(token, username, refreshToken, "Bearer");
    }

    public String getUsernameFromToken(String token) {

        return decoder.decode(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            System.out.println(decoder.decode(token).getExpiresAt());
            if (Objects.requireNonNull(decoder.decode(token).getExpiresAt()).isBefore(Instant.now())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}