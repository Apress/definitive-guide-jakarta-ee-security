package com.apress.appendixa.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

/**
 * REST endpoint for various utility methods.
 */
@RestController
public class HelloController {

    @GetMapping("/public")
    public Mono<String> publicHello(Principal principal) {
        return Mono.just("Hello " + getPrincipalName(principal) + ", from a public page");
    }

    @GetMapping("/private")
    public Mono<String> privateHello(Principal principal) {
        return Mono.just("Hello " + getPrincipalName(principal) + ", from a private page");
    }

    private String getPrincipalName(Principal principal) {
        return principal == null ? "anonymous" : principal.getName();
    }
}
