package com.services.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FlywayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(FlywayApplication.class, args);
        Flyway flyway = ctx.getBean(Flyway.class);
        if(!ctx.getEnvironment().getProperty("spring.flyway.cleanDisabled", Boolean.class, false)) {
            flyway.clean();
        }
        SpringApplication.exit(ctx);
    }

}
