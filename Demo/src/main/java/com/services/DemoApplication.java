package com.services;

import com.services.base.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    private static final String APPLICATION_NAME = "demo";

    @Bean
    String getDescription(@Value("${description}") String description) {
        System.out.println(description);
        return new String("tset");
    }

    public static void main(String[] args) {
        Base.configureApp(APPLICATION_NAME);
        SpringApplication.run(DemoApplication.class, args);
    }

}
