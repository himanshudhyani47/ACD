# ACD
Setup demonstrates dynamic service registration, centralized configuration resolution, and real-time service health monitoring.

## Intellij VM arguments:
    1. Discovery
        -Dspring.profiles.active=dev1,common
    2. Admin
        -Dspring.profiles.active=dev1,common
    3. Config
        -Dspring.profiles.active=dev1,native,common -DRESOURCES_DIR=D:\Workspace\ACD\Common-Resources\src\main\resources
    4. Demo
        -Dspring.profiles.active=dev1,common

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

