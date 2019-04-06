package br.com.renanravelli.batch.configuration.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author renanravelli
 * @since 02/03/2019
 */
@Configuration
public class UserStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Value("${file.directory.out}")
    private String path;


    /**
     * Step responsavel por realizar a execucao dos itens reader e writer.
     */
    @Bean("stepReaderUsers")
    public Step stepReaderUsers(@Qualifier("reader") ItemReader reader, @Qualifier("userItemWriter") ItemWriter writer) {
        return this.stepBuilderFactory.get("STEP_READER_USERS_IN_DATABASE")
                .chunk(1000)
                .reader(reader)
                .writer(writer)
                .build();
    }
}
