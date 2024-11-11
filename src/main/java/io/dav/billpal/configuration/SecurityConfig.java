package io.dav.billpal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration class for the application
 * @author Dave AKN
 * @version 1.0
 */
public class SecurityConfig {
    // Array of URL patterns that should be publicly accessible without authentication
    private static final String[] PUBLIC_URLS = {};

    /**
     * Configures the security filter chain for the application
     * @param http HttpSecurity object to be configured
     * @return Configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (Cross-Site Request Forgery) protection
                // This is often done for stateless APIs using token-based authentication
                // Caution: Ensure you have alternative protection mechanisms in place
                .csrf(AbstractHttpConfigurer::disable)

                // Disable CORS (Cross-Origin Resource Sharing)
                // Be cautious: this allows requests from any origin. In production, you might want to configure CORS more strictly
                .cors(AbstractHttpConfigurer::disable)

                // Configure session management
                .sessionManagement(session -> session
                        // Set the session creation policy to STATELESS
                        // This means the server will not create or use any session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configure authorization rules for HTTP requests
                .authorizeHttpRequests(auth -> auth
                        // Allow unrestricted access to URLs defined in PUBLIC_URLS
                        .requestMatchers(PUBLIC_URLS).permitAll()

                        // Require "DELETE:USER" authority for DELETE requests to "/user/delete/**"
                        .requestMatchers(HttpMethod.DELETE, "/user/delete/**").hasAnyAuthority("DELETE:USER")

                        // Require "DELETE:CUSTOMER" authority for DELETE requests to "/customer/delete/**"
                        .requestMatchers(HttpMethod.DELETE, "/customer/delete/**").hasAnyAuthority("DELETE:CUSTOMER")

                        // Any request not matched by the above rules requires authentication
                        .anyRequest().authenticated()
                )

                // Configure exception handling
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        // Set a custom AccessDeniedHandler (currently null, should be implemented)
                        // This handles cases where an authenticated user doesn't have required permissions
                        .accessDeniedHandler(null)

                        // Set a custom AuthenticationEntryPoint (currently null, should be implemented)
                        // This handles cases where an unauthenticated user tries to access a protected resource
                        .authenticationEntryPoint(null)
                );

        // Build and return the configured SecurityFilterChain
        return http.build();
    }
}

/**
 * Additional notes for overall understanding:
 * 1. This class, SecurityConfig, is responsible for configuring Spring Security for your application.
 * 2. The securityFilterChain method is a bean that defines the security rules and behaviors for your application.
 * 3. The configuration is using Spring Security's new lambda-based style, which is more readable and flexible than the older method chaining style.
 * 4. The security configuration is stateless, which is common for RESTful APIs, especially those using token-based authentication like JWT.
 * 5. The PUBLIC_URLS array is empty in this example. You should populate it with any URL patterns that should be accessible without authentication (e.g., "/api/public/**", "/login", etc.).
 * 6. The configuration currently has null values for accessDeniedHandler and authenticationEntryPoint. In a production environment, you should implement and provide custom handlers for
 * these to properly manage access denied scenarios and unauthenticated access attempts.
 * 7. The authorization rules are quite specific, requiring particular authorities for certain DELETE operations. Make sure these align with your application's permission model.
 * 8. Remember to regularly review and update your security configuration as your application's needs change and to keep up with best practices in application security.
 */