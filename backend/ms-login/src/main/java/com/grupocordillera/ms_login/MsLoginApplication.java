package com.grupocordillera.ms_login;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class MsLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsLoginApplication.class, args);
    }

    @Bean
    public CommandLineRunner printMappings(
            @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping mapping) {
        return args -> {
            System.out.println("===== ENDPOINTS REGISTRADOS =====");
            mapping.getHandlerMethods().forEach((key, value) -> {
                System.out.println("MAPPING => " + key);
            });
        };
    }
}