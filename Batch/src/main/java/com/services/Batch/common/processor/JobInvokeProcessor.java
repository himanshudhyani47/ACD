package com.services.Batch.common.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.services.Batch.common.Util.JobUtil.getNewBatchId;
import static com.services.Batch.common.Util.JobUtil.getRunDate;

/**
 * Processor class for invoking batch jobs.
 */
@Component
public class JobInvokeProcessor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * Invokes a batch job with the given job name and parameters.
     *
     * @param jobName the name of the job to be invoked
     * @param jobParameters the parameters for the job
     * @return the status of the batch job
     */

    public BatchStatus invokeJob(String jobName, Map<String, JobParameter<?>> jobParameters) {
        try {
            String batchId = getNewBatchId();
            return invokeJob(batchId, jobName, jobParameters);
        } catch (Exception e) {
            logger.error("Error invoking job: " + jobName, e);
            return null;
        }
    }

    /**
     * Helper method to invoke a batch job with the given batch ID, job name, and parameters.
     *
     * @param batchId the ID of the batch
     * @param jobName the name of the job to be invoked
     * @param jobParameters the parameters for the job
     * @return the status of the batch job
     * @throws JobInstanceAlreadyCompleteException if the job instance is already complete
     * @throws JobExecutionAlreadyRunningException if the job execution is already running
     * @throws JobParametersInvalidException if the job parameters are invalid
     * @throws JobRestartException if the job restart fails
     */

    private BatchStatus invokeJob(String batchId, String jobName, Map<String, JobParameter<?>> jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters parameters = new JobParametersBuilder()
                .addString("batchId", batchId)
                .addDate("runDate", getRunDate())
                .addJobParameters(new JobParameters(jobParameters))
                .toJobParameters();
        Job job = (Job) applicationContext.getBean(jobName);
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        return jobExecution.getStatus();
    }
}
