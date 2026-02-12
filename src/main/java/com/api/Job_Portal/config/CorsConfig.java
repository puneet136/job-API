package com.api.Job_Portal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allow CORS on all endpoints
                .allowedOrigins("*")  // For demo/portfolio – allows from any origin (including Swagger UI in browser)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")  // All methods you use
                .allowedHeaders("*")  // Allow all headers (including Authorization, Content-Type)
                .allowCredentials(false)  // Set true only if you use cookies/auth with credentials
                .maxAge(3600);  // Cache preflight for 1 hour
    }
}