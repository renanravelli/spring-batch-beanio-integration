package br.com.renanravelli.batch.configuration.step;

import br.com.renanravelli.batch.streams.enums.StreamName;
import br.com.renanravelli.batch.streams.mapping.Registry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.beanio.StreamFactory;
import org.beanio.spring.BeanIOFlatFileItemReader;
import org.beanio.spring.BeanIOFlatFileItemWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class UserStepReaderFile {

    @Value("${file.directory.out}")
    private String diretorio;
    @Qualifier("streamFactoryFile")
    private final StreamFactory streamFactoryFile;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    @SneakyThrows
    public ItemReader<Registry> userItemReaderFile(@Qualifier("streamFactoryFile") StreamFactory streamFactoryFile,
                                                   @Value("${file.directory.out}") String path) {
        BeanIOFlatFileItemReader<Registry> reader = new BeanIOFlatFileItemReader<>();
        reader.setResource(new FileSystemResource(
                path.concat(File.separator)
                        .concat(StreamName.USER_CSV.getStream()
                                .concat(StreamName.USER_CSV.getExtesion()))));
        reader.setStreamName(StreamName.USER_CSV.getStream());
        reader.setStreamFactory(streamFactoryFile);
        reader.open(new ExecutionContext());
        reader.afterPropertiesSet();
        return reader;
    }

    @Bean
    @StepScope
    @SneakyThrows
    public ItemWriter<Registry> userItemWriterFile(){
       BeanIOFlatFileItemWriter<Registry> writer = new BeanIOFlatFileItemWriter<>();
        writer.setResource(new FileSystemResource( // OBS: Utilizando o nome do stream para gerar o arquivo.
                diretorio + StreamName.USER_DELIMITED.getStream() + StreamName.USER_DELIMITED.getExtesion()));
        writer.setStreamName(StreamName.USER_FIXED_LENGTH.getStream());
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
    public Step stepReaderFileUsers(@Qualifier("userItemReaderFile") ItemReader userItemReaderFile,
                                    @Qualifier("userItemWriterFile") ItemWriter userItemWriterFile) {
        return this.stepBuilderFactory.get("STEP_READER_USERS_IN_FILE")
                .chunk(1000)
                .reader(userItemReaderFile)
                .writer(userItemWriterFile)
                .build();
    }


}
