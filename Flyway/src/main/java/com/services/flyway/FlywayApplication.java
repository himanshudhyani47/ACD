package com.services.flyway;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Main application class for managing Flyway migrations with multiple data sources.
 * Configures two data sources and their respective Flyway instances.
 */
@SpringBootApplication
public class FlywayApplication {

    /**
     * Creates a `DataSourceProperties` bean for the first data source (SQL Server).
     * @return DataSourceProperties for SQL Server.
     */
    @Bean(name = "dataSourceProperties1")
    @ConfigurationProperties(prefix = "spring.datasource.sqlserver")
    public DataSourceProperties dataSourceProperties1() {
        return new DataSourceProperties();
    }

    /**
     * Creates a `DataSource` bean for the first data source (SQL Server).
     *
     * @param properties The `DataSourceProperties` for SQL Server.
     * @return DataSource for SQL Server.
     */
    @Bean(name = "dataSource1")
    public DataSource dataSource1(@Qualifier("dataSourceProperties1") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "dataSourceProperties2")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSourceProperties dataSourceProperties2() {
        return new DataSourceProperties();
    }

    /**
     * Creates a `DataSourceProperties` bean for the second data source (MySQL).
     *
     * @return DataSourceProperties for MySQL.
     */
    @Bean(name = "dataSource2")
    public DataSource dataSource2(@Qualifier("dataSourceProperties2") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * Configures a Flyway instance for the first data source (SQL Server).
     *
     * @param dataSource1 The SQL Server data source.
     * @param sqlLocation The location of SQL migration scripts for SQL Server.
     * @param isClear Whether to enable cleaning of the database.
     * @return Configured Flyway instance for SQL Server.
     */
    @Bean(name = "flyway1")
    public Flyway flyway1(@Qualifier("dataSource1") DataSource dataSource1,
                          @Value("${spring.flyway.sql.locations.sqlserver}") String sqlLocation,
                          @Value("${spring.flyway.clear.sqlServer}") boolean isClear) {
        FluentConfiguration fluentConfiguration = Flyway.configure().dataSource(dataSource1);
        if (isClear) {
            return fluentConfiguration
                    .cleanDisabled(false)
                    .load();
        }
        return fluentConfiguration
                .locations(sqlLocation)
                .load();
    }

    /**
     * Configures a Flyway instance for the second data source (MySQL).
     *
     * @param dataSource2 The MySQL data source.
     * @param sqlLocation The location of SQL migration scripts for MySQL.
     * @param isClear Whether to enable cleaning of the database.
     * @return Configured Flyway instance for MySQL.
     */
    @Bean(name = "flyway2")
    public Flyway flyway2(@Qualifier("dataSource2") DataSource dataSource2,
                          @Value("${spring.flyway.sql.locations.mysql}") String sqlLocation,
                          @Value("${spring.flyway.clear.mySql}") boolean isClear) {

        FluentConfiguration fluentConfiguration = Flyway.configure().dataSource(dataSource2);

        if (isClear) {
            return fluentConfiguration
                    .cleanDisabled(false)
                    .load();
        }
        return fluentConfiguration
                .locations(sqlLocation)
                .load();
    }

    /**
     * Main method to run the Spring Boot application.
     * Initializes Flyway instances and performs database migrations or cleaning based on configuration.
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(FlywayApplication.class, args);

        Flyway flyway1 = ctx.getBean("flyway1", Flyway.class);
        Flyway flyway2 = ctx.getBean("flyway2", Flyway.class);

        boolean isCleanDisabled1 = flyway1.getConfiguration().isCleanDisabled();
        boolean isCleanDisabled2 = flyway2.getConfiguration().isCleanDisabled();

        // Perform cleaning or migration based on the cleanDisabled configuration
        if(!isCleanDisabled1 && !isCleanDisabled2) {
            flyway1.clean();
            flyway2.clean();
        } else if(!isCleanDisabled1) {
            flyway1.clean();
            flyway2.migrate();
        } else if(!isCleanDisabled2) {
            flyway2.clean();
            flyway1.migrate();
        } else {
            flyway1.migrate();
            flyway2.migrate();
        }
        SpringApplication.exit(ctx);
    }

}
