package br.com.renanravelli.batch.configuration.listener;

import br.com.renanravelli.batch.configuration.job.UserJob;
import br.com.renanravelli.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author joao4018
 * @since 20/03/2020
 */
@Configuration
@RequiredArgsConstructor
public class listenerWriterBD {

    private static final Logger log = LoggerFactory.getLogger(UserJob.class);

    private final UserRepository userRepository;


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
                            forEach(user -> log.info("Found <" + user + "> in the database."));
                }
            }
        };
    }
}
