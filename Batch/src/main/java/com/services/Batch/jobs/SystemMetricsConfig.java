package com.services.Batch.jobs;

import com.services.Batch.tasklet.ReadSystemMetricsTasklet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SystemMetricsConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Bean(name = "systemMetricsJob")
    public Job systemMetricsJob() {
        logger.info("Creating system metrics job");
        return new JobBuilder("systemMetricsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(readSystemMetricsStep())
//                .next(storeSystemMetricsStep())
//                .next(sendSystemMetricsStep())
                .build();
    }

    @Bean(name = "readSystemMetricsStep")
    public Step readSystemMetricsStep() {
        logger.info("Creating read system metrics step");
        return new StepBuilder("readSystemMetricsStep",jobRepository)
                .tasklet(new ReadSystemMetricsTasklet(), platformTransactionManager)
                .build();
    }
    @Bean
    @StepScope
    public ReadSystemMetricsTasklet readSystemMetricsTasklet() {
        return new ReadSystemMetricsTasklet();
    }
}
