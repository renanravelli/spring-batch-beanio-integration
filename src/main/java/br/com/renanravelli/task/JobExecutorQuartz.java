package br.com.renanravelli.task;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Getter
@Setter
public class JobExecutorQuartz extends QuartzJobBean {

    private JobLauncher jobLauncher;
    private JobLocator jobLocator;

    @Override
    @SneakyThrows
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Job job = this.jobLocator.getJob("USER_JOB_CREATE");
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        this.jobLauncher.run(job, params);
    }
}
