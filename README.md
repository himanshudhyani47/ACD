# ACD
Setup demonstrates dynamic service registration, centralized configuration resolution, and real-time service health monitoring.

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

