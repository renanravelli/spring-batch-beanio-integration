package br.com.renanravelli.batch.configuration.step;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author joao4018
 * @since 20/03/2020
 */
@Configuration
@RequiredArgsConstructor
public class UserStepWriterDB extends StepExecutionListenerSupport {

    @NonNull
    private StepBuilderFactory stepBuilderFactory;

    /**
     * Step responsavel por realizar a execucao dos itens reader e writer.
     */
    @Bean("stepWriterUsersDB")
    public Step stepWriteUsersDB(@Qualifier("jpaUserItemReader") ItemReader reader, @Qualifier("jpaUserItemWriter") ItemWriter writer) {
        return this.stepBuilderFactory.get("STEP_WRITER_USERS_IN_DATABASE")
                .chunk(1000)
                .reader(reader)
                .writer(writer)
                .build();
    }

}
