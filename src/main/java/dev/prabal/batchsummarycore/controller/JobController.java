package dev.prabal.batchsummarycore.controller;

import dev.prabal.batchsummarycore.request.JobParamsRequest;
import dev.prabal.batchsummarycore.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobController {

    JobService jobService;
    JobOperator jobOperator;

    @Autowired
    public JobController(JobService jobService, JobOperator jobOperator){
        this.jobService=jobService;
        this.jobOperator = jobOperator;
    }

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName, @RequestBody List<JobParamsRequest> jobParamsRequestList) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        jobService.startJob(jobName, jobParamsRequestList);

        return "Job Started...";
    }

    @GetMapping("/stop/{jobExecutionId}")
    public String stopJob(@PathVariable long jobExecutionId){
        try {
            jobOperator.stop(jobExecutionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Job Stopped...";
    }

}
