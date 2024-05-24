package com.example.demo.job;

import com.example.demo.tasklet.UserTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class UserJobConfig {

    private final UserTasklet tasklet;

    @Autowired
    public UserJobConfig(UserTasklet tasklet) {
        this.tasklet = tasklet;
    }


    @Bean
    public Job userJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("UserJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step userStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        return new StepBuilder("UserStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }


}
