package com.apress.appendixa.webflux;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ReactiveSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/private").hasRole("USER")
                .matchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                .anyExchange().permitAll()
                .and().httpBasic()
                .and().build();
    }

    /**
     * Sample in-memory user details service.
     */
    @SuppressWarnings("deprecation")
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        return new MapReactiveUserDetailsService(
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("password")
                        .roles("USER,ADMIN")
                        .build());
    }
}
