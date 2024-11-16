package com.ooad.newsaggregator.services;

import com.ooad.newsaggregator.authfilter.FirebaseAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final FirebaseAuthService firebaseAuthService;

    public SecurityConfig(FirebaseAuthService firebaseAuthService) {
        this.firebaseAuthService = firebaseAuthService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        FirebaseAuthenticationFilter firebaseAuthenticationFilter = new FirebaseAuthenticationFilter(firebaseAuthService);

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                )
                .addFilterBefore(firebaseAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
