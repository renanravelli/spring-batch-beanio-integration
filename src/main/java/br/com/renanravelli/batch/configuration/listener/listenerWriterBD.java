package br.com.renanravelli.batch.configuration.listener;

import br.com.renanravelli.batch.configuration.job.UserJob;
import br.com.renanravelli.batch.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class listenerWriterBD {

    private static final Logger log = LoggerFactory.getLogger(UserJob.class);

    @Autowired
    UserRepository userRepository;


    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {


            @Override
            public void beforeJob(JobExecution jobExecution) {

            }


            @Override
            public void afterJob(JobExecution jobExecution) {
                if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                    log.info("!!! JOB FINISHED! Time to verify the results");
                    userRepository.findAll().
                            forEach(person -> log.info("Found <" + person + "> in the database."));
                }
            }
        };
    }
}
