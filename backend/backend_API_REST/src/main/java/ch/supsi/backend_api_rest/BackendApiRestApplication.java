package ch.supsi.backend_api_rest;

import ch.supsi.backend_api_rest.security.jwt.TokenRefreshRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class BackendApiRestApplication {


    private final TokenRefreshRepository tokenRefreshRepository;

    public BackendApiRestApplication(TokenRefreshRepository tokenRefreshRepository) {
        this.tokenRefreshRepository = tokenRefreshRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApiRestApplication.class, args);
    }

@Bean
    public CommandLineRunner initialize() {
        return args -> tokenRefreshRepository.resetSessions();
    }

}
