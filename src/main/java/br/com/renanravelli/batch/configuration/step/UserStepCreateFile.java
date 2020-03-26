package br.com.renanravelli.batch.configuration.step;

import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.streams.enums.StreamName;
import br.com.renanravelli.batch.streams.mapping.Registry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.beanio.StreamFactory;
import org.beanio.spring.BeanIOFlatFileItemWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManager;

/**
 * @author renanravelli
 * @since 02/03/2019
 */
@Configuration
@RequiredArgsConstructor
public class UserStepCreateFile extends StepExecutionListenerSupport {

    @Value("${file.directory.out}")
    private String diretorio;
    private final EntityManager entityManager;
    @Qualifier("streamFactoryFile")
    private final StreamFactory streamFactoryFile;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public JpaPagingItemReader<User> jpaUserItemReader() {
        return new JpaPagingItemReaderBuilder<User>()
                .name("UserItemReader")
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .queryString("SELECT u FROM User u")
                .pageSize(2)
                .transacted(false)
                .build();
    }

    @Bean
    @StepScope
    @SneakyThrows
    public ItemWriter<Registry> userItemWriter() {
        BeanIOFlatFileItemWriter<Registry> writer = new BeanIOFlatFileItemWriter<>();
        writer.setResource(new FileSystemResource( // OBS: Utilizando o nome do stream para gerar o arquivo.
                diretorio + StreamName.USER_CSV.getStream() + StreamName.USER_CSV.getExtesion()));
        writer.setStreamName(StreamName.USER_CSV.getStream());
        writer.setStreamFactory(streamFactoryFile);
        writer.setTransactional(false);
        writer.open(new ExecutionContext());
        writer.afterPropertiesSet();
        return writer;
    }

    /**
     * Step responsavel por realizar a execucao dos itens reader e writer.
     */
    @Bean
    public Step stepReaderUsers(@Qualifier("jpaUserItemReader") ItemReader jpaUserItemReader,
                                @Qualifier("userItemProcessor") ItemProcessor userItemProcessor,
                                @Qualifier("userItemWriter") ItemWriter userItemWriter) {
        return this.stepBuilderFactory.get("STEP_READER_USERS_IN_DATABASE")
                .chunk(1000)
                .reader(jpaUserItemReader)
                .processor(userItemProcessor)
                .writer(userItemWriter)
                .build();
    }
}
