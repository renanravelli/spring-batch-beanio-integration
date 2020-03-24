package br.com.renanravelli.batch.configuration.job;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author renanravelli
 * @since 02/03/2019
 * @Maintener joao4018
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
                         @Qualifier("stepReaderFileUsers") Step userStepReaderFile,
                         @Qualifier("stepWriterUsersDB") Step stepWriterUsersBD,
                         JobExecutionListener listener) {
        return this.jobBuilderFactory.get("USER_JOB_CREATE")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(stepReaderUsers)
                .next(userStepReaderFile)
                .next(stepWriterUsersBD)
                .build();
    }
    /**
     * Job utilizando o exemplo de simples de flow, para utilizar
     * comente o job de cima e descomnte o de baixo
     */

//    @Bean
//    public Job sampleJob(@Qualifier("flow") Flow flow,
//                         JobExecutionListener listener) {
//        return this.jobBuilderFactory.get("USER_JOB_CREATE")
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .start(flow)
//                .end()
//                .build();
//    }

}
