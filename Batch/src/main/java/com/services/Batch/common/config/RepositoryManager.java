package com.services.Batch.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
/**
 * Configuration class for setting up the repository manager.
 */

@Configuration
@EnableTransactionManagement
public class RepositoryManager {

    /**
     * Configuration class for setting up the Hikari connection pool.
     * Binding properties from configuration files (like batch.yml or application.properties) directly to the fields of the HikariConfig.
     * This makes it easier to manage and change configuration without modifying the code.
     * Don't use url in datasource config properties, use jdbcUrl instead. Because jdbcUrl is the property that HikariCP uses to connect to the database.
     */
    @Configuration
    @ConfigurationProperties(prefix = "datasource")
    public class CustomHikariConfig extends HikariConfig {
    }

    @Bean
    public DataSource dataSource(CustomHikariConfig customHikariConfig) {
        return new HikariDataSource(customHikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.services.dao.model")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}