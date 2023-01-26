package ch.supsi.backend_api_rest.controller;

import ch.supsi.backend_api_rest.exceptions.UnauthorizedOperation;
import ch.supsi.backend_api_rest.security.jwt.AuthResponse;
import ch.supsi.backend_api_rest.security.jwt.RequestRefresh;
import ch.supsi.backend_api_rest.security.jwt.TokenService;
import ch.supsi.backend_api_rest.security.login.LoginRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")

public class AuthenticationController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> token(@RequestBody LoginRequest loginRequest) {

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
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(@RequestHeader("Authorization") String BearerToken) {
        try {
            BearerToken = BearerToken.replace("Bearer ", "");
            if (tokenService.revokeUser(tokenService.getUsernameFromToken(BearerToken))) {
                LOG.trace("User: " + tokenService.getUsernameFromToken(BearerToken) + " logged out");
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RequestRefresh BearerToken) {

        var username = tokenService.getUsernameFromToken(BearerToken.refresh().replace("Bearer ", ""));

        LOG.trace("User: " + username + " requested refresh");
        return getAuthResponseResponseEntity(BearerToken.refresh());
    }

    @NotNull
    private ResponseEntity<AuthResponse> getAuthResponseResponseEntity(String BearerToken) {
        BearerToken = BearerToken.replace("Bearer ", "");

        try {
            var newToken = tokenService.refreshToken(BearerToken);
            return ResponseEntity.ok(newToken);
        } catch (InvalidBearerTokenException e) {
            return ResponseEntity.badRequest().build();
        }
        catch (UnauthorizedOperation e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

}
