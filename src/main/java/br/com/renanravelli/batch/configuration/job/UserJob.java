package br.com.renanravelli.batch.configuration.job;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author renanravelli
 * @since 02/03/2019
 */
@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class UserJob {

    private JobBuilderFactory jobBuilderFactory;

    /**
     * Job responsavel por realizar a chamada dos steps de escrita do arquivo.
     */
    @Bean
    public Job sampleJob(@Qualifier("stepReaderUsers") Step stepReaderUsers,
                         @Qualifier("stepReaderFileUsers") Step userStepReaderFile) {
        return this.jobBuilderFactory.get("USER_JOB_CREATE")
                .incrementer(new RunIdIncrementer())
                .start(stepReaderUsers)
                .next(userStepReaderFile)
                .build();
    }

}
