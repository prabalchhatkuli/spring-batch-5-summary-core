package dev.prabal.batchsummarycore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
//@EnableScheduling
public class BatchSummaryCoreApplication {

    public static void main(String[] args) {
/*

        //one way to start multiple jobs
        ApplicationContext ctx = SpringApplication.run(BatchSummaryCoreApplication.class, args);
        JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");

        Job job1= (Job) ctx.getBean("firstJob");
        Job job2= (Job) ctx.getBean("secondJob");
        jobLauncher.run(job1,new JobParameters());
        jobLauncher.run(job2,new JobParameters());

 */
        System.exit(SpringApplication.exit(new SpringApplicationBuilder(BatchSummaryCoreApplication.class).run(args)));
    }
}
