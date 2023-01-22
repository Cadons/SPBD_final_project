package ch.supsi.backend_api_rest;

import ch.supsi.backend_api_rest.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)


public class BackendApiRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApiRestApplication.class, args);
    }

}
