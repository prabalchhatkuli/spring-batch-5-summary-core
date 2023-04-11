package dev.prabal.batchsummarycore.config;

import dev.prabal.batchsummarycore.listener.FirstJobListener;
import dev.prabal.batchsummarycore.listener.FirstStepListener;
import dev.prabal.batchsummarycore.service.SecondTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.DefaultExecutionContextSerializer;
import org.springframework.batch.core.repository.dao.Jackson2ExecutionContextStringSerializer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleJob extends DefaultBatchConfiguration{

    @Override
    protected ExecutionContextSerializer getExecutionContextSerializer() {
        return new Jackson2ExecutionContextStringSerializer();
    }

    @Bean
    @Order(1)
    public Job firstJob(JobRepository jobRepository, Step firstStep, Step secondStep, FirstJobListener firstJobListener){
        return new JobBuilder("First Job",jobRepository)
                .start(firstStep)
                .next(secondStep)
                .listener(firstJobListener)
                .build();
    }

    @Bean
    Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, FirstStepListener firstStepListener){
        return new StepBuilder("First Step", jobRepository)
                .tasklet(firstTask(), transactionManager)
                .listener(firstStepListener)
                .build();
    }

    @Bean
    Step secondStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, SecondTasklet secondTasklet){
        return new StepBuilder("Second Step", jobRepository)
                .tasklet(secondTasklet, transactionManager)
                .build();
    }

    private Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is first tasklet step");
                System.out.println("SEC = "+chunkContext.getStepContext().getStepExecutionContext());
                return RepeatStatus.FINISHED;
            }
        };
    }

    @Bean
    @Order(2)
    public Job secondJob(JobRepository jobRepository, Step firstChunkStep){
        return new JobBuilder("Second Job", jobRepository)
                .start(firstChunkStep)
                .build();
    }

    @Bean
    Step firstChunkStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, ItemReader firstItemReader, ItemWriter firstItemWriter, ItemProcessor firstItemProcessor){
        return new StepBuilder("First Chunk Step", jobRepository)
                .<Integer, Long>chunk(3, platformTransactionManager)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        final JobRegistryBeanPostProcessor answer = new JobRegistryBeanPostProcessor();
        answer.setJobRegistry(jobRegistry);
        return answer;
    }


}
