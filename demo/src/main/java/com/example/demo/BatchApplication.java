package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BatchApplication {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private ConfigurableApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			JobParameters parameters = new JobParameters();
			if (args.length > 0) {
				String jobName = args[0];
				Job job = (Job) context.getBean(jobName);
				JobExecution execution = jobLauncher.run(job, parameters);
				System.out.println("Job Status" + execution.getStatus());
			} else {
				System.out.println("Please job name");
			}

		};
	}
}
