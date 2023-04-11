package dev.prabal.batchsummarycore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
//@EnableScheduling
public class BatchSummaryCoreApplication {

    public static void main(String[] args) {
/*
        ApplicationContext ctx = SpringApplication.run(BatchSummaryCoreApplication.class, args);
        JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");

        Job job1= (Job) ctx.getBean("firstJob");
        Job job2= (Job) ctx.getBean("secondJob");
        jobLauncher.run(job1,new JobParameters());
        jobLauncher.run(job2,new JobParameters());

 */
        SpringApplication.run(BatchSummaryCoreApplication.class, args);
    }

}
