package com.example.ecom.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain customSecurityFilter(HttpSecurity http) throws Exception {

       return http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(httpReq->
                        httpReq.requestMatchers("/api/auth/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
                ).authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
