package com.example.mygatewayuserservice.it;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest {

    static KeycloakContainer container = new KeycloakContainer();

    static {
        container.withRealmImportFile("/keycloak/import/realm-export.json");
    }

    @BeforeAll
    public static void beforeAll() {
        container.start();

        String issuerUrl = String.format("http://%s:%d/realms/users-dev", container.getHost(), container.getFirstMappedPort());
        String keycloakUrl = String.format("http://%s:%d", container.getHost(), container.getFirstMappedPort());

        System.setProperty("spring.security.oauth2.resourceserver.jwt.issuer-uri", issuerUrl);
        System.setProperty("api.keycloak.auth-server-url", keycloakUrl);

    }

    @AfterAll
    public static void afterAll() {
        container.close();
    }
}
