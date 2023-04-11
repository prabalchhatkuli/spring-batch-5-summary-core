package dev.prabal.batchsummarycore.service;

import dev.prabal.batchsummarycore.request.JobParamsRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    JobLauncher jobLauncher;
    Job firstJob;
    Job secondJob;

    public JobService(JobLauncher jobLauncher, @Qualifier("firstJob") Job firstJob, @Qualifier("secondJob") Job secondJob){
        this.jobLauncher=jobLauncher;
        this.firstJob=firstJob;
        this.secondJob=secondJob;
    }

    @Async
    public void startJob(String jobName, List<JobParamsRequest> jobParamsRequestList){
        Map<String, JobParameter<?>> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis(), String.class));

        jobParamsRequestList.stream().forEach(jobParamsRequest -> {
            params.put(jobParamsRequest.getParamKey(), new JobParameter(jobParamsRequest.getParamValue(), String.class));
        });

        JobParameters jobParameters = new JobParameters(params);

        try{
            JobExecution jobExecution=null;
        if(jobName.equals("First Job")) {
            jobExecution = jobLauncher.run(firstJob, jobParameters);
        } else if(jobName.equals("Second Job")){
            jobExecution = jobLauncher.run(secondJob, jobParameters);
        }

        System.out.println("jobExecution ID: "+jobExecution.getId());

        }
        catch (Exception e){
            System.out.println("Exception while starting job");
        }

    }
}
