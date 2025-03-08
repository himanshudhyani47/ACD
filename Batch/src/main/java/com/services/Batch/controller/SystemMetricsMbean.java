package com.services.Batch.controller;

import com.services.Batch.common.processor.JobInvokeProcessor;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@ManagedResource(objectName = "com.services:type=SystemMetricsMbean",
        description = "Mbean to fetch system metrics")

@Component
public class SystemMetricsMbean {
    @Autowired
    private JobInvokeProcessor jobInvokeProcessor;

    @ManagedOperation(description = "Method to invoke system metrics job")
    public String invokeSystemMetricsJob() {
        try {
            Map<String, JobParameter<?>> parameter = new HashMap<>();
            jobInvokeProcessor.invokeJob("systemMetricsJob", parameter);
            return "System metrics job invoked";
        } catch (Exception e) {
            return "Error invoking system metrics job";
        }
    }
}
