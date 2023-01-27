package ch.supsi.backend_api_rest.controller;

import ch.supsi.backend_api_rest.exceptions.UnauthorizedOperation;
import ch.supsi.backend_api_rest.security.jwt.AuthResponse;
import ch.supsi.backend_api_rest.security.jwt.LoginResponse;
import ch.supsi.backend_api_rest.security.jwt.RequestRefresh;
import ch.supsi.backend_api_rest.security.jwt.TokenService;
import ch.supsi.backend_api_rest.security.login.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
            String cookie = refreshToken.getName()+"=" + refreshToken.getValue() + "; "+(refreshToken.isHttpOnly()?"HttpOnly;":"")+ "; "+(refreshToken.getSecure()?"Secure;":"")+ "; Path="+refreshToken.getPath()+"; Max-Age=" + refreshToken.getMaxAge()+ refreshToken.getMaxAge()+"; SameSite="+refreshToken.getAttribute("SameSite");;
            responseHeaders.add("Set-Cookie",cookie);

            return new ResponseEntity<>(new LoginResponse(token.token(), token.username()), responseHeaders, HttpStatus.OK);
        } else {
            //get request cookies

            var cookies = request.getCookies();
            if(cookies==null)
            {
                return ResponseEntity.badRequest().build();
            }
            var header = new HttpHeaders();
            //add all cookies to header
            for (var cookie : cookies) {
                if(cookie.getValue()!=null)
                header.add("Cookie", cookie.getName() + "=" + cookie.getValue());
            }
            return refreshToken(header);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request) {
        try {
            //get refresh token from cookie
            var BearerToken = request.getHeader("Cookie");

            if (BearerToken == null) {
                return ResponseEntity.badRequest().build();
            }
            BearerToken = BearerToken.replace("refreshToken=", "");
            BearerToken = BearerToken.replace("; HttpOnly; SameSite=Strict;Path=/", "");

            if ( tokenService.revokeToken(BearerToken)) {
                LOG.trace("User: " + tokenService.getUsernameFromToken(BearerToken) + " logged out");
                //delete token and refreshtoken in cookie
                HttpHeaders responseHeaders = new HttpHeaders();
                var refreshToken = setRefreshTokenCookie("");
                String cookie = refreshToken.getName()+"=" + refreshToken.getValue() + "; "+(refreshToken.isHttpOnly()?"HttpOnly;":"")+ "; "+(refreshToken.getSecure()?"Secure;":"")+ ";Path="+refreshToken.getPath()+"; Max-Age=" + Instant.now()+ refreshToken.getMaxAge()+"; SameSite="+refreshToken.getAttribute("SameSite");;
                responseHeaders.add("Set-Cookie",cookie);

                return new ResponseEntity<>(responseHeaders, HttpStatus.OK);

            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity<LoginResponse> refreshToken(HttpHeaders headers) {

        //get refresh token from cookie
        var BearerToken = headers.get("Cookie").get(0);
        BearerToken = BearerToken.replace("refreshToken=", "");
        BearerToken = BearerToken.replace("; HttpOnly; SameSite=Strict;Path=/", "");
        if ( !tokenService.validateToken(BearerToken))
            return ResponseEntity.badRequest().build();


        var username = tokenService.getUsernameFromToken(BearerToken);

        LOG.trace("User: " + username + " requested refresh");
        var token = getAuthResponseResponseEntity(BearerToken);

        return new ResponseEntity<>(new LoginResponse(token.getBody().token(), token.getBody().username()), HttpStatus.OK);
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
    private Cookie setRefreshTokenCookie(String refreshToken)
    {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(tokenService.getRefreshExpiration()*60);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }

}
