package ch.supsi.backend_api_rest.controller;

import ch.supsi.backend_api_rest.exceptions.UnauthorizedOperation;
import ch.supsi.backend_api_rest.security.jwt.AuthResponse;
import ch.supsi.backend_api_rest.security.jwt.LoginResponse;
import ch.supsi.backend_api_rest.security.jwt.TokenService;
import ch.supsi.backend_api_rest.security.login.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")

public class AuthenticationController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
@Value("${version}")
    private String version;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> token(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        if (loginRequest.username() == null || loginRequest.password() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (loginRequest.username().equals("") || loginRequest.password().equals("")) {
            return ResponseEntity.badRequest().build();
        }
        if (!tokenService.isLogged(loginRequest.username())) {

            LOG.trace("Token requested for user: '{}'", loginRequest.username());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

            var token = tokenService.generateToken(authentication);
            LOG.trace("Token granted");
            LOG.trace("User: " + loginRequest.username() + " logged in");
            //store token and refreshtoken in cookie
            HttpHeaders responseHeaders = new HttpHeaders();

            //expires in 10 minutes
            var refreshToken = setRefreshTokenCookie(token.refreshToken());
            var username=setTokenCookie("username", token.username(), tokenService.getRefreshExpiration() * 60);
            var tokenCookie = setTokenCookie("Authorization", token.token(), tokenService.getTokenExpiration() * 60);
            String cookie = formatCookie(refreshToken);
            responseHeaders.add("Set-Cookie", cookie);
            responseHeaders.add("Set-Cookie", formatCookie(tokenCookie));


            return new ResponseEntity<>(new LoginResponse(token.token(), token.username(),token.role()), responseHeaders, HttpStatus.OK);
        } else {
            //get request cookies

            var cookies = request.getCookies();
            if (cookies == null) {
                return ResponseEntity.badRequest().build();
            }
            var header = new HttpHeaders();
            //add all cookies to header
            for (var cookie : cookies) {
                if (cookie.getValue() != null)
                    header.add("Cookie", cookie.getName() + "=" + cookie.getValue());
            }

            return refreshToken(request);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request) {
        try {
            //get refresh token from cookie
            var BearerToken = Arrays.stream(request.getHeader("Cookie").split(";")).filter(cookie -> cookie.contains("refreshToken")).findFirst().get();
            var username = Arrays.stream(request.getHeader("Cookie").split(";")).filter(cookie -> cookie.contains("username")).findFirst().get();
            if (BearerToken != null) {

            BearerToken = BearerToken.replace("refreshToken=", "");
            BearerToken = BearerToken.replace("; HttpOnly; SameSite=Strict;Path=/", "");
            }
            LOG.trace("User: " + tokenService.getUsernameFromToken(BearerToken) + " logged out");
            if (tokenService.revokeToken(BearerToken, username)) {

                //delete token and refreshtoken in cookie
                HttpHeaders responseHeaders = new HttpHeaders();
                var refreshToken = setRefreshTokenCookie("");
                var tokenCookie = setTokenCookie("Authorization", "", 0);
                String cookie = formatCookie(refreshToken);
                responseHeaders.add("Set-Cookie", cookie);
                responseHeaders.add("Set-Cookie", formatCookie(tokenCookie));

                return new ResponseEntity<>(responseHeaders, HttpStatus.OK);

            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/refresh")
    public boolean isToRefresh(HttpServletRequest request)
    {
       return tokenService.validateToken(request.getHeader("Authorization"));
    }
@PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest headers) {

        //get refresh token from cookie
        var BearerToken = Arrays.stream(headers.getCookies()).filter(cookie -> cookie.getName().equals("refreshToken")).findFirst().get().getValue();
        BearerToken = BearerToken.replace("refreshToken=", "");
        BearerToken = BearerToken.replace("; HttpOnly; SameSite=Strict;Path=/", "");
        if (!tokenService.validateToken(BearerToken))
            return ResponseEntity.badRequest().build();


        var username = tokenService.getUsernameFromToken(BearerToken);

        LOG.trace("User: " + username + " requested refresh");
        var token = getAuthResponseResponseEntity(BearerToken);

        return new ResponseEntity<>(new LoginResponse(Objects.requireNonNull(token.getBody()).token(), token.getBody().username(),token.getBody().role()), HttpStatus.OK);
    }

    @GetMapping("/version")
    public String version() {
        return version;
    }

    @NotNull
    private ResponseEntity<AuthResponse> getAuthResponseResponseEntity(String BearerToken) {
        BearerToken = BearerToken.replace("Bearer ", "");

        try {
            var newToken = tokenService.refreshToken(BearerToken);
            return ResponseEntity.ok(newToken);
        } catch (InvalidBearerTokenException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnauthorizedOperation e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

    private Cookie setRefreshTokenCookie(String refreshToken) {
        return setTokenCookie("refreshToken", refreshToken, 60 * 10);
    }

    public static Cookie setTokenCookie(String name, String token, int expiration) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(expiration);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }

    public static String formatCookie(Cookie cookie) {
        return cookie.getName() + "=" + cookie.getValue() + "; " + (cookie.isHttpOnly() ? "HttpOnly;" : "") + "; " + (cookie.getSecure() ? "Secure;" : "") + "; Path=" + cookie.getPath() + "; Max-Age=" + cookie.getMaxAge() + cookie.getMaxAge() + "; SameSite=" + cookie.getAttribute("SameSite");
    }

}
