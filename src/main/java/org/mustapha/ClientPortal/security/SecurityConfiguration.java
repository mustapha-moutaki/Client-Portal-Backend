package org.mustapha.ClientPortal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to Auth (Login/Register) and Swagger UI
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // ---------------- CLIENT PERMISSIONS ----------------

                        // 1. Create Claims: Allow 'CLIENT' to send data (POST) to create a claim
                        .requestMatchers(HttpMethod.POST, "/api/claims").hasRole("CLIENT")

                        // 2. See Claims: Allow 'CLIENT' (and staff) to view claim details (GET)
                        .requestMatchers(HttpMethod.GET, "/api/claims/**").hasAnyRole("CLIENT", "ADMIN", "SUPERVISOR", "OPERATOR")

                        // 3. See Products: Allow 'CLIENT' to view the list of all products (GET)
                        // منتجات
                        .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("CLIENT", "ADMIN", "OPERATOR")


                        // 4. See Data: Allow 'CLIENT' to view their own profile information (GET)
                        .requestMatchers(HttpMethod.GET, "/api/users/profile").hasRole("CLIENT")

                        // ----------------------------------------------------

                        // 5. Convert Lead to Client: Allow ADMIN and SUPERVISOR
                        .requestMatchers(HttpMethod.POST, "/api/clients/convert/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR")



                        // Leads CRUD
                        .requestMatchers(HttpMethod.POST, "/api/leads").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/leads/**").hasAnyRole("CLIENT", "ADMIN", "SUPERVISOR", "OPERATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/leads/**").hasAnyRole("ADMIN", "SUPERVISOR", "OPERATOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/leads/**").hasRole("ADMIN") //

                        // Default rule: All other requests need to be logged in (Authenticated)
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}