package br.com.renanravelli.batch.configuration.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class UserJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job sampleJob(@Qualifier("stepReaderUsers") Step stepReaderUsers) {
        return this.jobBuilderFactory.get("sampleJob")
                .incrementer(new RunIdIncrementer())
                .start(stepReaderUsers)
                .build();
    }
}
