package com.peerrank.peerrank.config;

import com.peerrank.peerrank.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(Customizer.withDefaults())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // Authentication
                        .requestMatchers("/auth/**").permitAll()

                        // Images
                        .requestMatchers("/images/**").permitAll()

                        // Dashboard
                        .requestMatchers(HttpMethod.GET, "/dashboard").permitAll()
                        .requestMatchers(HttpMethod.GET, "/dashboard/**").permitAll()

                        // Items
                        .requestMatchers(HttpMethod.GET, "/items").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items/**").permitAll()

                        // Categories
                        .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()

                        // Reviews
                        .requestMatchers(HttpMethod.GET, "/reviews/item/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/top-rated").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/most-reviewed").permitAll()

                        // Protected Item APIs
                        .requestMatchers(HttpMethod.POST, "/items/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/items/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/items/**").authenticated()

                        // Protected Category APIs
                        .requestMatchers(HttpMethod.POST, "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").authenticated()

                        // Everything else
                        .anyRequest().authenticated()
                )

                .httpBasic(httpBasic -> httpBasic.disable())

                .formLogin(form -> form.disable())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}