package com.atman.server.Batch.VendorInvoiceBatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class VendorInvoiceScheduler {

    private final JobOperator jobOperator;
    private final Job generateVendorInvoiceJob;

    @Scheduled(cron = "0 0 0 * * *")
    public void runInvoiceJob() {
        try {
            log.info("Starting invoice generation job at {}", LocalDateTime.now());
            JobParameters jobParameters = new JobParametersBuilder().addString("timestamp", String.valueOf(System.currentTimeMillis()))
                                                                    .toJobParameters();
            jobOperator.start(generateVendorInvoiceJob, jobParameters);
            log.info("Invoice generation job started successfully.");
        } catch(Exception e) {
            log.error("Error occurred while running invoice job", e);
        }
    }

}
