/**
 * To register your application at the admin server, one can either include the
 * Spring Boot Admin Client or use Spring Cloud Discovery
 * For this project using spring cloud discovery. Adding @EnableDiscoveryClient
 * and using the existing dependency netflix-eureka-client.
 * Spring Boot Admin server will automatically discover the registered services
 * through the discovery mechanism without requiring the
 * individual spring-boot-admin-starter-client in each application.
 */
package com.acd.Admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
