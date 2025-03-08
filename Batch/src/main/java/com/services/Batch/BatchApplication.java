package com.services.Batch;

import com.services.base.Base;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {

	private static final String APPLICATION_NAME = "batch";
	public static void main(String[] args) {
		Base.configureApp(APPLICATION_NAME);
		SpringApplication.run(BatchApplication.class, args);
	}

}
