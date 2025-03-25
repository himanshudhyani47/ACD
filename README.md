# Description
Spring Boot application focused on data migration from SQL Server to MySQL using Spring Batch, with
externalized configuration, integrated with Spring Boot Admin and Eureka Server for monitoring and service discovery,
utilized Flyway for database migrations, and used Mockaroo for generating dummy data.

## Intellij VM arguments:
    1. Discovery
        -Dspring.profiles.active=common,dev2
    2. Admin
        -Dspring.profiles.active=common,dev2
    3. Config
        -Dspring.profiles.active=common,dev2,native -DRESOURCES_DIR=D:\Workspace\ACD\Common-Resources\src\main\resources
    4. Demo
        -Dspring.profiles.active=common,dev2

## Build order:
    1. Common-Parent 
    2. Discovery,Admin,Config
    3. Base
    4. Demo

## Run order:
    1. Discovery 
    2. Admin
    3. Config
    4. Demo

