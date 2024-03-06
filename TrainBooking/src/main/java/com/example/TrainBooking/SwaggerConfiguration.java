package com.example.TrainBooking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {
 
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Demo Example").version("App Version")
                .description("App Description").termsOfService("http://swagger.io/terms/"));
    }
}