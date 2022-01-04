package com.apress.appendixa.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class ReactiveSecureApplicationTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void testPublicResource() {
        client.get().uri("/public")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(containsString("public page"));
    }

    @Test
    public void testPrivateResource() {
        client.get().uri("/private")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser
    public void testPrivateResourceAsUser() {
        client.get().uri("/private")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(containsString("private page"));
    }

    @Test
    public void testActuator() {
        client.get().uri("/actuator/health")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser
    public void testActuatorAsUser() {
        client.get().uri("/actuator/health")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testActuatorAsAdmin() {
        client.get().uri("/actuator/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.status").isEqualTo("UP");
    }
}
