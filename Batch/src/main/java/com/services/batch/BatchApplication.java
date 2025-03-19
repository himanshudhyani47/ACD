package com.services.batch;

import com.services.base.Base;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EnableFeignClients(basePackages = "com.services.batch.client")
@EntityScan(basePackages = {"com.services.dao.entity"})
@EnableJpaRepositories(basePackages = {"com.services.dao.repository"})
@Import({com.services.batch.common.config.RepositoryManager.class}) // Import the RepositoryManager configuration class into this configuration so that bean will be created and available for autowiring.
@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {

	private static final String APPLICATION_NAME = "batch";
	public static void main(String[] args) {
		Base.configureApp(APPLICATION_NAME);
		SpringApplication.run(BatchApplication.class, args);
	}

}
