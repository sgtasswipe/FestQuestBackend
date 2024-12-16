package com.example.festquestbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Allow all routes
                        .allowedOrigins("http://127.0.0.1:5500","http://127.0.0.1:5501", "http://localhost:5501", "http://localhost:5500", "http://localhost:63342")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow specific HTTP methods
                        .allowedHeaders("Authorization", "Content-Type", "Accept")  // Allow specific headers
                        .exposedHeaders("Authorization")  // Expose headers. Makes it possible to retrieve the Authorization header in the frontend
                        .allowCredentials(true);  // Allow credentials if needed
            }
        };
    }
}
