package ch.supsi.backend_api_rest;

import ch.supsi.backend_api_rest.security.jwt.TokenRefreshRepository;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
    @Bean
    public ServletWebServerFactory servletContainer() {
        // Enable SSL Trafic
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        // Add HTTP to HTTPS redirect
        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

        return tomcat;
    }


    private Connector httpToHttpsRedirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(7000);
        connector.setSecure(false);
        connector.setRedirectPort(7443);
        return connector;
    }

}
