package com.api.Job_Portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // For demo/portfolio – allow everything (change to specific origins in real prod)
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");               // ← Allows Swagger UI from browser
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");               // GET, POST, PUT, DELETE, OPTIONS, etc.
        config.addExposedHeader("Authorization");   // Lets browser see JWT token in response
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}