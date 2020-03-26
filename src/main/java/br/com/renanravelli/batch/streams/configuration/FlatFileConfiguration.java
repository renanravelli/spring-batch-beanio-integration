package br.com.renanravelli.batch.streams.configuration;

import br.com.renanravelli.batch.streams.enums.FlatFileOption;
import br.com.renanravelli.batch.streams.enums.StreamName;
import lombok.Getter;
import lombok.NonNull;
import org.beanio.StreamFactory;
import org.beanio.spring.BeanIOFlatFileItemReader;
import org.beanio.spring.BeanIOFlatFileItemWriter;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
public class FlatFileConfiguration<T> {

    @Getter
    private BeanIOFlatFileItemWriter<T> writer;
    @Getter
    private BeanIOFlatFileItemReader<T> reader;

    @NonNull
    private StreamFactory streamFactory;

    public void initialize(FlatFileOption options, StreamName stream, String path) {
        try {
            if (FlatFileOption.isWriter(options)) {
                initializeWrite(stream, path);
            } else if (FlatFileOption.isReader(options)) {
                initializeReader(stream, path);
            } else {
                throw new RuntimeException("É necessário informar o FlatFileOption!");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (Objects.nonNull(this.writer)) {
            this.writer.close();
        } else if (Objects.nonNull(this.reader)) {
            this.reader.close();
        } else {
            throw new RuntimeException("Não foi encontrado nenhum BeanIOFlatFileItem instanciado!");
        }
    }

    private void initializeWrite(StreamName stream, String path) {
        try {
            this.writer = new BeanIOFlatFileItemWriter();
            this.writer.setResource(new FileSystemResource( // OBS: Utilizando o nome do stream para gerar o arquivo.
                    path.concat(File.separator)
                            .concat(stream.getStream()
                                    .concat(stream.getExtesion()))));
            this.writer.setStreamName(stream.getStream());
            this.writer.setStreamFactory(streamFactory);
            this.writer.setTransactional(false);
            this.writer.open(new ExecutionContext());
            this.writer.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao inicializar o initializeWrite!");
        }
    }

    private void initializeReader(StreamName stream, String path) {
        try {
            this.reader = new BeanIOFlatFileItemReader<>();
            this.reader.setResource(new FileSystemResource( //OBS: Utilizando o nome do stream para gerar o arquivo.
                    path.concat(File.separator)
                            .concat(stream.getStream()
                                    .concat(stream.getExtesion()))));
            this.reader.setStreamName(stream.getStream());
            this.reader.setStreamFactory(streamFactory);
            this.reader.open(new ExecutionContext());
            this.reader.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao inicializar o initializeReader!");
        }
    }
}
